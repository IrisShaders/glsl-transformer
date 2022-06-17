package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.GeneralASTVisitor;

public abstract class ASTNode {
  private ASTNode parent;

  public ASTNode getParent() {
    return parent;
  }

  public void setParent(ASTNode parent) {
    this.parent = parent;
  }

  public <R> R accept(GeneralASTVisitor<? extends R> visitor) {
    return visitor.visitDefault(this);
  }
}
