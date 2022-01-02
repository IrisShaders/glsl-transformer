package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * String node provides a terminal node with arbitrary contents. This is useful
 * for inserting strings into the printed code such as comments.
 * 
 * Only use this terminal node for non-parsed tokens like comments, whitespace
 * or preprocessor directives. For all other syntax features, use the local
 * roots system. Parse tree search doesn't work inside string nodes as they are
 * not parsed.
 */
public class StringTerminalNode extends TerminalNodeImpl {
  /**
   * Creates a new string node with the given string content.
   * 
   * @param text The string to create a token for
   */
  public StringTerminalNode(String text) {
    super(new StringToken(text));
  }
}
