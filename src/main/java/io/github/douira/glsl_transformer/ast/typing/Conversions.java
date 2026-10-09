package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

/**
 * The implicit conversion rules of GLSL. Which conversions are allowed depends
 * on the GLSL version and the enabled extensions.
 * <p>
 * The rules for the explicitly sized types are those of
 * GL_EXT_shader_explicit_arithmetic_types. Conversions that involve a type
 * other than int, uint, float and double are not gated on anything, since the
 * presence of such a type already means that an extension is in use.
 */
public final class Conversions {
  private static final EnumSet<NumericType> CORE_SCALARS = EnumSet.of(
      NumericType.INT32, NumericType.UINT32, NumericType.FLOAT32, NumericType.FLOAT64);

  private Conversions() {
  }

  /**
   * Checks if two types are the same type. The error type is the same as every
   * type, and array sizes only differ if both are known.
   *
   * @param a the first type
   * @param b the second type
   * @return true if the types are the same
   */
  public static boolean isSameType(Type a, Type b) {
    if (a.isError() || b.isError()) {
      return true;
    }
    if (a instanceof ArrayType x && b instanceof ArrayType y) {
      return x.size().isCompatibleWith(y.size()) && isSameType(x.element(), y.element());
    }
    return a.equals(b);
  }

  /**
   * Checks if a value of one type can be used where the other type is expected,
   * either because the types are the same or because there is an implicit
   * conversion.
   *
   * @param from the type of the value
   * @param to   the expected type
   * @param env  the environment that determines the available conversions
   * @return true if the types are the same or there is an implicit conversion
   */
  public static boolean canImplicitlyConvert(Type from, Type to, TypeEnvironment env) {
    if (isSameType(from, to)) {
      return true;
    }
    return from instanceof NumericType x && to instanceof NumericType y
        && Arrays.equals(x.getDimensions(), y.getDimensions())
        && canConvertScalar(x.getComponentType(), y.getComponentType(), env);
  }

  private static boolean canConvertScalar(NumericType from, NumericType to, TypeEnvironment env) {
    var fromNumber = from.getNumberType();
    var toNumber = to.getNumberType();
    if (fromNumber.isBoolean() || toNumber.isBoolean()
        || fromNumber.isFloatingPoint() && !toNumber.isFloatingPoint()) {
      return false;
    }

    // conversions never narrow and at the same size only go from signed integers
    // to unsigned integers and from integers to floating point numbers
    var widening = to.getBitDepth() > from.getBitDepth()
        || to.getBitDepth() == from.getBitDepth() && toNumber != NumberType.SIGNED_INTEGER;
    if (!widening || !CORE_SCALARS.contains(from) || !CORE_SCALARS.contains(to)) {
      return widening;
    }

    // the conversions between the core types were added over time
    if (to == NumericType.FLOAT32) {
      return env.isAtLeast(120, 0) || env.hasExtension("GL_EXT_shader_implicit_conversions");
    }
    if (to == NumericType.FLOAT64) {
      return env.isAtLeast(400, 0) || env.hasExtension("GL_ARB_gpu_shader_fp64");
    }
    return env.isAtLeast(400, 0) || env.hasExtension("GL_ARB_gpu_shader5")
        || env.hasExtension("GL_EXT_shader_implicit_conversions");
  }

  /**
   * Returns the scalar type that the components of the operands of a binary
   * operator are converted to.
   *
   * @param a   the scalar component type of the first operand
   * @param b   the scalar component type of the second operand
   * @param env the environment that determines the available conversions
   * @return the common scalar type, or null if there is none
   */
  public static NumericType binaryResult(NumericType a, NumericType b, TypeEnvironment env) {
    if (canImplicitlyConvert(a, b, env)) {
      return b;
    }
    return canImplicitlyConvert(b, a, env) ? a : null;
  }

  /**
   * Returns the type two values are converted to when they need to have the
   * same type, as in the branches of the ternary operator or the operands of
   * the equality operators.
   *
   * @param a   the first type
   * @param b   the second type
   * @param env the environment that determines the available conversions
   * @return the common type, or null if there is none
   */
  public static Type commonType(Type a, Type b, TypeEnvironment env) {
    if (a instanceof NumericType x && b instanceof NumericType y) {
      var component = binaryResult(x.getComponentType(), y.getComponentType(), env);
      return component != null && Arrays.equals(x.getDimensions(), y.getDimensions())
          ? x.withComponentType(component)
          : null;
    }
    if (!isSameType(a, b)) {
      return null;
    }
    return a.isError() ? b : a;
  }

  private static boolean isPromotion(NumericType from, NumericType to) {
    return to == NumericType.FLOAT64 && from.getNumberType().isFloatingPoint()
        || to == NumericType.INT32 && from.getBitDepth() < 32;
  }

  /**
   * Checks if converting a function argument to the first parameter type is
   * better than converting it to the second parameter type according to the
   * overload resolution rules: an exact match is better than a conversion, a
   * promotion (like float to double) is better than other conversions and
   * converting an integer to float is better than converting it to double.
   *
   * @param argument the type of the argument
   * @param first    the first parameter type
   * @param second   the second parameter type
   * @return true if the conversion to the first parameter type is better
   */
  public static boolean isBetterConversion(Type argument, Type first, Type second) {
    if (!(argument instanceof NumericType a && first instanceof NumericType x && second instanceof NumericType y)
        || x == y) {
      return false;
    }
    if (a == x || a == y) {
      return a == x;
    }
    var from = a.getComponentType();
    var toFirst = x.getComponentType();
    var toSecond = y.getComponentType();
    if (isPromotion(from, toFirst) || isPromotion(from, toSecond)) {
      return !isPromotion(from, toSecond);
    }
    return from.getNumberType().isInteger() && toFirst == NumericType.FLOAT32 && toSecond == NumericType.FLOAT64;
  }
}
