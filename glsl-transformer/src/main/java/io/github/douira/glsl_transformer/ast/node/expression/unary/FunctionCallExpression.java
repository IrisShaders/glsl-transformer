package io.github.douira.glsl_transformer.ast.node.expression.unary;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.TerminalExpression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionCallExpression extends TerminalExpression {
  protected InnerASTNode functionCall; // TODO: FunctionCall

  public FunctionCallExpression(InnerASTNode functionCall) {
    this.functionCall = setup(functionCall);
  }

  public InnerASTNode getFunctionCall() {
    return functionCall;
  }

  public void setFunctionCall(InnerASTNode functionCall) {
    updateParents(this.functionCall, functionCall);
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
    super.enterNode(listener);
    listener.enterFunctionCallExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitFunctionCallExpression(this);
  }
}
