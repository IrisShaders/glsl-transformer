package me.douira.glsl_transformer.transform;

import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public abstract class WalkPhase extends Phase {
  /**
   * Is read by {@link PhaseCollector} to check if {@link afterWalk} should be
   * run. The value is filled with what the invocation of {@link isActive}
   * returned.
   */
  protected boolean isActiveCache;

  /**
   * Overwrite this method to add a check of if this phase should be run at all.
   * 
   * @return If the phase should run. {@code true} by default.
   */
  protected boolean isActive() {
    return true;
  }

  protected final boolean checkActive() {
    return isActiveCache = isActive();
  }

  protected void beforeWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }

  protected void afterWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }
}
