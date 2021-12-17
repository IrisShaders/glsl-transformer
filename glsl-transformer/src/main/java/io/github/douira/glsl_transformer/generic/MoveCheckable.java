package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The move checkable interface provides a method for checking if the
 * implementing node replaces a given node. This is helpful for the dynamic
 * parse tree walker in determining if the current node is was looking at has
 * moved or was replaced by an empty terminal node.
 * 
 * All nodes that are not terminal nodes with tokens should implement this
 * interface in the parse tree.
 */
public interface MoveCheckable {
  /**
   * Checks if the implementing object was inserted into the tree to replace the
   * given node. This is used to check if an empty terminal node replaced this
   * node in the parse tree.
   * 
   * @param node The node to check for having been replaced by this node
   * @return {@code true} if this node replaced the given node
   */
  public boolean replacesNode(ParseTree node);

  /**
   * Checks if any given parse tree node replaces another. This also deals with
   * some nodes being regular terminal nodes and maybe not implementing this
   * interface. Otherwise it does the same thing as
   * {@link #replacesNode(ParseTree)}.
   * 
   * @param oldNode The node having maybe been replaced
   * @param newNode The node that could be the replacement
   * @return {@code true} if the new node replaced the old node
   */
  public static boolean replaces(ParseTree oldNode, ParseTree newNode) {
    if (newNode instanceof MoveCheckable checkable) {
      return checkable.replacesNode(oldNode);
    } else {
      return false;
    }
  }
}
