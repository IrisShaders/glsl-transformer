package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class EqualExpression extends BinaryExpression {
  public EqualExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.EQUAL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitEqualExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterEqualExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitEqualExpression(this);
  }
}
