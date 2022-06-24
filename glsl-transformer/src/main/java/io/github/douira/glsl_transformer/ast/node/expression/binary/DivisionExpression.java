package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class DivisionExpression extends BinaryExpression {
  public DivisionExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.DIVISION;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitDivisionExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterDivisionExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitDivisionExpression(this);
  }
}
