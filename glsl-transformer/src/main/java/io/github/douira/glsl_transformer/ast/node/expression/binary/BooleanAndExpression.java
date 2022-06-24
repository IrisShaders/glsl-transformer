package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BooleanAndExpression extends BinaryExpression {
  public BooleanAndExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.BOOLEAN_AND;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBooleanAndExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBooleanAndExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBooleanAndExpression(this);
  }
}
