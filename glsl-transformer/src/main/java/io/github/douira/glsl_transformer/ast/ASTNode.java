package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.generic.ExtendedTerminalNode;

/**
 * The AST node is a terminal node that can be printed with a method implemented
 * by subclasses. The result of this method is used by other abstract AST
 * classes to re-insert the result of an AST manipulation back into the parse
 * tree.
 */
public abstract class ASTNode extends ExtendedTerminalNode {
  /**
   * Generates the string representation of this AST object as it would be in the
   * generated code.
   * 
   * @return The printed string for this AST object
   */
  protected abstract String getPrinted();
}
