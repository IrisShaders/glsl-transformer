package io.github.douira.glsl_transformer.cst.transform;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

/**
 * The walk phase is a phase on which the listener methods of the generated
 * listener interface are called. It can control when it is run by changing the
 * is*Walk and isActive methods. It can also run before and after the tree walk
 * happens.
 */
public abstract class WalkPhase<T extends JobParameters> extends TransformationPhase<T> {
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
   * 
   * @param ctx The root translation unit node
   */
  protected void beforeWalk(TranslationUnitContext ctx) {
  }

  /**
   * This method is called after the tree walk was performed.
   * 
   * @param ctx The root translation unit node
   */
  protected void afterWalk(TranslationUnitContext ctx) {
  }

  @Override
  protected boolean canWalk() {
    return true;
  }

  @Override
  final protected boolean checkBeforeWalk(TranslationUnitContext ctx) {
    if (isActiveBeforeWalk()) {
      beforeWalk(ctx);
    }
    return isActiveAtWalk();
  }

  @Override
  final protected void runAfterWalk(TranslationUnitContext ctx) {
    if (isActiveAfterWalk()) {
      afterWalk(ctx);
    }
  }
}
