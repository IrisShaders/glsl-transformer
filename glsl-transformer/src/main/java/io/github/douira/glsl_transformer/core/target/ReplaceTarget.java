package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A replacement target searches for a search string and uses a method to
 * generate a tree member to replace it in the tree. If something more
 * than node replacement should be done, implement a custom
 * {@link HandlerTarget} subclass.
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

  @Override
  public void handleResult(TreeMember node, String match) {
    var newNode = getReplacement(node, match);
    if (newNode != null) {
      replaceNode(node, newNode);
    }
  }

  /**
   * Returns the node to insert replacing the node found with the search string.
   * The replacement can be cancelled by returning {@code null}.
   * 
   * @param node  The node found to contain the search string
   * @param match The entire content of the node that matches the search string
   * @return The new node to replace the found node with
   */
  public abstract TreeMember getReplacement(TreeMember node, String match);
}
