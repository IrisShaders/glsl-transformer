package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.TerminalExpression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionCallExpression extends TerminalExpression {
  public InnerASTNode functionCall; // TODO: FunctionCall

  public FunctionCallExpression(InnerASTNode functionCall) {
    super();
    this.functionCall = functionCall;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.FUNCTION_CALL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionCallExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFunctionCallExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFunctionCallExpression(this);
  }
}
