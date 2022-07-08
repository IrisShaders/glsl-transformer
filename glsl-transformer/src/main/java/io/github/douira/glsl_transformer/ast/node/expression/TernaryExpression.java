package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class TernaryExpression extends Expression {
  protected Expression first;
  protected Expression second;
  protected Expression third;

  public TernaryExpression(Expression first, Expression second, Expression third) {
    this.first = setup(first);
    this.second = setup(second);
    this.third = setup(third);
  }

  public Expression getFirst() {
    return first;
  }

  public void setFirst(Expression first) {
    updateParents(this.first, first);
    this.first = first;
  }

  public Expression getSecond() {
    return second;
  }

  public void setSecond(Expression second) {
    updateParents(this.second, second);
    this.second = second;
  }

  public Expression getThird() {
    return third;
  }

  public void setThird(Expression third) {
    updateParents(this.third, third);
    this.third = third;
  }

  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.TERNARY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitTernaryExpression(this),
        expressionAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterTernaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitTernaryExpression(this);
  }
}
