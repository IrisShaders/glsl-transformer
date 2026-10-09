package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.Expression.ExpressionType;
import io.github.douira.glsl_transformer.ast.node.expression.binary.BinaryExpression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;

/**
 * Evaluates integral constant expressions as they are needed for array sizes
 * and case labels. Expressions have to be typed before they are evaluated.
 * <p>
 * Only scalar boolean and integer values are computed. Other constant
 * expressions evaluate to {@link ConstantValue#UNKNOWN}.
 */
final class ConstantEvaluator {
  private final AnalysisContext ctx;

  ConstantEvaluator(AnalysisContext ctx) {
    this.ctx = ctx;
  }

  /**
   * Evaluates an expression.
   *
   * @return the value, which is unknown if the expression is constant but could
   *         not be evaluated, or null if the expression is not constant
   */
  ConstantValue evaluate(Expression node) {
    var type = ctx.analysis.typeOf(node);
    if (type.isError()) {
      // not knowing the value of something erroneous avoids further diagnostics
      return ConstantValue.UNKNOWN;
    }
    return switch (node.getExpressionType()) {
      case LITERAL -> {
        var literal = (LiteralExpression) node;
        yield literal.isBoolean() ? ConstantValue.of(type, literal.getBoolean() ? 1 : 0)
            : literal.isInteger() ? ConstantValue.of(type, literal.getInteger())
                : ConstantValue.UNKNOWN;
      }
      case REFERENCE -> ((VariableSymbol) ctx.analysis.symbolOf(node)).constantValue();
      case GROUPING, IDENTITY -> evaluate(((UnaryExpression) node).getOperand());
      case NEGATION, BOOLEAN_NOT, BITWISE_NOT -> {
        var operand = evaluate(((UnaryExpression) node).getOperand());
        yield operand == null || !operand.isKnown() ? operand
            : ConstantValue.of(type, switch (node.getExpressionType()) {
              case NEGATION -> -operand.value();
              case BITWISE_NOT -> ~operand.value();
              default -> operand.value() ^ 1;
            });
      }
      case MULTIPLICATION, DIVISION, MODULO, ADDITION, SUBTRACTION, SHIFT_LEFT, SHIFT_RIGHT,
          LESS_THAN, GREATER_THAN, LESS_THAN_EQUAL, GREATER_THAN_EQUAL, EQUAL, NOT_EQUAL,
          BITWISE_AND, BITWISE_XOR, BITWISE_OR, BOOLEAN_AND, BOOLEAN_XOR, BOOLEAN_OR -> {
        var binary = (BinaryExpression) node;
        yield binary(node.getExpressionType(), type, evaluate(binary.getLeft()), evaluate(binary.getRight()));
      }
      case CONDITION -> {
        var condition = (ConditionExpression) node;
        var test = evaluate(condition.getCondition());
        var ifTrue = evaluate(condition.getTrueExpression());
        var ifFalse = evaluate(condition.getFalseExpression());
        if (test == null || ifTrue == null || ifFalse == null) {
          yield null;
        }
        var selected = test.value() != 0 ? ifTrue : ifFalse;
        yield test.isKnown() && selected.isKnown() ? ConstantValue.of(type, selected.value()) : ConstantValue.UNKNOWN;
      }
      case FUNCTION_CALL -> {
        // calls to constructors and builtin functions with constant arguments are constant
        var call = (FunctionCallExpression) node;
        var signature = ctx.analysis.signatureOf(call);
        var constant = signature == null || signature.declaration() == null;
        ConstantValue only = null;
        for (var parameter : call.getParameters()) {
          only = evaluate(parameter);
          constant &= only != null;
        }
        yield !constant ? null
            : signature == null && call.getParameters().size() == 1 && only.isKnown()
                ? ConstantValue.of(type, only.value())
                : ConstantValue.UNKNOWN;
      }
      case LENGTH_ACCESS -> {
        var operandType = ctx.analysis.typeOf(((LengthAccessExpression) node).getOperand());
        // arrays without a size can be sized implicitly, for example by a layout
        // qualifier, so their length is treated as a constant with an unknown value
        yield operandType instanceof NumericType numeric
            ? ConstantValue.of(type, numeric.isMatrix() ? numeric.getColumns() : numeric.getRows())
            : operandType instanceof ArrayType array
                ? array.size().isKnown() ? ConstantValue.of(type, array.size().length()) : ConstantValue.UNKNOWN
                : null;
      }
      case MEMBER_ACCESS -> unknownIfConstant(evaluate(((MemberAccessExpression) node).getOperand()));
      case ARRAY_ACCESS -> {
        var access = (BinaryExpression) node;
        var left = evaluate(access.getLeft());
        yield evaluate(access.getRight()) == null ? null : unknownIfConstant(left);
      }
      case SEQUENCE, ASSIGNMENT, MULTIPLICATION_ASSIGNMENT, DIVISION_ASSIGNMENT, MODULO_ASSIGNMENT,
          ADDITION_ASSIGNMENT, SUBTRACTION_ASSIGNMENT, LEFT_SHIFT_ASSIGNMENT, RIGHT_SHIFT_ASSIGNMENT,
          BITWISE_AND_ASSIGNMENT, BITWISE_XOR_ASSIGNMENT, BITWISE_OR_ASSIGNMENT,
          INCREMENT_POSTFIX, DECREMENT_POSTFIX, INCREMENT_PREFIX, DECREMENT_PREFIX -> null;
    };
  }

