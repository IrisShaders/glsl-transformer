package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class WhileLoopStatement extends ConditionLoopStatement {
  protected IterationConditionInitializer iterationConditionInitializer;

  public WhileLoopStatement(Expression condition, Statement statement) {
    super(statement, condition);
  }

  public WhileLoopStatement(
      IterationConditionInitializer iterationConditionInitializer,
      Statement statement) {
    super(statement, null);
    this.iterationConditionInitializer = setup(iterationConditionInitializer, this::setIterationConditionInitializer);
  }

  public IterationConditionInitializer getIterationConditionInitializer() {
    return iterationConditionInitializer;
  }

  public void setIterationConditionInitializer(IterationConditionInitializer iterationConditionInitializer) {
    updateParents(this.iterationConditionInitializer, iterationConditionInitializer,
        this::setIterationConditionInitializer);
    this.iterationConditionInitializer = iterationConditionInitializer;
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.WHILE_LOOP;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitWhileLoopStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterWhileLoopStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitWhileLoopStatement(this);
  }
}
