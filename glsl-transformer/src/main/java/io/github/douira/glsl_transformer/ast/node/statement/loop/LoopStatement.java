package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class LoopStatement extends Statement {
  protected Statement statement;

  public LoopStatement(Statement statement) {
    this.statement = setup(statement);
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    updateParents(this.statement, statement);
    this.statement = statement;
  }

  @Override
  public StructureType getStructureType() {
    return StructureType.UNARY;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitLoopStatement(this),
        statementAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterLoopStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitLoopStatement(this);
  }
}
