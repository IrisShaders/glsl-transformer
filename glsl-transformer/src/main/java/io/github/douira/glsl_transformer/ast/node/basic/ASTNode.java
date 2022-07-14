package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.function.Consumer;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ASTNode {
  /**
   * is only null for the root node or before being added to a parent
   * invariant: the parent of this node has to share the same root as this node
   */
  private ASTNode parent;

  /**
   * a reference to the method of the parent that would overwrite this node
   * useful for replacing this node within it's parent without searching for it
   * Invariant: always refers to a method in the currently set parent.
   * Null if the parent is also null.
   */
  private Consumer<ASTNode> parentSetter;

  // should never be null
  private Root root = Root.getActiveBuildRoot();

  public ASTNode() {
  }

  public abstract <R> R accept(ASTVisitor<R> visitor);

  public ASTNode getParent() {
    return parent;
  }

  public Consumer<ASTNode> getParentSetter() {
    return parentSetter;
  }

  public boolean replaceInParent(ASTNode replacement) {
    if (parentSetter != null) {
      parentSetter.accept(replacement);
      return true;
    }
    return false;
  }

  public ASTNode getNthParent(int n) {
    ASTNode node = this;
    for (int i = 0; i < n; i++) {
      if (node == null) {
        return null;
      }
      node = node.getParent();
    }
    return node;
  }

  public ASTNode getFirstOfType(int limit, Class<? extends ASTNode> type) {
    if (this.getClass() == type) {
      return this;
    }
    return getFirstOfType(limit, type);
  }

  public ASTNode getFirstParentOfType(int limit, Class<? extends ASTNode> type) {
    ASTNode node = this;
    for (int i = 0; i < limit; i++) {
      if (node == null) {
        return null;
      }
      node = node.getParent();
      if (node.getClass() == type) {
        return node;
      }
    }
    return null;
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

  class ChangeRootVisitor extends ASTVoidVisitor {
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
  @SuppressWarnings("unchecked") // we rely on our construction doing the right thing
  public boolean setParent(ASTNode parent, Consumer<? extends ASTNode> setter) {
    if (parent == null) {
      throw new IllegalArgumentException(
          "parent cannot be set to null");
    }

    // if the parent doesn't change, nothing has to be done
    if (this.parent == parent) {
      return false;
    }

    // if the roots are the same nothing important happens
    // this is the normal case for building the AST or moving nodes around
    if (root == parent.root) {
      this.parent = parent;
      this.parentSetter = (Consumer<ASTNode>) setter;
      return true;
    }

    // in this case, we have to adapt the root of this subtree to the root of the
    // new parent since this subtree is now part of a different tree
    parent.root.merge(root);

    // not unregistering from the previous root because it's being discarded
    this.parent = parent;
    this.parentSetter = (Consumer<ASTNode>) setter;
    new ChangeRootVisitor(parent.root).visit(this);

    return true;
  }

  class UnregisterVisitor extends ASTVoidVisitor {
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
  public void detachFromParent() {
    if (parent == null) {
      return;
    }
    new UnregisterVisitor().visit(this);
    parent = null;
  }

  public <NodeType extends ASTNode> NodeType setup(
      NodeType node,
      Consumer<? extends NodeType> setter) {
    if (node != null) {
      node.setParent(this, setter);
      root.registerChild(node);
    }
    return node;
  }

  public <NodeType extends ASTNode> void updateParents(
      NodeType currentNode,
      NodeType newNode,
      Consumer<? extends NodeType> setter) {
    if (currentNode == newNode) {
      return;
    }

    if (currentNode != null) {
      currentNode.detachFromParent();
    }

    if (newNode != null) {
      newNode.setParent(this, setter);
    }
  }
}
