package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BooleanXorExpression extends BinaryExpression {
  public BooleanXorExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.BOOLEAN_XOR;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBooleanXorExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBooleanXorExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBooleanXorExpression(this);
  }
}
