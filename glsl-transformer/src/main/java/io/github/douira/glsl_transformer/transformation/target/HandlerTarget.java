package io.github.douira.glsl_transformer.transformation.target;

import java.util.function.BiConsumer;

import io.github.douira.glsl_transformer.generic.TreeMember;
import io.github.douira.glsl_transformer.transform.PhaseCollector;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

public abstract class HandlerTarget extends TransformationPhase {
  private String needle;

  public HandlerTarget(String needle) {
    this.needle = needle;
  }

  /**
   * Returns the string to search for. This method should be fast as it will be
   * called often.
   * 
   * @return The string to search for
   */
  public String getNeedle() {
    return needle;
  }

  @Override
  public void setCollector(PhaseCollector parent) {
    super.setCollector(parent);
  }

  /**
   * Handles the containing node and token that the string was found in.
   * 
   * @param node  The node that contains the token
   * @param match The identifier that contains the needle
   */
  public abstract void handleResult(TreeMember node, String match);

  public static HandlerTarget fromConsumer(
      String needle, BiConsumer<TreeMember, String> handler) {
    return new HandlerTarget(needle) {
      @Override
      public void handleResult(TreeMember node, String match) {
        handler.accept(node, match);
      }
    };
  }
}
