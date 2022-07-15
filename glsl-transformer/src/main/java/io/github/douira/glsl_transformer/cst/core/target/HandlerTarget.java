package io.github.douira.glsl_transformer.cst.core.target;

import io.github.douira.glsl_transformer.cst.transform.*;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.tree.TreeMember;

/**
 * A handler target contains a string to search for and a method that is called
 * to handle finding the string in a parse tree.
 */
public abstract class HandlerTarget<T extends JobParameters> extends TransformationPhaseBase<T> {
  /**
   * Creates a new empty handler target.
   */
  protected HandlerTarget() {
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

  @Override
  public void setPlanner(ExecutionPlanner<T> parent) {
    super.setPlanner(parent);
  }
}
