package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Statement extends InnerASTNode {
  public enum StatementType {
    COMPOUND, // many-ary
    DECLARATION, // TODO (incomplete) semi-terminal
    EXPRESSION, // semi-terminal
    EMPTY, // terminal
    SELECTION, // many-ary (if-else)
    SWITCH, // unary (nested compound statement)
    CASE, // semi-terminal
    DEFAULT, // terminal
    FOR_LOOP, // unary
    WHILE_LOOP, // unary
    DO_WHILE_LOOP, // unary

    CONTINUE, // terminal
    BREAK, // terminal
    RETURN, // semi-terminal
    DISCARD, // terminal
    DEMOTE // terminal
  }

  public abstract StatementType getStatementType();

  public enum StructureType {
    SEMI_TERMINAL, // no nested statements but not a terminal AST node
    TERMINAL, // no nested statements or AST nodes
    UNARY, // one nested statement
    MANY // a list of nested statements
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
