package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Statement extends InnerASTNode {
  public enum StatementType {
    COMPOUND,
    DECLARATION, // TODO
    EXPRESSION, // TODO
    EMPTY,
    SELECTION, // TODO
    SWITCH, // TODO
    CASE_LABEL, // TODO
    FOR_LOOP, // TODO
    WHILE_LOOP, // TODO
    DO_WHILE_LOOP, // TODO
    CONTINUE, // TODO
    BREAK, // TODO
    RETURN, // TODO
    DISCARD, // TODO
    DEMOTE // TODO
  }

  public abstract StatementType getStatementType();

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitStatement(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitStatement(this);
  }
}
