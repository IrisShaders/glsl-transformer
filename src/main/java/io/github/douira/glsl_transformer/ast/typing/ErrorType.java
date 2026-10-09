package io.github.douira.glsl_transformer.ast.typing;

/**
 * The type of anything that could not be typed. The error type absorbs further
 * errors: an expression with an operand of the error type gets the error type
 * without generating an additional diagnostic. It is compatible with every
 * other type.
 */
public final class ErrorType implements Type {
  public static final ErrorType INSTANCE = new ErrorType();

  private ErrorType() {
  }

  @Override
  public String getTypeName() {
    return "<error>";
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
