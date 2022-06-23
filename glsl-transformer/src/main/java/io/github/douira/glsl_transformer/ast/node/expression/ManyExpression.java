package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ManyExpression extends Expression {
  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.MANY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitManyExpression(this),
        expressionAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterManyExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitManyExpression(this);
  }

}
