package io.github.douira.glsl_transformer.ast;

public abstract class ASTNode {
  private ASTNode parent;

  public abstract void enterNode();

  public abstract void exitNode();

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
