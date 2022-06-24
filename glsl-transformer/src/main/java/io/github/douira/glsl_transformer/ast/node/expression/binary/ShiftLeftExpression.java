package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ShiftLeftExpression extends BinaryExpression {
  public ShiftLeftExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.SHIFT_LEFT;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitShiftLeftExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterShiftLeftExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitShiftLeftExpression(this);
  }
}
