package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.node.statement.terminal.SemiTerminalStatement;
import io.github.douira.glsl_transformer.ast.query.Root;
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

  @Override
  public EmptyStatement clone() {
    return (EmptyStatement) super.clone();
  }

  @Override
  public EmptyStatement cloneInto(Root root) {
    return (EmptyStatement) super.cloneInto(root);
  }

  @Override
  public EmptyStatement cloneSeparate() {
    return (EmptyStatement) super.cloneSeparate();
  }
}
