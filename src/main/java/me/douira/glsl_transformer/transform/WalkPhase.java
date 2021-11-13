package me.douira.glsl_transformer.transform;

import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public abstract class WalkPhase extends Phase {
  protected boolean isActiveBeforeWalk() {
    return isActive();
  }

  protected boolean isActiveAtWalk() {
    return isActive();
  }

  protected boolean isActiveAfterWalk() {
    return isActive();
  }

  protected void beforeWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }

  protected void afterWalk(TranslationUnitContext ctx) {
    // to be possibly overwritten by the implementing class
  }
}
