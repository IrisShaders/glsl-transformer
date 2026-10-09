package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;
import java.util.function.Predicate;

import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.Expression.ExpressionType;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression.FunctionReferenceType;
import io.github.douira.glsl_transformer.ast.typing.FunctionSignature.Direction;

/**
 * Determines the type of expressions. Every visited expression gets a type,
 * which is the error type if the expression is invalid. An expression with an
 * operand of the error type does not generate further diagnostics.
 */
final class ExpressionTyper {
  private static final Predicate<NumericType> ARITHMETIC = type -> !type.getNumberType().isBoolean();
  private static final Predicate<NumericType> INTEGER = type -> type.getNumberType().isInteger();
  private static final Predicate<NumericType> BOOLEAN = type -> type == NumericType.BOOL;
  private static final String[] SWIZZLE_SETS = { "xyzw", "rgba", "stpq" };

  private final AnalysisContext ctx;

  ExpressionTyper(AnalysisContext ctx) {
    this.ctx = ctx;
  }

  Type type(Expression node) {
    return ctx.record(node, compute(node));
  }

  private Type compute(Expression node) {
    return switch (node.getExpressionType()) {
      case REFERENCE -> reference((ReferenceExpression) node);
      case LITERAL -> ((LiteralExpression) node).getLiteralType();
      case GROUPING -> type(((UnaryExpression) node).getOperand());
      case INCREMENT_POSTFIX, DECREMENT_POSTFIX, INCREMENT_PREFIX, DECREMENT_PREFIX -> {
        var operand = ((UnaryExpression) node).getOperand();
        var type = unary((UnaryExpression) node, ARITHMETIC);
        requireAssignable(operand, type);
        yield type;
      }
      case IDENTITY, NEGATION -> unary((UnaryExpression) node, ARITHMETIC);
      case BOOLEAN_NOT -> unary((UnaryExpression) node, BOOLEAN);
      case BITWISE_NOT -> unary((UnaryExpression) node, INTEGER);
      case LENGTH_ACCESS -> lengthAccess((LengthAccessExpression) node);
      case MEMBER_ACCESS -> memberAccess((MemberAccessExpression) node);
      case FUNCTION_CALL -> call((FunctionCallExpression) node);
      case ARRAY_ACCESS -> arrayAccess((ArrayAccessExpression) node);
      case MULTIPLICATION, DIVISION, MODULO, ADDITION, SUBTRACTION, SHIFT_LEFT, SHIFT_RIGHT,
          BITWISE_AND, BITWISE_XOR, BITWISE_OR -> {
        var binary = (BinaryExpression) node;
        yield operation(node, node.getExpressionType(), type(binary.getLeft()), type(binary.getRight()));
      }
      case LESS_THAN, GREATER_THAN, LESS_THAN_EQUAL, GREATER_THAN_EQUAL -> comparison((BinaryExpression) node, true);
      case EQUAL, NOT_EQUAL -> comparison((BinaryExpression) node, false);
      case BOOLEAN_AND, BOOLEAN_XOR, BOOLEAN_OR -> {
        var binary = (BinaryExpression) node;
        var left = type(binary.getLeft());
        var right = type(binary.getRight());
        if (!Conversions.isSameType(left, NumericType.BOOL) || !Conversions.isSameType(right, NumericType.BOOL)) {
          invalidOperands(node, left, right);
        }
        yield NumericType.BOOL;
      }
      case ASSIGNMENT -> {
        var binary = (BinaryExpression) node;
        yield assign(binary, type(binary.getLeft()), type(binary.getRight()));
      }
      case MULTIPLICATION_ASSIGNMENT, DIVISION_ASSIGNMENT, MODULO_ASSIGNMENT, ADDITION_ASSIGNMENT,
          SUBTRACTION_ASSIGNMENT, LEFT_SHIFT_ASSIGNMENT, RIGHT_SHIFT_ASSIGNMENT, BITWISE_AND_ASSIGNMENT,
          BITWISE_XOR_ASSIGNMENT, BITWISE_OR_ASSIGNMENT -> {
        var binary = (BinaryExpression) node;
        var left = type(binary.getLeft());
        yield assign(binary, left,
            operation(node, operatorOfAssignment(node.getExpressionType()), left, type(binary.getRight())));
      }
      case CONDITION -> {
        var condition = (ConditionExpression) node;
        var test = type(condition.getCondition());
        if (!Conversions.isSameType(test, NumericType.BOOL)) {
          ctx.error(DiagnosticCode.INVALID_CONDITION, condition.getCondition(),
              "A condition has to be a bool but is " + test.getTypeName());
        }
        var ifTrue = type(condition.getTrueExpression());
        var ifFalse = type(condition.getFalseExpression());
        var common = Conversions.commonType(ifTrue, ifFalse, ctx.env);
        yield common != null ? common
            : ctx.error(DiagnosticCode.TYPE_MISMATCH, node, "The alternatives of the conditional expression have the"
                + " incompatible types " + ifTrue.getTypeName() + " and " + ifFalse.getTypeName());
      }
      case SEQUENCE -> {
        Type last = ErrorType.INSTANCE;
        for (var expression : ((SequenceExpression) node).getExpressions()) {
          last = type(expression);
        }
        yield last;
      }
    };
  }

