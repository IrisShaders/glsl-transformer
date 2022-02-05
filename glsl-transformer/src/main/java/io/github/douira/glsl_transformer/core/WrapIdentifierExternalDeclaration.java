package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.RunPhase;

public abstract class WrapIdentifierExternalDeclaration<T>
    extends WrapIdentifierDynamic<T> {

  public WrapIdentifierExternalDeclaration() {
    super();
    addConcurrentPhase(new RunPhase<T>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(
            InjectionPoint.BEFORE_DECLARATIONS,
            getInjectionContent());
      }

      @Override
      protected boolean isActive() {
        return isActiveDynamic();
      }
    });
  }

  protected abstract String getInjectionContent();
}
