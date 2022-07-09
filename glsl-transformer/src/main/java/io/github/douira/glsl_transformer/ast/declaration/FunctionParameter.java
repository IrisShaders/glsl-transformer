package io.github.douira.glsl_transformer.ast.declaration;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class FunctionParameter extends InnerASTNode {
  public enum ParameterKind {
    NAMED,
    FULLY_SPECIFIED_TYPE
  }

  public abstract ParameterKind getParameterKind();

  public abstract <R> R functionParameterAccept(ASTVisitor<R> visitor);

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        visitor.visitFunctionParameter(this),
        functionParameterAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFunctionParameter(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFunctionParameter(this);
  }
}
