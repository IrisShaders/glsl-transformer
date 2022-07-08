package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class BinaryExpression extends Expression {
  protected Expression left;
  protected Expression right;

  public BinaryExpression(Expression left, Expression right) {
    this.left = setup(left);
    this.right = setup(right);
  }

  public Expression getLeft() {
    return left;
  }

  public void setLeft(Expression left) {
    updateParents(this.left, left);
    this.left = left;
  }

  public Expression getRight() {
    return right;
  }

  public void setRight(Expression right) {
    updateParents(this.right, right);
    this.right = right;
  }

  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.BINARY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitBinaryExpression(this),
        expressionAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterBinaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitBinaryExpression(this);
  }
}
