package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.query.Root;

public abstract class ConditionLoopStatement extends LoopStatement {
  protected Expression condition;

  public ConditionLoopStatement(Statement statement, Expression condition) {
    super(statement);
    this.condition = setup(condition, this::setCondition);
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    updateParents(this.condition, condition, this::setCondition);
    this.condition = condition;
  }

  @Override
  public ConditionLoopStatement clone() {
    var clone = (ConditionLoopStatement) super.clone();
    clone.cloneChild(condition, clone::setCondition);
    return clone;
  }

  @Override
  public ConditionLoopStatement cloneInto(Root root) {
    return (ConditionLoopStatement) super.cloneInto(root);
  }

  @Override
  public ConditionLoopStatement cloneSeparate() {
    return (ConditionLoopStatement) super.cloneSeparate();
  }
}
