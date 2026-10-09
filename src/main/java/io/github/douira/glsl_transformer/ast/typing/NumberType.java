package io.github.douira.glsl_transformer.ast.typing;

import java.util.EnumSet;

/**
 * The different ways bits in a tensor can be interpreted.
 */
public enum NumberType {
  /**
   * boolean bit usage
   */
  BOOLEAN(1, 4),

  /**
   * unsigned integer bit usage
   */
  UNSIGNED_INTEGER(64, 4),

  /**
   * integer bit usage
   */
  SIGNED_INTEGER(64, 4),

  /**
   * floating point bit usage
   */
  FLOATING_POINT(64, 4, 4);

  private final int maxBitDepth;
  private final int[] maxDimensions;

  NumberType(int maxBitDepth, int... maxDimensions) {
    this.maxBitDepth = maxBitDepth;
    this.maxDimensions = maxDimensions;
  }

  public int getMaxBitDepth() {
    return maxBitDepth;
  }

  public int[] getMaxDimensions() {
    return maxDimensions;
  }

  /**
   * @return An EnumSet of all the Types which use this number type.
   */
  public EnumSet<NumericType> getRegisteredTypes() {
    return NumericType.ofNumberType(this);
  }

  public boolean isBoolean() {
    return this == BOOLEAN;
  }

  public boolean isInteger() {
    return this == SIGNED_INTEGER || this == UNSIGNED_INTEGER;
  }

  public boolean isFloatingPoint() {
    return this == FLOATING_POINT;
  }

  public boolean isSigned() {
    return this == SIGNED_INTEGER || this == FLOATING_POINT;
  }
}
