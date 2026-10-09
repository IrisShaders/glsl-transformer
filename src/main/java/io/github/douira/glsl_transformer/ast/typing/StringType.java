package io.github.douira.glsl_transformer.ast.typing;

/**
 * The type of string literals as they are used with printf-style debugging
 * extensions.
 */
public final class StringType implements Type {
  public static final StringType INSTANCE = new StringType();

  private StringType() {
  }

  @Override
  public String getTypeName() {
    return "string";
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
