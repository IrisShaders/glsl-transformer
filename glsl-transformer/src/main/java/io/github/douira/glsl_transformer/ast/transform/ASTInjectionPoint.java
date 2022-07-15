package io.github.douira.glsl_transformer.ast.transform;

import io.github.douira.glsl_transformer.util.ExcludeFromJacocoGeneratedReport;

public enum ASTInjectionPoint {
  BEFORE_EXTENSIONS() {
    // TODO
  },

  BEFORE_DIRECTIVES() {
    // TODO
  },

  BEFORE_DECLARATIONS() {
    // TODO
  },

  BEFORE_FUNCTIONS() {
    // TODO
  },

  END() {
    // TODO
  };

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
  @ExcludeFromJacocoGeneratedReport
  protected boolean checkChildRelevant(Class<?> childClass) {
    throw new AssertionError("A non-special injection point doesn't have a child relevance implementation!");
  }
}
