package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.core.target.TerminalReplaceTarget;
import io.github.douira.glsl_transformer.core.target.WrapThrowTarget;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

public abstract class WrapIdentifierDynamic<T> extends Transformation<T> {
  public WrapIdentifierDynamic(TransformationPhase<T> wrappingInjector) {
    this();
    addConcurrentPhase(wrappingInjector);
  }

  protected WrapIdentifierDynamic() {
    addPhase(new SearchTerminals<T>(new WrapThrowTarget<T>() {
      @Override
      protected String getWrapResult() {
        return getWrapResultDynamic();
      }
    }) {
      @Override
      protected boolean isActive() {
        return isActiveDynamic();
      }
    });

    addPhase(new SearchTerminals<T>(new TerminalReplaceTarget<T>() {
      @Override
      protected String getTerminalContent() {
        return getWrapResultDynamic();
      }

      @Override
      public String getNeedle() {
        return getWrapTargetDynamic();
      }
    }) {
      @Override
      protected boolean isActive() {
        return isActiveDynamic();
      }
    });
  }

  protected abstract boolean isActiveDynamic();

  protected abstract String getWrapResultDynamic();

  protected abstract String getWrapTargetDynamic();
}
