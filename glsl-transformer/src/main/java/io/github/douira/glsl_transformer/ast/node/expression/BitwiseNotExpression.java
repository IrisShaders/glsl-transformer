package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class BitwiseNotExpression extends UnaryExpression {
  public BitwiseNotExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBitwiseNotExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBitwiseNotExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBitwiseNotExpression(this);
  }
}
