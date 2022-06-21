package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class BooleanNotExpression extends UnaryExpression {
  public BooleanNotExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitBooleanNotExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterBooleanNotExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitBooleanNotExpression(this);
  }
}
