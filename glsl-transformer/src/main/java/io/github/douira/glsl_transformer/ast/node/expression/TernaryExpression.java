package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class TernaryExpression extends Expression {
  public Expression first;
  public Expression second;
  public Expression third;

  public TernaryExpression(Expression first, Expression second, Expression third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.TERNARY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitTernaryExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTernaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTernaryExpression(this);
  }
}
