package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class EmptyStatement extends Statement {

  @Override
  public StatementType getStatementType() {
    return StatementType.EMPTY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitEmptyStatement(this));
  }
}
