package io.github.douira.glsl_transformer.ast;

public abstract class InnerASTNode extends ASTNode {
  public abstract void enterNode(ASTListener listener);

  public abstract void exitNode(ASTListener listener);
}
