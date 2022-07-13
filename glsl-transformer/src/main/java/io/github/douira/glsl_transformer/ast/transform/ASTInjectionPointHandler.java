package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.basic.InjectionPoint;

public class ASTInjectionPointHandler {
  public static boolean checkChildRelevant(InjectionPoint location, Class<?> childClass) {
    switch (location) {
      case BEFORE_DECLARATIONS:
        // TODO
      case BEFORE_DIRECTIVES:
        // TODO
      case BEFORE_EOF:
        // TODO
      case BEFORE_EXTENSIONS:
        // TODO
      case BEFORE_FUNCTIONS:
        // TODO
      case BEFORE_VERSION:
        // TODO
      default:
        InjectionPoint.throwMissingCaseError();
        return false;
    }
  }
}
