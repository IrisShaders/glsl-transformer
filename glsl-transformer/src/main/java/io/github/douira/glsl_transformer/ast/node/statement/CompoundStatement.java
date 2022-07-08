package io.github.douira.glsl_transformer.ast.node.statement;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class CompoundStatement extends ManyStatement {
  public CompoundStatement(List<Statement> statements) {
    super(statements);
  }

  public CompoundStatement(Stream<Statement> statements) {
    super(statements);
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.COMPOUND;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitCompoundStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterCompoundStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitCompoundStatement(this);
  }
}
