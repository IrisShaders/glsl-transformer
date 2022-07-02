package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class BinaryExpression extends Expression {
  public Expression left;
  public Expression right;

  public BinaryExpression(Expression left, Expression right) {
    this.left = setup(left);
    this.right = setup(right);
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
    listener.enterBinaryExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBinaryExpression(this);
  }
}
