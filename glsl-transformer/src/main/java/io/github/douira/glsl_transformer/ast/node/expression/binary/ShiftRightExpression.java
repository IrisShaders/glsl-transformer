package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ShiftRightExpression extends BinaryExpression {
  public ShiftRightExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.SHIFT_RIGHT;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitShiftRightExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterShiftRightExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitShiftRightExpression(this);
  }
}
