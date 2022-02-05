package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.SemanticException;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A target that searches for a search string in and upon finding a match uses a
 * method to generate a string for a semantic exception which is then thrown.
 */
public abstract class ThrowTarget<T> extends HandlerTargetImpl<T> {
  /**
   * Creates a new throw target with a given search string
   * 
   * @param needle The search string
   */
  public ThrowTarget(String needle) {
    super(needle);
  }

  /**
   * Creates a new throw target with no search string. The {@link #getNeedle()}
   * method should be overwritten if this constructor is used.
   * 
   * @see io.github.douira.glsl_transformer.core.target.HandlerTargetImpl#HandlerTargetImpl()
   */
  protected ThrowTarget() {
  }

  /**
   * Generates the exception when this target is found.
   * 
   * @param node  The node that contains the token
   * @param match The token text that contains the needle
   * @return The semantic exception to throw
   */
  protected abstract String getMessage(TreeMember node, String match);

  @Override
  public void handleResult(TreeMember node, String match) {
    throw new SemanticException(getMessage(node, match));
  }
}
