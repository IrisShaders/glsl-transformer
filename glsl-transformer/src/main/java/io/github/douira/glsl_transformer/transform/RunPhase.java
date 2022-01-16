package io.github.douira.glsl_transformer.transform;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * A run phase simply executes one method when it is executed in a level by the
 * phase collector. Even though it extends {@link TransformationPhase}, no
 * listener methods on
 * it are executed.
 */
public abstract class RunPhase extends TransformationPhase {
  /**
   * This method is implemented by subclasses to be executed by the phase
   * collector at the right time.
   * 
   * @param ctx The root node of the parse tree being transformed
   */
  protected abstract void run(TranslationUnitContext ctx);

  @Override
  final protected boolean checkBeforeWalk(TranslationUnitContext ctx) {
    if (isActive()) {
      run(ctx);
    }
    return false;
  }

  @Override
  final protected void runAfterWalk(TranslationUnitContext ctx) {
  }
}
