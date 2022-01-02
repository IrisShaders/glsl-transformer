package io.github.douira.glsl_transformer.ast;

import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * The AST node is a terminal node that can be printed with a method implemented
 * by subclasses. The result of this method is used by other abstract AST
 * classes to re-insert the result of an AST manipulation back into the parse
 * tree.
 */
public abstract class ASTNode extends TerminalNodeImpl {
  /**
   * Creates a new AST node. Internally this calls the terminal node constructor
   * with a null string since the token facilities of the terminal node are not
   * used.
   */
  public ASTNode() {
    super(null);
  }

  /**
   * Generates the string representation of this AST object as it would be in the
   * generated code.
   * 
   * @return The printed string for this AST object
   */
  protected abstract String getPrinted();
}
