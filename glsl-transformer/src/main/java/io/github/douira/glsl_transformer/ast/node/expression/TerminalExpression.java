package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class TerminalExpression extends Expression {
  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.NONE;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitTerminalExpression(this),
        expressionAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    // terminal expressions have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    // terminal expressions have no children
  }
}
