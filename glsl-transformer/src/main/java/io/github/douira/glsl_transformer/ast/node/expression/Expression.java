package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Expression extends InnerASTNode {
  public enum ExpressionType {
    REFERENCE, // TODO
    LITERAL, // TODO
    GROUPING, // TODO
    ARRAY_ACCESS, // TODO
    METHOD_CALL, // TODO
    FUNCTION_CALL, // TODO
    MEMBER_ACCESS, // TODO
    POSTFIX, // TODO
    INCREMENT_POSTFIX, // TODO
    DECREMENT_POSTFIX, // TODO
    INCREMENT_PREFIX, // TODO
    DECREMENT_PREFIX, // TODO
    NEGATION, // TODO
    BOOLEAN_NOT, // TODO
    BITWISE_NOT, // TODO
    MULTIPLICATION, // TODO
    DIVISION, // TODO
    MODULO, // TODO
    ADDITION, // TODO
    SUBTRACTION, // TODO
    SHIFT_LEFT, // TODO
    SHIFT_RIGHT, // TODO
    LESS_THAN, // TODO
    GREATER_THAN, // TODO
    LESS_THAN_EQUAL, // TODO
    GREATER_THAN_EQUAL, // TODO
    EQUAL, // TODO
    NOT_EQUAL, // TODO
    BITWISE_AND, // TODO
    BITWISE_XOR, // TODO
    BITWISE_OR, // TODO
    BOOLEAN_AND, // TODO
    BOOLEAN_XOR, // TODO
    BOOLEAN_OR, // TODO
    TERNARY, // TODO
    ASSIGNMENT, // TODO
    MULTIPLICATION_ASSIGNMENT, // TODO
    DIVISION_ASSIGNMENT, // TODO
    MODULO_ASSIGNMENT, // TODO
    ADDITION_ASSIGNMENT, // TODO
    SUBTRACTION_ASSIGNMENT, // TODO
    LEFT_SHIFT_ASSIGNMENT, // TODO
    RIGHT_SHIFT_ASSIGNMENT, // TODO
    BITWISE_AND_ASSIGNMENT, // TODO
    BITWISE_XOR_ASSIGNMENT, // TODO
    BITWISE_OR_ASSIGNMENT, // TODO
    SEQUENCE // TODO
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
