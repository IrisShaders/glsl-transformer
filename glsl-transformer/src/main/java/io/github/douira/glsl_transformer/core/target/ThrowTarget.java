package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.SemanticException;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A target that searches for a search string in and upon finding a match uses a
 * method to generate an exception which is then thrown.
 */
public abstract class ThrowTarget<T> extends HandlerTarget<T> {
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
    throw getException(node, match);
  }

  /**
   * Generates the exception when this target is found.
   * 
   * @param node  The node that contains the token
   * @param match The token text that contains the needle
   * @return The semantic exception to throw
   */
  public abstract SemanticException getException(TreeMember node, String match);

  /**
   * Creates a new throw target with a fixed message instead of using the
   * implemented method of a subclass to generate exceptions.
   * 
   * @param <T> The job parameter type
   * @param needle  The search string for the throw target
   * @param message The message to make the exceptions with
   * @return The constructed throw target
   */
  public static <T> ThrowTarget<T> fromMessage(String needle, String message) {
    return new ThrowTarget<T>(needle) {
      @Override
      public SemanticException getException(TreeMember node, String match) {
        return new SemanticException(message, node);
      }
    };
  }
}
