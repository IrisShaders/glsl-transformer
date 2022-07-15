package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class DefaultStatement extends CaseLabelStatement {
  @Override
  public CaseLabelType getCaseLabelType() {
    return CaseLabelType.DEFAULT;
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.DEFAULT;
  }

  @Override
  public StructureType getStructureType() {
    return StructureType.TERMINAL;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitDefaultStatement(this);
  }
}
