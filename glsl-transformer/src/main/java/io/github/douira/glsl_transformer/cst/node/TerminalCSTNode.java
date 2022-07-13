package io.github.douira.glsl_transformer.cst.node;

import io.github.douira.glsl_transformer.tree.ExtendedTerminalNode;

/**
 * The CST node is a terminal node that can be printed with a method implemented
 * by subclasses. The result of this method is used by other abstract CST
 * classes to re-insert the result of an CST manipulation back into the parse
 * tree.
 */
public abstract class TerminalCSTNode extends ExtendedTerminalNode {
  /**
   * Generates the string representation of this CST object as it would be in the
   * generated code.
   * 
   * @return The printed string for this CST object
   */
  protected abstract String getPrinted();
}
