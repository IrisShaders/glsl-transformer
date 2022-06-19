package io.github.douira.glsl_transformer.ast.node.statement;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class CompoundStatement extends Statement implements ListNode<Statement> {
  public List<Statement> statements;

  public CompoundStatement(List<Statement> statements) {
    this.statements = new ChildNodeList<>(statements, this);
  }

  public CompoundStatement(Stream<Statement> statements) {
    this.statements = ChildNodeList.collect(statements, this);
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.COMPOUND;
  }

  @Override
  public List<Statement> getChildren() {
    return statements;
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterCompoundStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitCompoundStatement(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitCompoundStatement(this));
  }
}
