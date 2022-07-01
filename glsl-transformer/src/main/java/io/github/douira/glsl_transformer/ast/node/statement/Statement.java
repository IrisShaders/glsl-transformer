package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Statement extends InnerASTNode {
  public enum StatementType {
    COMPOUND, // many-ary
    DECLARATION, // TODO (incomplete) terminal
    EXPRESSION, // terminal
    EMPTY, // terminal
    SELECTION, // TODO many-ary (if-else)
    SWITCH, // TODO many-ary but complicated
    CASE_LABEL, // TODO special
    FOR_LOOP, // TODO unary
    WHILE_LOOP, // TODO unary
    DO_WHILE_LOOP, // TODO unary

    CONTINUE, // terminal
    BREAK, // terminal
    RETURN, // terminal
    DISCARD, // terminal
    DEMOTE // terminal
  }

  public abstract StatementType getStatementType();

  public enum StructureType {
    SEMI_TERMINAL, // no nested statements but not a terminal AST node
    TERMINAL, // no nested statements or AST nodes
    UNARY, // one nested statement
    MANY, // a list of nested statements
    SPECIAL // something else (case-label), TODO the handling of this
  }

  public abstract StructureType getStructureType();

  public abstract <R> R statementAccept(ASTVisitor<R> visitor);

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
