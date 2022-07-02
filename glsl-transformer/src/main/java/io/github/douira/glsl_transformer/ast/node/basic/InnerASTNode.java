package io.github.douira.glsl_transformer.ast.node.basic;

import io.github.douira.glsl_transformer.ast.traversal.ASTListener;

public abstract class InnerASTNode extends ASTNode {
  public abstract void enterNode(ASTListener listener);

  public abstract void exitNode(ASTListener listener);
}