  private static ConstantValue unknownIfConstant(ConstantValue value) {
    return value == null ? null : ConstantValue.UNKNOWN;
  }

  private ConstantValue binary(ExpressionType operator, Type resultType, ConstantValue left, ConstantValue right) {
    if (left == null || right == null) {
      return null;
    }
    if (!left.isKnown() || !right.isKnown()) {
      return ConstantValue.UNKNOWN;
    }

    // the operands of shifts are not converted to a common type, the operands of
    // all other operators are
    var isShift = operator == ExpressionType.SHIFT_LEFT || operator == ExpressionType.SHIFT_RIGHT;
    var operandType = isShift ? left.type() : Conversions.binaryResult(left.type(), right.type(), ctx.env);
    var unsigned = operandType.getNumberType() == NumberType.UNSIGNED_INTEGER;
    var a = ConstantValue.of(operandType, left.value()).value();
    var b = isShift ? right.value() : ConstantValue.of(operandType, right.value()).value();
    var comparison = unsigned ? Long.compareUnsigned(a, b) : Long.compare(a, b);
    if ((operator == ExpressionType.DIVISION || operator == ExpressionType.MODULO) && b == 0) {
      return ConstantValue.UNKNOWN;
    }
    return ConstantValue.of(resultType, switch (operator) {
      case MULTIPLICATION -> a * b;
      case DIVISION -> unsigned ? Long.divideUnsigned(a, b) : a / b;
      case MODULO -> unsigned ? Long.remainderUnsigned(a, b) : a % b;
      case ADDITION -> a + b;
      case SUBTRACTION -> a - b;
      case SHIFT_LEFT -> a << b;
      case SHIFT_RIGHT -> unsigned ? a >>> b : a >> b;
      case LESS_THAN -> comparison < 0 ? 1 : 0;
      case GREATER_THAN -> comparison > 0 ? 1 : 0;
      case LESS_THAN_EQUAL -> comparison <= 0 ? 1 : 0;
      case GREATER_THAN_EQUAL -> comparison >= 0 ? 1 : 0;
      case EQUAL -> comparison == 0 ? 1 : 0;
      case NOT_EQUAL -> comparison != 0 ? 1 : 0;
      case BITWISE_AND, BOOLEAN_AND -> a & b;
      case BITWISE_OR, BOOLEAN_OR -> a | b;
      default -> a ^ b;
    });
  }
}
