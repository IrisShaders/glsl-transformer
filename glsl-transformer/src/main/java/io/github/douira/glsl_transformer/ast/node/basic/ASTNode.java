package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.Objects;
import java.util.function.Consumer;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

/**
 * The AST node represents a node in the abstract syntax tree. Each AST node has
 * a reference to the shared root object that contains the indexes for querying
 * the tree.
 * 
 * Invariants:
 * 1. The root must contain exactly the nodes that are accessible by
 * traversing the tree downwards at any time.
 * 2. Each contained node must have a reference to the root node it is part of.
 * 3. The node must have a reference to its parent if it's not the root of the
 * tree and it must have the same root reference as its parent.
 * 4. The selfReplacer function replaces the current node in the referenced
 * parent with a new one.
 * 5. An AST node may only ever be in a tree once. Attempting to insert it
 * multiple times will cause undefined behavior. Moving a node requires removing
 * it from one parent and then adding it to another.
 */
public abstract class ASTNode {
  private ASTNode parent;
  private Consumer<ASTNode> selfReplacer;
  private Root root = Root.getActiveBuildRoot();
  private boolean registered = false;

  public ASTNode() {
  }

  public abstract <R> R accept(ASTVisitor<R> visitor);

  public ASTNode getParent() {
    return parent;
  }

  public Consumer<ASTNode> getParentSetter() {
    return selfReplacer;
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

  public boolean hasAncestor(ASTNode ancestor) {
    ASTNode node = this;
    while (node != null) {
      if (node == ancestor) {
        return true;
      }
      node = node.getParent();
    }
    return false;
  }

  public ASTNode getFirstOfType(int limit, Class<? extends ASTNode> type) {
    ASTNode node = this;
    for (int i = 0; i < limit; i++) {
      if (node == null || type.isInstance(node)) {
        return node;
      }
      node = node.getParent();
    }
    return node;
  }

  public ASTNode getFirstOfType(Class<? extends ASTNode> type) {
    return getFirstOfType(Integer.MAX_VALUE, type);
  }

  public ASTNode getAncestorOfType(int limit, Class<? extends ASTNode> type) {
    return parent == null ? null : parent.getFirstOfType(limit - 1, type);
  }

  public ASTNode getAncestorOfType(Class<? extends ASTNode> type) {
    return getFirstOfType(Integer.MAX_VALUE, type);
  }

  public boolean hasAncestorOfType(int limit, Class<? extends ASTNode> type) {
    return getAncestorOfType(limit, type) != null;
  }

  public boolean hasAncestorOfType(Class<? extends ASTNode> type) {
    return getAncestorOfType(type) != null;
  }

  public Root getRoot() {
    return root;
  }

  class ChangeRootVisitor extends ASTVoidVisitor {
    private Root root;

    public ChangeRootVisitor(Root root) {
      this.root = root;
    }

    @Override
    public void visitVoid(ASTNode node) {
      node.setRoot(root);
    }
  }

  class UnregisterVisitor extends ASTVoidVisitor {
    @Override
    public void visitVoid(ASTNode node) {
      node.unregister();
    }
  }

  private void setRoot(Root root) {
    if (this.root == root) {
      return;
    }
    if (registered) {
      unregister();
    }
    this.root = root;
    register();
  }

  private void unregister() {
    root.unregisterChild(this);
    registered = false;
  }

  private void register() {
    root.registerChild(this);
    registered = true;
  }

  /**
   * Sets the parent of this node and handles registration and unregistration of
   * the subtree with the new parent.
   * 
   * @param parent The parent value to set, cannot be null.
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  @SuppressWarnings("unchecked") // we rely on our construction doing the right thing
  public boolean setParent(ASTNode parent, Consumer<? extends ASTNode> setter) {
    Objects.requireNonNull(parent);

    // if the parent doesn't change, nothing has to be done
    if (this.parent == parent) {
      return false;
    }

    // if the roots are the same nothing important happens
    // this is the normal case for building the AST or moving nodes around
    if (root == parent.root) {
      this.parent = parent;
      this.selfReplacer = (Consumer<ASTNode>) setter;

      // when the root node of a newly built subtree that already has the same root
      // references is added to the main tree, only the root node isn't registered yet
      if (!registered) {
        register();
      }
      return true;
    }

    this.parent = parent;
    this.selfReplacer = (Consumer<ASTNode>) setter;
    new ChangeRootVisitor(parent.root).visit(this);
    return true;
  }

  /**
   * Uses the stored replacer function to remove this node from the parent and
   * remove the parent from this node. It does not unregister the subtree.
   * 
   * @param replacement The node to replace this node with
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  public boolean replaceBy(ASTNode replacement) {
    if (selfReplacer != null) {
      selfReplacer.accept(replacement);
      return true;
    }
    return false;
  }

  /**
   * Replaces this node in the parent by the given node and unregisters the
   * subtree. This fully removes the node from the tree.
   * 
   * @param replacement The node to replace this node with
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  public boolean replaceByAndDelete(ASTNode replacement) {
    if (replaceBy(replacement)) {
      unregisterSubtree();
      return true;
    }
    return false;
  }

  /**
   * Detaches a node from its parent by using the stored self replacer function
   * and also removes the parent from this node. Does not unregister the subtree.
   * 
   * @return {@code true} if the node was removed, {@code false} otherwise.
   */
  public boolean detach() {
    // the parent is removed from this node through updateParents()
    return replaceBy(null);
  }

  /**
   * Removes this node in the parent and unregisters the subtree.
   * 
   * @return {@code true} if the parent was changed, {@code false} otherwise.
   */
  public boolean detachAndDelete() {
    return replaceByAndDelete(null);
  }

  /**
   * Removes the parent from this node. This is useful if the node has already
   * been (efficiently) removed from the parent.
   */
  public void detachParent() {
    parent = null;
    selfReplacer = null;
  }

  /**
   * This unregisters this node and all its children from its root.
   * 
   * @return {@code true} if there was unregistering, {@code false} otherwise.
   */
  public boolean unregisterSubtree() {
    detachParent();
    new UnregisterVisitor().visit(this);
    return true;
  }

  /**
   * Swaps two nodes in their parents. Throws if the nodes or their parents are
   * null in which case calling this method is pointless.
   * 
   * @param a The first node to swap
   * @param b The second node to swap
   * @return {@code true} if the nodes were swapped, {@code false} otherwise.
   */
  public static boolean swap(ASTNode a, ASTNode b) {
    Objects.requireNonNull(a);
    Objects.requireNonNull(b);
    var bParent = b.getParent();
    var aParent = a.getParent();
    Objects.requireNonNull(aParent);
    Objects.requireNonNull(bParent);
    if (aParent == b || bParent == a) {
      return false;
    }
    var bReplacer = b.selfReplacer;
    a.replaceBy(b);
    bReplacer.accept(a);
    return true;
  }

  /**
   * Adds a newly constructed node to this node as the parent. The node will
   * already have a reference to the current root but will not be registered to it
   * yet.
   * 
   * @param <NodeType> Type of the node for passthrough
   * @param node       The node to add
   * @param setter     The setter to replace the node in this parent
   * @return The node itself
   */
  public <NodeType extends ASTNode> NodeType setup(
      NodeType node,
      Consumer<? extends NodeType> setter) {
    if (node != null) {
      node.setParent(this, setter);
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
      currentNode.detachParent();
    }

    if (newNode != null) {
      newNode.setParent(this, setter);
    }
  }
}
