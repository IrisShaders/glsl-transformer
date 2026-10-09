package io.github.douira.glsl_transformer.ast.typing;

/**
 * The value of an integral constant expression. Only scalar boolean and
 * integer values are evaluated. All other constant expressions, like floating
 * point numbers, vectors or calls to builtin functions, have the
 * {@link #UNKNOWN} value.
 *
 * @param type  the scalar boolean or integer type of the value, null if the
 *              value is not known
 * @param value the value, 0 or 1 for booleans. Signed integers are sign
 *              extended and unsigned integers are zero extended to 64 bits.
 */
public record ConstantValue(NumericType type, long value) {
  /**
   * The value of constant expressions that could not be evaluated.
   */
  public static final ConstantValue UNKNOWN = new ConstantValue(null, 0);

  /**
   * Creates the constant of the given type that the given bits are converted
   * to, wrapping around if the value does not fit into the type.
   *
   * @param type  the type of the value
   * @param value the value to convert
   * @return the value, which is unknown if the type is not a scalar boolean or
   *         integer type
   */
  public static ConstantValue of(Type type, long value) {
    if (!(type instanceof NumericType numeric) || !numeric.isScalar() || numeric.getNumberType().isFloatingPoint()) {
      return UNKNOWN;
    }
    if (numeric.getNumberType().isBoolean()) {
      return new ConstantValue(numeric, value == 0 ? 0 : 1);
    }
    var unusedBits = 64 - numeric.getBitDepth();
    return new ConstantValue(numeric, numeric.getNumberType() == NumberType.SIGNED_INTEGER
        ? value << unusedBits >> unusedBits
        : value << unusedBits >>> unusedBits);
  }

  public boolean isKnown() {
    return type != null;
  }

  public boolean isUnsigned() {
    return type.getNumberType() == NumberType.UNSIGNED_INTEGER;
  }
}
