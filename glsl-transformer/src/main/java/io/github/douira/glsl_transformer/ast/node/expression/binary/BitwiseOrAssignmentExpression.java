package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BitwiseOrAssignmentExpression extends BinaryExpression {
  public BitwiseOrAssignmentExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.BITWISE_OR_ASSIGNMENT;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBitwiseOrAssignmentExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBitwiseOrAssignmentExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBitwiseOrAssignmentExpression(this);
  }
}
