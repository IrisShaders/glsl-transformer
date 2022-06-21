package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class ConditionExpression extends TernaryExpression {
  public ConditionExpression(Expression condition, Expression trueExpression, Expression falseExpression) {
    super(condition, trueExpression, falseExpression);
  }

  public Expression getCondition() {
    return first;
  }

  public Expression getTrueExpression() {
    return second;
  }

  public Expression getFalseExpression() {
    return third;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.CONDITION;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitConditionExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterConditionExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitConditionExpression(this);
  }
}
