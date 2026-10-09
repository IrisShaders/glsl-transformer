package io.github.douira.glsl_transformer.ast.typing;

/**
 * A type in the GLSL type system. Types are immutable. Numeric types, fixed
 * types, strings, arrays and the error type are compared structurally, struct
 * and interface block types are nominal and compared by identity.
 */
public sealed interface Type
    permits NumericType, FixedType, StringType, ArrayType, StructType, InterfaceBlockType, SubroutineType, ErrorType {
  /**
   * Returns the name of the type as it would be written in GLSL, as far as that
   * is possible. This is intended for diagnostics and debugging.
   *
   * @return The name of the type
   */
  String getTypeName();

  /**
   * Returns whether this is the error type, which is given to anything that
   * could not be typed.
   *
   * @return true if this is the error type
   */
  default boolean isError() {
    return this == ErrorType.INSTANCE;
  }
}
