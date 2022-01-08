package io.github.douira.glsl_transformer.transformation.target;

import io.github.douira.glsl_transformer.generic.TreeMember;
import io.github.douira.glsl_transformer.transform.SemanticException;

/**
 * A target that searches for a search string in and upon finding a match uses a
 * method to generate an exception which is then thrown.
 */
public abstract class ThrowTarget extends HandlerTarget {
  /**
   * Creates a new throw target with a given search string
   * 
   * @param needle The search string
   */
  public ThrowTarget(String needle) {
    super(needle);
  }

  @Override
  public void handleResult(TreeMember node, String match) {
    throw getMessage(node, match);
  }

  /**
   * Generates the exception when this target is found.
   * 
   * @param node  The node that contains the token
   * @param match The identifier that contains the needle
   * @return The semantic exception to throw
   */
  public abstract SemanticException getMessage(TreeMember node, String match);

  /**
   * Creates a new throw target with a fixed message instead of using the
   * implemented method of a subclass to generate exceptions.
   * 
   * @param needle  The search string for the throw target
   * @param message The message to make the exceptions with
   * @return The constructed throw target
   */
  public static ThrowTarget fromMessage(String needle, String message) {
    return new ThrowTarget(needle) {
      @Override
      public SemanticException getMessage(TreeMember node, String match) {
        return new SemanticException(message, node);
      }
    };
  }
}