  private static ExpressionType operatorOfAssignment(ExpressionType assignment) {
    return switch (assignment) {
      case MULTIPLICATION_ASSIGNMENT -> ExpressionType.MULTIPLICATION;
      case DIVISION_ASSIGNMENT -> ExpressionType.DIVISION;
      case MODULO_ASSIGNMENT -> ExpressionType.MODULO;
      case ADDITION_ASSIGNMENT -> ExpressionType.ADDITION;
      case SUBTRACTION_ASSIGNMENT -> ExpressionType.SUBTRACTION;
      case LEFT_SHIFT_ASSIGNMENT -> ExpressionType.SHIFT_LEFT;
      case RIGHT_SHIFT_ASSIGNMENT -> ExpressionType.SHIFT_RIGHT;
      case BITWISE_AND_ASSIGNMENT -> ExpressionType.BITWISE_AND;
      case BITWISE_XOR_ASSIGNMENT -> ExpressionType.BITWISE_XOR;
      default -> ExpressionType.BITWISE_OR;
    };
  }

  private Type invalidOperands(Expression node, Type... operands) {
    var names = new StringJoiner(" and ");
    for (var operand : operands) {
      names.add(operand.getTypeName());
    }
    return ctx.error(DiagnosticCode.INVALID_OPERANDS, node,
        "The operator " + node.getExpressionType() + " cannot be applied to " + names);
  }

  private Type reference(ReferenceExpression node) {
    var name = node.getIdentifier().getName();
    var symbol = ctx.scope.lookup(name);
    if (symbol instanceof VariableSymbol variable) {
      ctx.analysis.symbols.put(node, variable);
      return variable.type();
    }
    return symbol == null
        ? ctx.error(DiagnosticCode.UNDECLARED_IDENTIFIER, node, "Undeclared identifier '" + name + "'")
        : ctx.error(DiagnosticCode.NOT_A_VARIABLE, node, "'" + name + "' is not a variable");
  }

  private Type unary(UnaryExpression node, Predicate<NumericType> accepted) {
    var operand = type(node.getOperand());
    return operand.isError() || operand instanceof NumericType numeric && accepted.test(numeric)
        ? operand
        : invalidOperands(node, operand);
  }

  private Type operation(Expression node, ExpressionType operator, Type left, Type right) {
    if (left.isError() || right.isError()) {
      return ErrorType.INSTANCE;
    }
    var result = left instanceof NumericType x && right instanceof NumericType y
        ? numericOperation(operator, x, y)
        : null;
    return result != null ? result : invalidOperands(node, left, right);
  }

