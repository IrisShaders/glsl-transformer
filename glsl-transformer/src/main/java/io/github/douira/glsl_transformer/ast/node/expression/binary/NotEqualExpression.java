package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class NotEqualExpression extends BinaryExpression {
  public NotEqualExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.NOT_EQUAL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitNotEqualExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterNotEqualExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitNotEqualExpression(this);
  }
}
