package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class DecrementPostfixExpression extends UnaryExpression {
  public DecrementPostfixExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.DECREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitDecrementPostfixExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterDecrementPostfixExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitDecrementPostfixExpression(this);
  }
}