  /**
   * Returns the result type of an arithmetic, bitwise or shift operator, or
   * null if the operator cannot be applied to the operands.
   */
  private NumericType numericOperation(ExpressionType operator, NumericType left, NumericType right) {
    var isShift = operator == ExpressionType.SHIFT_LEFT || operator == ExpressionType.SHIFT_RIGHT;
    var integerOnly = isShift || operator == ExpressionType.MODULO || operator == ExpressionType.BITWISE_AND
        || operator == ExpressionType.BITWISE_XOR || operator == ExpressionType.BITWISE_OR;

    // the result of a shift has the type of the left operand
    var component = isShift ? left.getComponentType()
        : Conversions.binaryResult(left.getComponentType(), right.getComponentType(), ctx.env);
    if (component == null || component.getNumberType().isBoolean()
        || integerOnly && !(component.getNumberType().isInteger() && right.getNumberType().isInteger())) {
      return null;
    }
    if (right.isScalar()) {
      return left.withComponentType(component);
    }
    if (left.isScalar()) {
      return isShift ? null : right.withComponentType(component);
    }
    if (operator == ExpressionType.MULTIPLICATION && (left.isMatrix() || right.isMatrix())) {
      // linear algebraic multiplication, where a vector on the left is a row
      // vector and a vector on the right is a column vector
      var leftColumns = left.isVector() ? left.getRows() : left.getColumns();
      var leftRows = left.isVector() ? 1 : left.getRows();
      if (leftColumns != right.getRows()) {
        return null;
      }
      return leftRows == 1
          ? NumericType.vector(component, right.getColumns())
          : NumericType.matrix(component, right.getColumns(), leftRows);
    }
    return Arrays.equals(left.getDimensions(), right.getDimensions()) ? left.withComponentType(component) : null;
  }

  private Type comparison(BinaryExpression node, boolean relational) {
    var left = type(node.getLeft());
    var right = type(node.getRight());
    var common = Conversions.commonType(left, right, ctx.env);
    var valid = left.isError() || right.isError() || common != null && (!relational
        || common instanceof NumericType numeric && numeric.isScalar() && !numeric.getNumberType().isBoolean());
    if (!valid) {
      invalidOperands(node, left, right);
    }
    return NumericType.BOOL;
  }

  private Type assign(BinaryExpression node, Type target, Type value) {
    requireAssignable(node.getLeft(), target);
    if (!ctx.converts(value, target)) {
      ctx.error(DiagnosticCode.TYPE_MISMATCH, node,
          "Cannot assign a value of type " + value.getTypeName() + " to " + target.getTypeName());
    }
    return target;
  }

  private void requireAssignable(Expression target, Type type) {
    if (!type.isError() && !isAssignable(target)) {
      ctx.error(DiagnosticCode.NOT_ASSIGNABLE, target, "The expression cannot be assigned to");
    }
  }

  // only called for expressions that have a valid type
  private boolean isAssignable(Expression node) {
    if (node instanceof ReferenceExpression) {
      return ((VariableSymbol) ctx.analysis.symbolOf(node)).assignable();
    }
    if (node instanceof GroupingExpression grouping) {
      return isAssignable(grouping.getOperand());
    }
    if (node instanceof ArrayAccessExpression access) {
      return isAssignable(access.getLeft());
    }
    if (node instanceof MemberAccessExpression access) {
      // a swizzle that repeats a component cannot be assigned to
      var member = access.getMember().getName();
      return isAssignable(access.getOperand())
          && !(ctx.analysis.typeOf(access.getOperand()) instanceof NumericType
              && member.chars().distinct().count() != member.length());
    }
    return false;
  }

  private Type arrayAccess(ArrayAccessExpression node) {
    var base = type(node.getLeft());
    var index = type(node.getRight());
    var validIndex = index instanceof NumericType numeric && numeric.isScalar()
        && numeric.getNumberType().isInteger();
    if (!validIndex && !index.isError()) {
      ctx.error(DiagnosticCode.INVALID_INDEX, node.getRight(),
          "An index has to be an integer but is " + index.getTypeName());
    }

    Type element;
    var size = Integer.MAX_VALUE;
    if (base instanceof ArrayType array) {
      element = array.element();
      size = array.size().isKnown() ? array.size().length() : size;
    } else if (base instanceof NumericType numeric && !numeric.isScalar()) {
      element = numeric.getElementType();
      size = numeric.isMatrix() ? numeric.getColumns() : numeric.getRows();
    } else {
      return base.isError() ? base
          : ctx.error(DiagnosticCode.INVALID_INDEX, node, "A value of type " + base.getTypeName()
              + " cannot be indexed");
    }

    if (validIndex) {
      var value = ctx.constants.evaluate(node.getRight());
      if (value != null && value.isKnown() && (value.value() < 0 || value.value() >= size)) {
        ctx.error(DiagnosticCode.INDEX_OUT_OF_BOUNDS, node.getRight(), "The index " + value.value()
            + " is out of bounds for a value of type " + base.getTypeName());
      }
    }
    return element;
  }

