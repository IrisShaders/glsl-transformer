package io.github.douira.glsl_transformer.core;

import java.util.Collection;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;
import io.github.douira.glsl_transformer.transform.JobParameters;

/**
 * 
 */
public abstract class SearchTerminalsDynamic<T extends JobParameters> extends SearchTerminals<T> {


  @Override
  protected void beforeWalk(TranslationUnitContext ctx) {
    targets = null;
  }

  @Override
  protected boolean isActiveBeforeWalk() {
    return true;
  }
}
