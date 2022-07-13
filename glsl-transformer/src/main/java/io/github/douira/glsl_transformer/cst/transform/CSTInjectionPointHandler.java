package io.github.douira.glsl_transformer.cst.transform;

import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.basic.InjectionPoint;

public class CSTInjectionPointHandler {
  /**
   * Checks if the given class of the child of an external declaration makes the
   * external declaration one that should be injected before. When this method
   * returns true, the injection happens right before the external declaration
   * that has the child that was found to be relevant.
   * 
   * @param childClass The class of the only child node of the external
   *                   declaration being tested for being a node before which the
   *                   injection has to happen
   * @return {@code true} if the class means the injection should happen before
   *         this external declaration according to the implementing injection
   *         location
   */
  public static boolean checkChildRelevant(InjectionPoint location, Class<?> childClass) {
    switch (location) {
      case BEFORE_DECLARATIONS:
        return checkChildRelevant(InjectionPoint.BEFORE_FUNCTIONS, childClass)
            || childClass == LayoutDefaultsContext.class
            || DeclarationContext.class.isAssignableFrom(childClass);
      case BEFORE_DIRECTIVES:
        return checkChildRelevant(InjectionPoint.BEFORE_DECLARATIONS, childClass)
            || childClass == PragmaStatementContext.class;
      case BEFORE_EXTENSIONS:
        return checkChildRelevant(InjectionPoint.BEFORE_DIRECTIVES, childClass)
            || childClass == ExtensionStatementContext.class;
      case BEFORE_FUNCTIONS:
        return childClass == FunctionDefinitionContext.class;
      default:
        InjectionPoint.throwMissingCaseError();
        return false;
    }
  }
}
