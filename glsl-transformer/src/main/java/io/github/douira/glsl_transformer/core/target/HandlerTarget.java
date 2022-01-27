package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.PhaseCollector;
import io.github.douira.glsl_transformer.transform.TransformationPhase;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A handler target contains a string to search for and a method that is called
 * to handle finding the string in a parse tree.
 */
public abstract class HandlerTarget<T> extends TransformationPhase<T> {
  @Override
  public void setCollector(PhaseCollector<T> parent) {
    super.setCollector(parent);
  }

  /**
   * Returns the string to search for. This method should be fast as it will be
   * called often.
   * 
   * @return The string to search for
   */
  public abstract String getNeedle();

  /**
   * Handles the containing node and token that the string was found in.
   * 
   * @param node  The node that contains the token
   * @param match The token text that contains the needle
   */
  public abstract void handleResult(TreeMember node, String match);
}
