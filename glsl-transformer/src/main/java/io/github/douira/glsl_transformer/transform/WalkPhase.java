package io.github.douira.glsl_transformer.transform;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * The walk phase is a phase on which the listener methods of the generated
 * listener interface are called. It can control when it is run by changing the
 * is*Walk and isActive methods. It can also run before and after the tree walk
 * happens.
 */
public abstract class WalkPhase extends TransformationPhase {
  /**
   * Determines if this phase's {@link #beforeWalk(TranslationUnitContext)} should
   * be run.
   * 
   * @return If it should be run
   */
  protected boolean isActiveBeforeWalk() {
    return isActive();
  }

  /**
   * Determines if the phase's listener methods should be run. If this returns
   * {@code false}, the phase effectively degenerates into a {@link RunPhase}.
   * 
   * @return If the phase should participate in the tree walk
   */
  protected boolean isActiveAtWalk() {
    return isActive();
  }

  /**
   * Determines if this phase's {@link #afterWalk(TranslationUnitContext)} should
   * be run.
   * 
   * @return If it should be run
   */
  protected boolean isActiveAfterWalk() {
    return isActive();
  }

  /**
   * This method is called before the tree walk is performed.
   */
  protected void beforeWalk(TranslationUnitContext ctx) {
  }

  /**
   * This method is called after the tree walk was performed.
   */
  protected void afterWalk(TranslationUnitContext ctx) {
  }
}
