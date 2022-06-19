package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Expression extends InnerASTNode {
  public enum ExpressionType {
    REFERENCE,
    LITERAL,
    GROUPING,
    ARRAY_ACCESS,
    METHOD_CALL,
    FUNCTION_CALL,
    MEMBER_ACCESS,
    POSTFIX,
    INCREMENT_POSTFIX,
    DECREMENT_POSTFIX,
    INCREMENT_PREFIX,
    DECREMENT_PREFIX,
    NEGATION,
    BOOLEAN_NOT,
    BITWISE_NOT,
    MULTIPLICATION,
    DIVISION,
    MODULO,
    ADDITION,
    SUBTRACTION,
    SHIFT_LEFT,
    SHIFT_RIGHT,
    LESS_THAN,
    GREATER_THAN,
    LESS_THAN_EQUAL,
    GREATER_THAN_EQUAL,
    EQUAL,
    NOT_EQUAL,
    BITWISE_AND,
    BITWISE_XOR,
    BITWISE_OR,
    BOOLEAN_AND,
    BOOLEAN_XOR,
    BOOLEAN_OR,
    TERNARY,
    ASSIGNMENT,
    MULTIPLICATION_ASSIGNMENT,
    DIVISION_ASSIGNMENT,
    MODULO_ASSIGNMENT,
    ADDITION_ASSIGNMENT,
    SUBTRACTION_ASSIGNMENT,
    LEFT_SHIFT_ASSIGNMENT,
    RIGHT_SHIFT_ASSIGNMENT,
    BITWISE_AND_ASSIGNMENT,
    BITWISE_XOR_ASSIGNMENT,
    BITWISE_OR_ASSIGNMENT,
    SEQUENCE
  }

  public abstract ExpressionType getExpressionType();

  public enum OperandStructure {
    UNARY,
    BINARY,
    TERNARY
  }

  public abstract OperandStructure getOperandStructure();

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitExpression(this);
  }
}
