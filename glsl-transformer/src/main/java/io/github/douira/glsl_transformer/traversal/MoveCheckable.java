package io.github.douira.glsl_transformer.traversal;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The move checkable interface provides a method for checking if the
 * implementing node replaces a given node. This is helpful for the dynamic
 * parse tree walker in determining if the current node it was looking at has
 * moved or was replaced by an empty terminal node.
 * 
 * All nodes that are not terminal nodes with tokens should implement this
 * interface in the parse tree.
 */
public interface MoveCheckable {
  /**
   * Returns the node that this node replaced during a tree transformation.
   * 
   * @return The replaced node
   */
  ParseTree getPreviousNode();

  /**
   * Allows the transformation phase to tell this node which node it replaces.
   * 
   * @param previousNode The node to set as the previous node in this position in
   *                     the parent's child array that this node replaces
   */
  void setPreviousNode(ParseTree previousNode);

  /**
   * Checks if any given parse tree node replaces another. If the nodes aren't the
   * same, it checks recursively if the previous node of the new node maybe
   * replaces the old node. This chain must end at some point or something is
   * wrong.
   * 
   * @param oldNode The node having maybe been replaced
   * @param newNode The node that could be the replacement
   * @return {@code true} if the new node replaced the old node
   */
  static boolean replaces(ParseTree oldNode, ParseTree newNode) {
    return oldNode == newNode
        || newNode instanceof MoveCheckable checkable && replaces(oldNode, checkable.getPreviousNode());
  }
}
