package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * The empty terminal node is a placeholder in parse tree children lists in
 * order to prevent exceptions from being thrown because the parse tree walker
 * expects child lists not to change length during iteration. The print visitor
 * ignores it during printing because it's token is null.
 */
public class EmptyTerminalNode extends TerminalNodeImpl {
  /**
   * Creates a new empty terminal node.
   */
  public EmptyTerminalNode() {
    super(null);
  }
}
