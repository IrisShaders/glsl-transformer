package io.github.douira.glsl_transformer.basic;

import io.github.douira.glsl_transformer.cst.transform.TransformationPhase;
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
public enum InjectionPoint {
  /**
   * Before the #version statement (and all other syntax features by necessity)
   */
  BEFORE_VERSION,

  /**
   * Before the #extension statement, before other directives, declarations and
   * function definitions
   */
  BEFORE_EXTENSIONS,

  /**
   * Before non-extension parsed #-directives such as #pragma, before
   * declarations and function definitions. (after extension statements if they
   * aren't mixed with other directives and directly follow the #version)
   */
  BEFORE_DIRECTIVES,

  /**
   * Before declarations like layout and struct, before function definitions
   */
  BEFORE_DECLARATIONS,

  /**
   * Before function definitions
   */
  BEFORE_FUNCTIONS,

  /**
   * Before the end of the file, basically the last possible location
   */
  BEFORE_EOF;

  @ExcludeFromJacocoGeneratedReport
  public static void throwMissingCaseError() {
    throw new AssertionError("A non-special injection point doesn't have a child relevance implementation!");
  }
}
