package io.github.douira.glsl_transformer.ast;

public abstract class ASTNode {
  private ASTNode parent;

  public ASTNode getParent() {
    return parent;
  }

  public void setParent(ASTNode parent) {
    this.parent = parent;
  }

  public abstract <R> R accept(ASTVisitor<? extends R> visitor);
}