  private Type memberAccess(MemberAccessExpression node) {
    var operand = type(node.getOperand());
    var name = node.getMember().getName();
    if (operand.isError()) {
      return operand;
    }
    var members = operand instanceof StructType struct ? struct.getFields()
        : operand instanceof InterfaceBlockType block ? block.getMembers() : null;
    if (members != null && members.containsKey(name)) {
      return members.get(name);
    }
    if (!(operand instanceof NumericType numeric) || numeric.isMatrix()) {
      return ctx.error(DiagnosticCode.NO_SUCH_MEMBER, node,
          "A value of type " + operand.getTypeName() + " has no member '" + name + "'");
    }

    // swizzles are also allowed on scalars
    for (var set : SWIZZLE_SETS) {
      var valid = name.length() <= 4;
      for (var i = 0; i < name.length(); i++) {
        var component = set.indexOf(name.charAt(i));
        valid &= component >= 0 && component < numeric.getRows();
      }
      if (valid) {
        return numeric.withComponentCount(name.length());
      }
    }
    return ctx.error(DiagnosticCode.INVALID_SWIZZLE, node,
        "'" + name + "' is not a valid swizzle of a value of type " + operand.getTypeName());
  }

  private Type lengthAccess(LengthAccessExpression node) {
    var operand = type(node.getOperand());
    if (!(operand.isError() || operand instanceof ArrayType
        || operand instanceof NumericType numeric && !numeric.isScalar())) {
      invalidOperands(node, operand);
    }
    return NumericType.INT32;
  }

  private Type call(FunctionCallExpression node) {
    var arguments = new ArrayList<Type>();
    for (var parameter : node.getParameters()) {
      arguments.add(type(parameter));
    }
    if (node.getReferenceType() == FunctionReferenceType.TYPE_SPECIFIER) {
      return construct(node, ctx.declarations.typeSpecifier(node.getFunctionSpecifier()), arguments);
    }

    var name = node.getFunctionName().getName();
    var symbol = ctx.scope.lookup(name);
    ctx.analysis.symbols.put(node, symbol);
    if (symbol instanceof TypeSymbol type) {
      return construct(node, type.type(), arguments);
    }

    List<FunctionSignature> viable = List.of();
    if (symbol instanceof VariableSymbol variable && variable.type() instanceof SubroutineType subroutine) {
      viable = viable(subroutine.function(), arguments);
    } else if (symbol instanceof FunctionSymbol) {
      // functions hide the functions with the same name in the enclosing scopes,
      // but those are still used if none of the hiding functions fit
      for (var scope = ctx.scope; scope != null && viable.isEmpty(); scope = scope.getParent()) {
        if (scope.lookupLocal(name) instanceof FunctionSymbol function) {
          viable = viable(function, arguments);
        }
      }
    } else {
      return symbol == null
          ? ctx.error(DiagnosticCode.UNDECLARED_IDENTIFIER, node, "Undeclared function '" + name + "'")
          : ctx.error(DiagnosticCode.NOT_A_FUNCTION, node, "'" + name + "' is not a function");
    }
    return select(node, name, viable, arguments);
  }

  private List<FunctionSignature> viable(FunctionSymbol function, List<Type> arguments) {
    return function.overloads().stream().filter(signature -> accepts(signature, arguments)).toList();
  }

