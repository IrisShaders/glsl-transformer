package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class UnaryExpression extends Expression {
  public Expression operand;

  public UnaryExpression(Expression operand) {
    this.operand = operand;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitUnaryExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterUnaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitUnaryExpression(this);
  }
}
