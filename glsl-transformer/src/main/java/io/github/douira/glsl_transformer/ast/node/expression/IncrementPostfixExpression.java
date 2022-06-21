package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class IncrementPostfixExpression extends UnaryExpression {
  public IncrementPostfixExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    // TODO Auto-generated method stub
    return super.accept(visitor);
  }

  @Override
  public void enterNode(ASTListener listener) {
    // TODO Auto-generated method stub
    super.enterNode(listener);
  }

  @Override
  public void exitNode(ASTListener listener) {
    // TODO Auto-generated method stub
    super.exitNode(listener);
  }
}
