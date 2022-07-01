package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class DemoteStatement extends TerminalStatement {
  public DemoteStatement() {
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.DEMOTE;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitDemoteStatement(this);
  }
}
