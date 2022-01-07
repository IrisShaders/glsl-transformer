package io.github.douira.glsl_transformer.transformation.target;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.transform.SemanticException;

/**
 * An illegal target to search for in identifiers contains a string to search
 * for and a method for generating exceptions.
 */
public abstract class IllegalTarget extends HandlerTarget {
  public IllegalTarget(String needle) {
    super(needle);
  }

  @Override
  public void handleResult(TerminalNode node, String match) {
    throw getMessage(node, match);
  }

  /**
   * Generates the exception when this target is found.
   * 
   * @param node  The node that contains the token
   * @param match The identifier that contains the needle
   * @return The semantic exception to throw
   */
  public abstract SemanticException getMessage(TerminalNode node, String match);

  public static IllegalTarget fromMessage(String needle, String message) {
    return new IllegalTarget(needle) {
      @Override
      public SemanticException getMessage(TerminalNode node, String match) {
        return new SemanticException(message, node);
      }
    };
  }
}
