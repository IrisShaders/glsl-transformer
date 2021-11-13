package me.douira.glsl_transformer.transform;

import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public abstract class RunPhase extends Phase {
  protected abstract void run(TranslationUnitContext ctx);
}
