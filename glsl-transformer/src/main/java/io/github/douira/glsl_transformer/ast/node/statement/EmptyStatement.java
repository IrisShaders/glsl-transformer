package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.node.statement.terminal.SemiTerminalStatement;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class EmptyStatement extends SemiTerminalStatement {
  public EmptyStatement() {
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.EMPTY;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitEmptyStatement(this);
  }
}
