package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class MemberAccessExpression extends UnaryExpression {
  public String memberName;

  public MemberAccessExpression(Expression expression, String member) {
    super(expression);
    this.memberName = member;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitMemberAccessExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterMemberAccessExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitMemberAccessExpression(this);
  }
}
