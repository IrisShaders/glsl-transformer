package io.github.douira.glsl_transformer.transform;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * A run phase simply executes one method when it is executed in a level by the
 * phase collector. Even though it extends {@link Phase}, no listener methods on
 * it are executed.
 */
public abstract class RunPhase extends Phase {
  protected abstract void run(TranslationUnitContext ctx);
}
