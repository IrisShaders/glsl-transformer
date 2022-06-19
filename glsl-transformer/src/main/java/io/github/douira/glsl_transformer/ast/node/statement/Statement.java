package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Statement extends InnerASTNode {
  public enum StatementType {
    COMPOUND,
    DECLARATION,
    EXPRESSION,
    EMPTY,
    SELECTION,
    SWITCH,
    CASE_LABEL,
    FOR_LOOP,
    WHILE_LOOP,
    DO_WHILE_LOOP,
    CONTINUE,
    BREAK,
    RETURN,
    DISCARD,
    DEMOTE
  }

  public abstract StatementType getStatementType();

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitStatement(this);
  }
}
