package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class MemberAccessExpression extends UnaryExpression {
  public String memberName;

  public MemberAccessExpression(Expression expression, String member) {
    super(expression);
    this.memberName = member;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.MEMBER_ACCESS;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitMemberAccessExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterMemberAccessExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitMemberAccessExpression(this);
  }
}
