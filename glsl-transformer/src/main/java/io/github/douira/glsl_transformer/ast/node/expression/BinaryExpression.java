package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class BinaryExpression extends Expression {
  public Expression left;
  public Expression right;

  public BinaryExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
    left.setParent(this);
    right.setParent(this);
  }

  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.BINARY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitBinaryExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBinaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBinaryExpression(this);
  }
}