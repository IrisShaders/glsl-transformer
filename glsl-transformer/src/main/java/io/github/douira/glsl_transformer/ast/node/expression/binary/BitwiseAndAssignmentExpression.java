package io.github.douira.glsl_transformer.ast.node.expression.binary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class BitwiseAndAssignmentExpression extends BinaryExpression {
  public BitwiseAndAssignmentExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.BITWISE_AND_ASSIGNMENT;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBitwiseAndAssignmentExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBitwiseAndAssignmentExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBitwiseAndAssignmentExpression(this);
  }
}