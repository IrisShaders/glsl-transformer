package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class DecrementPrefixExpression extends UnaryExpression {
  public DecrementPrefixExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitDecrementPrefixExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterDecrementPrefixExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitDecrementPrefixExpression(this);
  }
}
