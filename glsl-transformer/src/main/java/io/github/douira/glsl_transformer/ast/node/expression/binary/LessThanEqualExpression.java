package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class LessThanEqualExpression extends BinaryExpression {
  public LessThanEqualExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.LESS_THAN_EQUAL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitLessThanEqualExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterLessThanEqualExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitLessThanEqualExpression(this);
  }
}
