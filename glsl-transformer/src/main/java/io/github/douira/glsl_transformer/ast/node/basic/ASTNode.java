package io.github.douira.glsl_transformer.ast.node.basic;

import io.github.douira.glsl_transformer.ast.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ASTNode {
  // is only null for the root node or before being added to a parent
  // invariant: if this is set it must contain the same root as this node
  private ASTNode parent;

  // should never be null
  private Root root = Root.getActiveBuildRoot();

  public ASTNode() {
  }

  public abstract <R> R accept(ASTVisitor<R> visitor);

  public ASTNode getParent() {
    return parent;
  }

  public Root getRoot() {
    return root;
  }

  private void adoptNewRoot(Root root) {
    this.root = root;
    root.registerChild(this);
  }

  private void unregister() {
    root.unregisterChild(this);
  }

  class ChangeRootVisitor implements ASTVisitor<Void>, ASTVoidVisitor {
    private Root root;

    public ChangeRootVisitor(Root root) {
      this.root = root;
    }

    @Override
    public void visitVoid(ASTNode node) {
      node.adoptNewRoot(root);
    }
  }

  /**
   * Sets the parent of this node and handles registration.
   * 
   * @param parent The parent value to set, cannot be null.
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  public boolean setParent(ASTNode parent) {
    if (parent == null) {
      throw new IllegalArgumentException(
          "parent cannot be set to null");
    }

    // if the parent doesn't change nothing has to be done
    if (this.parent == parent) {
      return false;
    }

    // if the roots are the same nothing important happens
    if (root == parent.root) {
      this.parent = parent;
      return true;
    }

    // in this case, we have to adapt the root of this subtree to the root of the
    // new parent since this subtree is now part of a different tree
    parent.root.merge(root);

    // not unregistering from the previous root because it's being discarded
    this.parent = parent;
    new ChangeRootVisitor(parent.root).visit(this);

    return true;
  }

  class UnregisterVisitor implements ASTVisitor<Void>, ASTVoidVisitor {
    @Override
    public void visitVoid(ASTNode node) {
      node.unregister();
    }
  }

  /**
   * To be called after removing this node from its parent. This unregisters it
   * and all its children from the parent's root. Use a simple
   * {@link ASTNode#setParent(ASTNode)} to move a subtree from one node to the
   * other.
   */
  public void unregisterFromParent() {
    if (parent == null) {
      return;
    }
    new UnregisterVisitor().visit(this);
  }

  public <T extends ASTNode> T setup(T node) {
    node.setParent(this);
    return node;
  }
}
