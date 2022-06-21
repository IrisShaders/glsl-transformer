package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class IdentityExpression extends UnaryExpression {
  public IdentityExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitIdentityExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterIdentityExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitIdentityExpression(this);
  }
}
