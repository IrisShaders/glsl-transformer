package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.util.CompatUtil;

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
 * 
 * While it is possible to have a single node appear multiple times throughout
 * the tree (at which point the tree becomes a DAG), this is discuraged since it
 * violates common expectations like the fact that removing a node doesn't also
 * affect other parts of the tree.
 */
public abstract class ASTNode implements Cloneable {
  private ASTNode parent;
  private Consumer<ASTNode> selfReplacer;
  private Root root = Root.getActiveBuildRoot();

  /**
   * Whether this node has been registered with the root. This is only used when
   * constructing nodes. The {@link #setParent(ASTNode)} method does not check
   * this recursively.
   */
  private boolean registered = false;

  public ASTNode() {
  }

  public abstract <R> R accept(ASTVisitor<R> visitor);

  public ASTNode getParent() {
    return parent;
  }

  public boolean hasParent() {
    return parent != null;
  }

  public Consumer<ASTNode> getParentSetter() {
    return selfReplacer;
  }

  /**
   * Gets the nth parent of this node. The 0th parent is this node. The 1st parent
   * is the parent of this node.
   * 
   * @param n the number of parents to go up
   * @return the nth parent of this node
   */
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

  /**
   * Checks if there is an ancestor of this node that fulfills the given
   * predicate within a limited nubmer of steps after a number of steps skipped.
   * If the limit is 0, it will only check the current node.
   * 
   * @param limit     the number of parents to check in total
   * @param skip      the number of parents to skip before checking the predicate
   * @param predicate the predicate to check
   * @return true if there is an ancestor of this node that fulfills the given
   *         predicate, false otherwise
   */
  public boolean hasAncestor(int limit, int skip, Predicate<ASTNode> predicate) {
    ASTNode node = this;
    for (int i = 0; i <= limit; i++) {
      if (node == null) {
        return false;
      }
      if (i >= skip && predicate.test(node)) {
        return true;
      }
      node = node.getParent();
    }
    return false;
  }

  /**
   * Checks if there is an ancestor of this node that fulfills the given
   * predicate.
   * 
   * @param predicate the predicate to check
   * @return true if there is an ancestor of this node that fulfills the predicate
   */
  public boolean hasAncestor(Predicate<ASTNode> predicate) {
    return hasAncestor(Integer.MAX_VALUE, 0, predicate);
  }

  /**
   * Checks if there is an ancestor of this node that is an instance of the given
   * class.
   * 
   * @param clazz the class to check
   * @return true if there is an ancestor that is an instance of the given class
   */
  public boolean hasAncestor(Class<? extends ASTNode> clazz) {
    return hasAncestor(clazz::isInstance);
  }

  /**
   * Checks if the given node is an ancestor of this node.
   * 
   * @param node the node to check
   * @return true if the given node is an ancestor of this node
   */
  public boolean hasAncestor(ASTNode node) {
    return hasAncestor(node::equals);
  }

  /**
   * Returns the first ancestor that fulfills the given predicate, limited to a
   * certain number of steps. If the limit is 0, it will only check the current
   * node.
   * 
   * @param limit     the number of parents to check in total
   * @param skip      the number of steps to skip before checking the predicate
   * @param predicate the predicate to check
   * @return the first ancestor that fulfills the given predicate, or null
   *         otherwise
   */
  public ASTNode getAncestor(int limit, int skip, Predicate<ASTNode> predicate) {
    ASTNode node = this;
    for (int i = 0; i <= limit; i++) {
      if (node == null) {
        return null;
      }
      if (i >= skip && predicate.test(node)) {
        return node;
      }
      node = node.getParent();
    }
    return null;
  }

  /**
   * Returns the first ancestor that fulfills the given predicate.
   * 
   * @param predicate the predicate to check
   * @return the first ancestor that fulfills the given predicate, or null
   *         otherwise
   */
  public ASTNode getAncestor(Predicate<ASTNode> predicate) {
    return getAncestor(Integer.MAX_VALUE, 0, predicate);
  }

