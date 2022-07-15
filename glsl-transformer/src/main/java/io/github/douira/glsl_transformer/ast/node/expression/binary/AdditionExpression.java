package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class AdditionExpression extends BinaryExpression {
  public AdditionExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.ADDITION;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitAdditionExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterAdditionExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitAdditionExpression(this);
  }
}
