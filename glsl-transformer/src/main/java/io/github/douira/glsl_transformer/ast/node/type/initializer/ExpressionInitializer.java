package io.github.douira.glsl_transformer.ast.node.type.initializer;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ExpressionInitializer extends Initializer {
  protected Expression expression;

  public ExpressionInitializer(Expression expression) {
    this.expression = setup(expression);
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    updateParents(this.expression, expression);
    this.expression = expression;
  }

  @Override
  public InitializerType getInitializerType() {
    return InitializerType.EXPRESSION;
  }

  @Override
  public <R> R initializerAccept(ASTVisitor<R> visitor) {
    return visitor.visitExpressionInitializer(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterExpressionInitializer(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitExpressionInitializer(this);
  }
}
