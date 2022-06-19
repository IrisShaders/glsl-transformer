package io.github.douira.glsl_transformer.ast.print;

/**
 * How important a token is to include in the output and why it exists in the
 * token stream.
 */
public enum TokenRole {
  /**
   * Required tokens must be printed to the output as they are.
   */
  EXACT,

  /**
   * Required spaces that can be converted to more whitespace but not other kinds
   * of whitespace.
   */
  EXTENDABLE_SPACE,

  /**
   * Required spaces that may be converted to a line break and other additional
   * whitespace if necessary to shorten a line.
   */
  BREAKABLE_SPACE,

  /**
   * Optional tokens that can be printed to the output if standard line breaks
   * between statements and other common locations are desired. May be extended by
   * additional whitespace for formatting.
   */
  COMMON_FORMATTING;

  public static final TokenRole DEFAULT = TokenRole.EXACT;
}
