package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class MethodCallExpression extends UnaryExpression {
  public InnerASTNode methodCall; // TODO: MethodCall

  public MethodCallExpression(Expression operand, InnerASTNode methodCall) {
    super(operand);
    this.methodCall = methodCall;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.METHOD_CALL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitMethodCallExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterMethodCallExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitMethodCallExpression(this);
  }
}
