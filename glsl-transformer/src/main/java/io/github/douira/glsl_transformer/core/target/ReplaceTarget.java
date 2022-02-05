package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A replacement target searches for a search string and uses a method to
 * generate a tree member to replace it in the tree. If something more
 * than node replacement should be done, implement a custom
 * {@link HandlerTargetImpl} subclass.
 */
public abstract class ReplaceTarget<T> extends HandlerTargetImpl<T> {
  /**
   * Creates a new replace target with a given search string
   * 
   * @param needle The search string
   */
  public ReplaceTarget(String needle) {
    super(needle);
  }

  /**
   * Creates a new replace target with no search string. The {@link #getNeedle()}
   * method should be overwritten if this constructor is used.
   * 
   * @see io.github.douira.glsl_transformer.core.target.HandlerTargetImpl#HandlerTargetImpl()
   */
  protected ReplaceTarget() {
  }

  /**
   * Returns the node to insert replacing the node found with the search string.
   * The replacement can be cancelled by returning {@code null}.
   * 
   * @param node  The node found to contain the search string
   * @param match The entire content of the node that matches the search string
   * @return The new node to replace the found node with
   */
  protected abstract TreeMember getReplacement(TreeMember node, String match);

  @Override
  public void handleResult(TreeMember node, String match) {
    var newNode = getReplacement(node, match);
    if (newNode != null) {
      replaceNode(node, newNode);
    }
  }
}
