package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.RunPhase;
import io.github.douira.glsl_transformer.transform.TransformationPhase.InjectionPoint;

/**
 * A wrap identifier transformation that injects an external declaration at a
 * specified location. By default it's injected at
 * {@link InjectionPoint#BEFORE_DECLARATIONS}.
 */
public abstract class WrapIdentifierExternalDeclaration<T>
    extends WrapIdentifierDynamic<T> {
  /**
   * Creates a new wrap identifier transformation that injects an external
   * declaration.
   */
  public WrapIdentifierExternalDeclaration() {
    super();
    addConcurrentPhase(new RunPhase<T>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration(getInjectionLocation(), getInjectionContent());
      }

      @Override
      protected boolean isActive() {
        return isActiveDynamic();
      }
    });
  }

  /**
   * Returns the content that is injected as an external declaration.
   * 
   * @return The string to be parsed into a new external declaration
   */
  protected abstract String getInjectionContent();

  /**
   * Returns the injection location for where the external declaration should be
   * injected. By default this is {@link InjectionPoint#BEFORE_DECLARATIONS} but
   * it can be overwritten.
   * 
   * @return The injection location to use
   */
  protected InjectionPoint getInjectionLocation() {
    return InjectionPoint.BEFORE_DECLARATIONS;
  }
}
