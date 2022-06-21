package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class NegationExpression extends UnaryExpression {
  public NegationExpression(Expression expression) {
    super(expression);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.INCREMENT_POSTFIX;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitNegationExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterNegationExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitNegationExpression(this);
  }
}
