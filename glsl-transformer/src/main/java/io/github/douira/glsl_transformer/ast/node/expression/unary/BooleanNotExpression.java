package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BooleanNotExpression extends UnaryExpression {
  public BooleanNotExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.BOOLEAN_NOT;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBooleanNotExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBooleanNotExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBooleanNotExpression(this);
  }
}
