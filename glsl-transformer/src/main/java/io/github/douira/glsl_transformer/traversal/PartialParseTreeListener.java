package io.github.douira.glsl_transformer.traversal;

import org.antlr.v4.runtime.tree.ParseTreeListener;

import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * Adds a method to the parse tree listener with which the parse tree walker can
 * detect if it should stop walking the tree.
 */
public interface PartialParseTreeListener extends ParseTreeListener {
  /**
   * Returns if this parse tree listener is no longer interested in walking the
   * tree.
   * 
   * @apiNote This method will be called at every level of the tree as the parse
   *          tree walker decides whether or not to continue iterating the
   *          children of each node.
   * 
   * @return {@code true} if the parse tree walker should stop walking the tree
   *         entirely.
   */
  default boolean isFinished() {
    return false;
  }

  /**
   * Returns if this parse tree listener wants to continue to walk the tree but
   * not go any deeper into the current (given) rule.
   * 
   * @param node The current node in which to maybe not visit the children
   * @return {@code true} if the parse tree walker
   */
  default boolean isDeepEnough(ExtendedContext node) {
    return false;
  }
}
