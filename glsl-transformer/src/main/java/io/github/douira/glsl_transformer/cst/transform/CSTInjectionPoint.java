package io.github.douira.glsl_transformer.cst.transform;

import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.util.ExcludeFromJacocoGeneratedReport;

/**
 * Shader code is expected to be roughly structured as follows:
 * version, extensions, other directives (#define, #pragma etc.), declarations
 * (layout etc.), functions (void main etc.).
 * 
 * These injection points can be used to insert nodes into the translation
 * unit's child list. An injection will happen before the syntax feature it
 * describes and any that follow it in the list.
 * 
 * This enum is used to specify where the injection methods in
 * {@link TransformationPhase} should insert things.
 * 
 * @implNote AFTER versions of these points would be the same as the next BEFORE
 *           point in the list.
 */
public enum CSTInjectionPoint {
  /**
   * Before the #version statement (and all other syntax features by necessity)
   */
  BEFORE_VERSION,

  /**
   * Before the #extension statement, before other directives, declarations and
   * function definitions
   */
  BEFORE_EXTENSIONS() {
    @Override
    protected boolean checkChildRelevant(Class<?> childClass) {
      return BEFORE_DIRECTIVES.checkChildRelevant(childClass)
          || childClass == ExtensionStatementContext.class;
    }
  },

  /**
   * Before non-extension parsed #-directives such as #pragma, before
   * declarations and function definitions. (after extension statements if they
   * aren't mixed with other directives and directly follow the #version)
   */
  BEFORE_DIRECTIVES() {
    @Override
    protected boolean checkChildRelevant(Class<?> childClass) {
      return BEFORE_DECLARATIONS.checkChildRelevant(childClass)
          || childClass == PragmaStatementContext.class;
    }
  },

  /**
   * Before declarations like layout and struct, before function definitions
   */
  BEFORE_DECLARATIONS() {
    @Override
    protected boolean checkChildRelevant(Class<?> childClass) {
      return BEFORE_FUNCTIONS.checkChildRelevant(childClass)
          || childClass == LayoutDefaultsContext.class
          || DeclarationContext.class.isAssignableFrom(childClass);
    }
  },

  /**
   * Before function definitions
   */
  BEFORE_FUNCTIONS() {
    @Override
    protected boolean checkChildRelevant(Class<?> childClass) {
      return childClass == FunctionDefinitionContext.class;
    }
  },

  /**
   * Before the end of the file, basically the last possible location
   */
  BEFORE_EOF;

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
