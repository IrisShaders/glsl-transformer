package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ArrayAccessExpression extends BinaryExpression {
  public ArrayAccessExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.ARRAY_ACCESS;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitArrayAccessExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterArrayAccessExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitArrayAccessExpression(this);
  }
}
