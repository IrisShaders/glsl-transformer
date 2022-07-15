package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class BreakStatement extends TerminalStatement {
  public BreakStatement() {
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.BREAK;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitBreakStatement(this);
  }
}
