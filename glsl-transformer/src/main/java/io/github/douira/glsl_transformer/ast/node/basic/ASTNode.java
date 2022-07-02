package io.github.douira.glsl_transformer.ast.node.basic;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public abstract class ASTNode implements NodeTracker {
  private ASTNode parent;
  private Root root; //TODO: make this be the collector root during a build

  public ASTNode() {
  }

  public abstract <R> R accept(ASTVisitor<R> visitor);

  public ASTNode getParent() {
    return parent;
  }

  @Override
  public void registerChild(ASTNode child) {
    root.registerChild(child);
  }

  @Override
  public void unregisterChild(ASTNode child) {
    root.unregisterChild(child);
  }

  /**
   * Sets the parent of this node and handles registration.
   * 
   * TODO: also detect if the parent is essentially the same meaning part of the
   * same tree (but not the actual same)
   * 
   * @param parent The parent value to set, can be null.
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  public boolean setParent(ASTNode parent) {
    if (this.parent == parent) {
      return false;
    }
    if (this.parent != null) {
      this.parent.unregisterChild(this);
    }
    this.parent = parent;
    if (this.parent != null) {
      this.parent.registerChild(this);
    }
    return true;
  }

  public boolean unsetParent() {
    return setParent(null);
  }

  public <T extends ASTNode> T setup(T node) {
    node.setParent(this);
    return node;
  }
}