  private boolean accepts(FunctionSignature signature, List<Type> arguments) {
    var parameters = signature.parameters();
    if (arguments.size() < parameters.size() || arguments.size() > parameters.size() && !signature.variadic()) {
      return false;
    }
    for (var i = 0; i < parameters.size(); i++) {
      // values are converted to the parameter type on the way in and to the
      // type of the argument on the way out
      var parameter = parameters.get(i);
      var argument = arguments.get(i);
      if (parameter.direction() != Direction.OUT && !ctx.converts(argument, parameter.type())
          || parameter.direction() != Direction.IN && !ctx.converts(parameter.type(), argument)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isBetter(FunctionSignature candidate, FunctionSignature other, List<Type> arguments) {
    var better = false;
    for (var i = 0; i < Math.min(candidate.parameters().size(), other.parameters().size()); i++) {
      var candidateType = candidate.parameters().get(i).type();
      var otherType = other.parameters().get(i).type();
      if (Conversions.isBetterConversion(arguments.get(i), otherType, candidateType)) {
        return false;
      }
      better |= Conversions.isBetterConversion(arguments.get(i), candidateType, otherType);
    }
    return better;
  }

  private Type select(FunctionCallExpression node, String name, List<FunctionSignature> viable,
      List<Type> arguments) {
    var argumentNames = arguments.stream().map(Type::getTypeName).toList();
    var hasError = arguments.stream().anyMatch(Type::isError);
    var best = viable.stream()
        .filter(candidate -> viable.stream().noneMatch(other -> isBetter(other, candidate, arguments)))
        .toList();
    if (best.isEmpty()) {
      return hasError ? ErrorType.INSTANCE
          : ctx.error(DiagnosticCode.NO_MATCHING_OVERLOAD, node,
              "No overload of the function '" + name + "' accepts the arguments " + argumentNames);
    }

    var selected = best.get(0);
    if (best.size() == 1) {
      ctx.analysis.signatures.put(node, selected);
      for (var i = 0; i < selected.parameters().size(); i++) {
        if (selected.parameters().get(i).direction() != Direction.IN) {
          requireAssignable(node.getParameters().get(i), arguments.get(i));
        }
      }
      return selected.returnType();
    }

    // an ambiguous call still has a type if all candidates return the same type
    if (!hasError) {
      ctx.error(DiagnosticCode.AMBIGUOUS_OVERLOAD, node, "The call to the function '" + name
          + "' with the arguments " + argumentNames + " is ambiguous between " + best);
    }
    return best.stream().allMatch(signature -> signature.returnType().equals(selected.returnType()))
        ? selected.returnType()
        : ErrorType.INSTANCE;
  }

  /**
   * Checks the arguments of a constructor and returns the constructed type.
   * The constructed type is returned even if the arguments are invalid.
   */
  private Type construct(FunctionCallExpression node, Type target, List<Type> arguments) {
    if (arguments.stream().anyMatch(Type::isError)) {
      return target;
    }
    boolean valid;
    if (target instanceof NumericType numeric) {
      valid = isNumericConstructor(numeric, arguments);
    } else {
      var expected = DeclarationAnalyzer.aggregateElements(target, arguments.size());
      valid = expected != null && expected.size() == arguments.size();
      for (var i = 0; valid && i < arguments.size(); i++) {
        valid = ctx.converts(arguments.get(i), expected.get(i));
      }
    }
    if (!valid || arguments.isEmpty()) {
      ctx.error(DiagnosticCode.INVALID_CONSTRUCTOR, node, "A value of type " + target.getTypeName()
          + " cannot be constructed from the arguments " + arguments.stream().map(Type::getTypeName).toList());
    }
    return target instanceof ArrayType array && !array.size().isKnown()
        ? new ArrayType(array.element(), ArraySize.of(arguments.size()))
        : target;
  }

  private static boolean isNumericConstructor(NumericType target, List<Type> arguments) {
    var total = 0;
    var beforeLast = 0;
    var hasMatrix = false;
    for (var argument : arguments) {
      if (!(argument instanceof NumericType numeric)) {
        return false;
      }
      beforeLast = total;
      total += numeric.getComponentCount();
      hasMatrix |= numeric.isMatrix();
    }

    // scalars are constructed from the first component of anything, a single
    // scalar initializes all components or the diagonal, and matrices can be
    // constructed from any other matrix
    if (arguments.size() == 1 && (total == 1 || target.isScalar() || target.isMatrix() && hasMatrix)) {
      return true;
    }

    // otherwise there have to be enough components and no unused arguments
    return !target.isScalar() && total >= target.getComponentCount() && beforeLast < target.getComponentCount()
        && !(target.isMatrix() && hasMatrix);
  }
}
