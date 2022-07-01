package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Expression extends InnerASTNode {
  public enum ExpressionType {
    REFERENCE, // identifier
    LITERAL, // literal
    GROUPING, // unary
    INCREMENT_POSTFIX, // unary
    DECREMENT_POSTFIX, // unary
    INCREMENT_PREFIX, // unary
    DECREMENT_PREFIX, // unary
    IDENTITY, // unary
    NEGATION, // unary
    BOOLEAN_NOT, // unary
    BITWISE_NOT, // unary
    LENGTH_ACCESS, // unary
    MEMBER_ACCESS, // unary + identifier
    FUNCTION_CALL, // TODO (incomplete) unary + parameters

    ARRAY_ACCESS, // binary
    MULTIPLICATION, // binary
    DIVISION, // binary
    MODULO, // binary
    ADDITION, // binary
    SUBTRACTION, // binary
    SHIFT_LEFT, // binary
    SHIFT_RIGHT, // binary
    LESS_THAN, // binary
    GREATER_THAN, // binary
    LESS_THAN_EQUAL, // binary
    GREATER_THAN_EQUAL, // binary
    EQUAL, // binary
    NOT_EQUAL, // binary
    BITWISE_AND, // binary
    BITWISE_XOR, // binary
    BITWISE_OR, // binary
    BOOLEAN_AND, // binary
    BOOLEAN_XOR, // binary
    BOOLEAN_OR, // binary
    ASSIGNMENT, // binary
    MULTIPLICATION_ASSIGNMENT, // binary
    DIVISION_ASSIGNMENT, // binary
    MODULO_ASSIGNMENT, // binary
    ADDITION_ASSIGNMENT, // binary
    SUBTRACTION_ASSIGNMENT, // binary
    LEFT_SHIFT_ASSIGNMENT, // binary
    RIGHT_SHIFT_ASSIGNMENT, // binary
    BITWISE_AND_ASSIGNMENT, // binary
    BITWISE_XOR_ASSIGNMENT, // binary
    BITWISE_OR_ASSIGNMENT, // binary

    CONDITION, // ternary
    SEQUENCE // many-ary
  }
  
  public abstract ExpressionType getExpressionType();

  public enum OperandStructure {
    NONE,
    UNARY,
    BINARY,
    TERNARY,
    MANY
  }

  public abstract OperandStructure getOperandStructure();

  public abstract <R> R expressionAccept(ASTVisitor<R> visitor);

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