  /**
   * Returns the first ancestor that is an instance of the given class.
   * 
   * @param clazz the class to check
   * @return the first ancestor that is an instance of the given class, or null
   *         otherwise
   */
  public <T extends ASTNode> T getAncestor(Class<T> clazz) {
    return clazz.cast(getAncestor(clazz::isInstance));
  }

  public ASTNode getBranchAncestor(int limit, int skip, BiPredicate<ASTNode, ASTNode> predicate) {
    ASTNode node = this;
    ASTNode last = null;
    for (int i = 0; i <= limit; i++) {
      if (node == null) {
        return null;
      }
      if (i >= skip && predicate.test(node, last)) {
        return node;
      }
      last = node;
      node = node.getParent();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public <T extends ASTNode> T getBranchAncestor(int limit, int skip, Class<T> branchClass,
      Function<T, ? extends ASTNode> branchGetter) {
    return (T) getBranchAncestor(limit, skip, (node, last) -> {
      if (!branchClass.isInstance(node)) {
        return false;
      }
      return branchGetter.apply(branchClass.cast(node)) == last;
    });
  }

  public <T extends ASTNode> T getBranchAncestor(Class<T> branchClass,
      Function<T, ? extends ASTNode> branchGetter) {
    return getBranchAncestor(Integer.MAX_VALUE, 0, branchClass, branchGetter);
  }

  public <T extends ASTNode, R extends ASTNode> R getBranchAncestorContinue(Class<T> branchClass,
      Function<T, ? extends ASTNode> branchGetter, Class<R> continueClass) {
    var result = getBranchAncestor(branchClass, branchGetter);
    return result == null ? null : result.getAncestor(continueClass);
  }

  /**
   * Returns a lazy stream of all ancestors of this node.
   * 
   * @return a stream of the ancestors
   */
  public Stream<ASTNode> getAncestors() {
    return CompatUtil.iterateStream(this, ASTNode::hasParent, ASTNode::getParent);
  }

  /**
   * Returns the root of this node.
   * 
   * @return the root
   */
  public Root getRoot() {
    return root;
  }

  private static class ChangeRootVisitor extends ASTVoidVisitor {
    private Root root;

    public ChangeRootVisitor(Root root) {
      this.root = root;
    }

    @Override
    public void visitVoid(ASTNode node) {
      node.setRoot(root);
    }
  }

  private static class UnregisterVisitor extends ASTVoidVisitor {
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
    root.unregisterNode(this);
    registered = false;
  }

  private void register() {
    root.registerNode(this);
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

    // always set the self replacer since the node might have moved inside its
    // parent without changing the parent
    this.selfReplacer = (Consumer<ASTNode>) setter;

    // if the parent doesn't change, nothing has to be done
    if (this.parent == parent) {
      return false;
    }

    // if the roots are the same nothing important happens
    // this is the normal case for building the AST or moving nodes around
    if (root == parent.root) {
      this.parent = parent;

      // when the root node of a newly built subtree that already has the same root
      // references is added to the main tree, only the root node isn't registered yet
      if (!registered) {
        register();
      }
      return true;
    }

    this.parent = parent;
    changeRootRecursive(parent.root);
    return true;
  }

  private void changeRootRecursive(Root root) {
    new ChangeRootVisitor(root).visit(this);
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

  /**
   * Updates the parents of two nodes where one is replacing the other in this
   * node. Both of them may be null if either the existing value is being removed
   * or a new value is being set.
   * 
   * @param <NodeType>  The type of the nodes for pass-through
   * @param currentNode The current node
   * @param newNode     The new node
   * @param setter      The setter to replace the node in this parent (this is
   *                    usually a method reference to a setter method of the
   *                    parent, this node)
   */
  public <NodeType extends ASTNode> void updateParents(
      NodeType currentNode,
      NodeType newNode,
      Consumer<? extends NodeType> setter) {
    if (currentNode == newNode && newNode.getParent() == this) {
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
