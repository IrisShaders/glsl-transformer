package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class GroupingExpression extends UnaryExpression {
  public GroupingExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitGroupingExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterGroupingExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitGroupingExpression(this);
  }
}
