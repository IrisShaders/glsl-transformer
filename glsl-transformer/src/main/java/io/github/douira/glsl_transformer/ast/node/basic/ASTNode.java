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

  public ASTNode getFirstOfType(int limit, Class<? extends ASTNode> type) {
    if (this.getClass() == type) {
      return this;
    }
    return getFirstOfType(limit, type);
  }

  public ASTNode getFirstOfType(Class<? extends ASTNode> type) {
    return getFirstOfType(Integer.MAX_VALUE, type);
  }

  public ASTNode getFirstParentOfType(Class<? extends ASTNode> type) {
    return getFirstParentOfType(Integer.MAX_VALUE, type);
  }

  public ASTNode getFirstParentOfType(int limit, Class<? extends ASTNode> type) {
    ASTNode node = this;
    for (int i = 0; i < limit; i++) {
      if (node == null || type.isInstance(node)) {
        return node;
      }
      node = node.getParent();
    }
    return node;
  }

  public Root getRoot() {
    return root;
  }

  private void adoptNewRoot(Root root) {
    if (this.root != null && registered) {
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
   * Sets the parent of this node and handles registration. It is assumed that
   * this node is removed from the old parent and added to the new one before or
   * after executing this method.
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

    // in this case, we have to adapt the root of this subtree to the root of the
    // new parent since this subtree is now part of a different tree
    parent.root.merge(root);

    // not unregistering from the previous root because it's being discarded
    this.parent = parent;
    this.selfReplacer = (Consumer<ASTNode>) setter;
    new ChangeRootVisitor(parent.root).visit(this);

    return true;
  }

  public boolean replaceBy(ASTNode replacement) {
    if (selfReplacer != null) {
      selfReplacer.accept(replacement);
      unregisterFromParent();
      return true;
    }
    return false;
  }

  /**
   * Removes a node from its parent by using the stored self replacer function. If
   * the node can be removed from the parent in a more efficient way, especially
   * in the case of node lists, use that method instead and then just call
   * ${@link #unregisterFromParent()} afterwards.
   * 
   * Calling this method is often not necessary at all since all interfaces with
   * the AST tree will automatically remove nodes from whereever they are stored.
   * 
   * @return {@code true} if the node was removed, {@code false} otherwise.
   */
  public boolean deleteFromParent() {
    return replaceBy(null);
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
   * {@link ASTNode#setParent(ASTNode, Consumer)} to move a subtree from one node
   * to the other.
   * 
   * Doesn't unregister from root if there is no parent since then there are no
   * other nodes that have access to this root and as such unregistering all nodes
   * from a root makes no sense.
   * 
   * @return {@code true} if there was unregistering, {@code false} otherwise.
   */
  public boolean unregisterFromParent() {
    if (parent == null) {
      return false;
    }
    new UnregisterVisitor().visit(this);
    parent = null;
    selfReplacer = null;
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
      currentNode.unregisterFromParent();
    }

    if (newNode != null) {
      newNode.setParent(this, setter);
    }
  }
}
