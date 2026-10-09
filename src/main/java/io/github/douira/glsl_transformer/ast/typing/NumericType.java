package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.TokenTyped;
import org.antlr.v4.runtime.Token;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents the scalar, vector and matrix types of GLSL and contains
 * easily accessible data about each of them. Each numeric type is also a
 * {@link Type} in the type system.
 * <p>
 * The dimensions are an array of one or two integers describing the shape of
 * the type. Scalars have the dimensions {@code {1}}, vectors have their
 * component count as the only dimension and matrices have the number of columns
 * followed by the number of rows as their dimensions.
 */
public enum NumericType implements TokenTyped, Type {
  BOOL(GLSLLexer.BOOL, GLSLLexer.BOOLCONSTANT, NumberType.BOOLEAN, "bool", "bool", 1),
  BVEC2(GLSLLexer.BVEC2, NumberType.BOOLEAN, "bvec2", "bvec2", 1, 2),
  BVEC3(GLSLLexer.BVEC3, NumberType.BOOLEAN, "bvec3", "bvec3", 1, 3),
  BVEC4(GLSLLexer.BVEC4, NumberType.BOOLEAN, "bvec4", "bvec4", 1, 4),

  INT8(GLSLLexer.INT8, NumberType.SIGNED_INTEGER, null, "int8_t", 8),
  I8VEC2(GLSLLexer.I8VEC2, NumberType.SIGNED_INTEGER, null, "i8vec2", 8, 2),
  I8VEC3(GLSLLexer.I8VEC3, NumberType.SIGNED_INTEGER, null, "i8vec3", 8, 3),
  I8VEC4(GLSLLexer.I8VEC4, NumberType.SIGNED_INTEGER, null, "i8vec4", 8, 4),
  UINT8(GLSLLexer.UINT8, NumberType.UNSIGNED_INTEGER, null, "uint8_t", 8),
  U8VEC2(GLSLLexer.U8VEC2, NumberType.UNSIGNED_INTEGER, null, "u8vec2", 8, 2),
  U8VEC3(GLSLLexer.U8VEC3, NumberType.UNSIGNED_INTEGER, null, "u8vec3", 8, 3),
  U8VEC4(GLSLLexer.U8VEC4, NumberType.UNSIGNED_INTEGER, null, "u8vec4", 8, 4),

  INT16(GLSLLexer.INT16, GLSLLexer.INT16CONSTANT, NumberType.SIGNED_INTEGER, null, "int16_t", 16),
  I16VEC2(GLSLLexer.I16VEC2, NumberType.SIGNED_INTEGER, null, "i16vec2", 16, 2),
  I16VEC3(GLSLLexer.I16VEC3, NumberType.SIGNED_INTEGER, null, "i16vec3", 16, 3),
  I16VEC4(GLSLLexer.I16VEC4, NumberType.SIGNED_INTEGER, null, "i16vec4", 16, 4),
  UINT16(GLSLLexer.UINT16, GLSLLexer.UINT16CONSTANT, NumberType.UNSIGNED_INTEGER, null, "uint16_t", 16),
  U16VEC2(GLSLLexer.U16VEC2, NumberType.UNSIGNED_INTEGER, null, "u16vec2", 16, 2),
  U16VEC3(GLSLLexer.U16VEC3, NumberType.UNSIGNED_INTEGER, null, "u16vec3", 16, 3),
  U16VEC4(GLSLLexer.U16VEC4, NumberType.UNSIGNED_INTEGER, null, "u16vec4", 16, 4),

  INT32(GLSLLexer.INT32, GLSLLexer.INT32CONSTANT, NumberType.SIGNED_INTEGER, "int", "int32_t", 32),
  I32VEC2(GLSLLexer.I32VEC2, NumberType.SIGNED_INTEGER, "ivec2", "i32vec2", 32, 2),
  I32VEC3(GLSLLexer.I32VEC3, NumberType.SIGNED_INTEGER, "ivec3", "i32vec3", 32, 3),
  I32VEC4(GLSLLexer.I32VEC4, NumberType.SIGNED_INTEGER, "ivec4", "i32vec4", 32, 4),
  UINT32(GLSLLexer.UINT32, GLSLLexer.UINT32CONSTANT, NumberType.UNSIGNED_INTEGER, "uint", "uint32_t", 32),
  U32VEC2(GLSLLexer.U32VEC2, NumberType.UNSIGNED_INTEGER, "uvec2", "u32vec2", 32, 2),
  U32VEC3(GLSLLexer.U32VEC3, NumberType.UNSIGNED_INTEGER, "uvec3", "u32vec3", 32, 3),
  U32VEC4(GLSLLexer.U32VEC4, NumberType.UNSIGNED_INTEGER, "uvec4", "u32vec4", 32, 4),

  INT64(GLSLLexer.INT64, GLSLLexer.INT64CONSTANT, NumberType.SIGNED_INTEGER, null, "int64_t", 64),
  I64VEC2(GLSLLexer.I64VEC2, NumberType.SIGNED_INTEGER, null, "i64vec2", 64, 2),
  I64VEC3(GLSLLexer.I64VEC3, NumberType.SIGNED_INTEGER, null, "i64vec3", 64, 3),
  I64VEC4(GLSLLexer.I64VEC4, NumberType.SIGNED_INTEGER, null, "i64vec4", 64, 4),
  UINT64(GLSLLexer.UINT64, GLSLLexer.UINT64CONSTANT, NumberType.UNSIGNED_INTEGER, null, "uint64_t", 64),
  U64VEC2(GLSLLexer.U64VEC2, NumberType.UNSIGNED_INTEGER, null, "u64vec2", 64, 2),
  U64VEC3(GLSLLexer.U64VEC3, NumberType.UNSIGNED_INTEGER, null, "u64vec3", 64, 3),
  U64VEC4(GLSLLexer.U64VEC4, NumberType.UNSIGNED_INTEGER, null, "u64vec4", 64, 4),

  FLOAT16(GLSLLexer.FLOAT16, GLSLLexer.FLOAT16CONSTANT, NumberType.FLOATING_POINT, null, "float16_t", 16),
  F16VEC2(GLSLLexer.F16VEC2, NumberType.FLOATING_POINT, null, "f16vec2", 16, 2),
  F16VEC3(GLSLLexer.F16VEC3, NumberType.FLOATING_POINT, null, "f16vec3", 16, 3),
  F16VEC4(GLSLLexer.F16VEC4, NumberType.FLOATING_POINT, null, "f16vec4", 16, 4),
  F16MAT2X2(GLSLLexer.F16MAT2X2, NumberType.FLOATING_POINT, "f16mat2", "f16mat2x2", 16, 2, 2),
  F16MAT2X3(GLSLLexer.F16MAT2X3, NumberType.FLOATING_POINT, null, "f16mat2x3", 16, 2, 3),
  F16MAT2X4(GLSLLexer.F16MAT2X4, NumberType.FLOATING_POINT, null, "f16mat2x4", 16, 2, 4),
  F16MAT3X2(GLSLLexer.F16MAT3X2, NumberType.FLOATING_POINT, null, "f16mat3x2", 16, 3, 2),
  F16MAT3X3(GLSLLexer.F16MAT3X3, NumberType.FLOATING_POINT, "f16mat3", "f16mat3x3", 16, 3, 3),
  F16MAT3X4(GLSLLexer.F16MAT3X4, NumberType.FLOATING_POINT, null, "f16mat3x4", 16, 3, 4),
  F16MAT4X2(GLSLLexer.F16MAT4X2, NumberType.FLOATING_POINT, null, "f16mat4x2", 16, 4, 2),
  F16MAT4X3(GLSLLexer.F16MAT4X3, NumberType.FLOATING_POINT, null, "f16mat4x3", 16, 4, 3),
  F16MAT4X4(GLSLLexer.F16MAT4X4, NumberType.FLOATING_POINT, "f16mat4", "f16mat4x4", 16, 4, 4),

  FLOAT32(GLSLLexer.FLOAT32, GLSLLexer.FLOAT32CONSTANT, NumberType.FLOATING_POINT, "float", "float32_t", 32),
  F32VEC2(GLSLLexer.F32VEC2, NumberType.FLOATING_POINT, "vec2", "f32vec2", 32, 2),
  F32VEC3(GLSLLexer.F32VEC3, NumberType.FLOATING_POINT, "vec3", "f32vec3", 32, 3),
  F32VEC4(GLSLLexer.F32VEC4, NumberType.FLOATING_POINT, "vec4", "f32vec4", 32, 4),
  F32MAT2X2(GLSLLexer.F32MAT2X2, NumberType.FLOATING_POINT, "mat2", "f32mat2x2", 32, 2, 2),
  F32MAT2X3(GLSLLexer.F32MAT2X3, NumberType.FLOATING_POINT, "mat2x3", "f32mat2x3", 32, 2, 3),
  F32MAT2X4(GLSLLexer.F32MAT2X4, NumberType.FLOATING_POINT, "mat2x4", "f32mat2x4", 32, 2, 4),
  F32MAT3X2(GLSLLexer.F32MAT3X2, NumberType.FLOATING_POINT, "mat3x2", "f32mat3x2", 32, 3, 2),
  F32MAT3X3(GLSLLexer.F32MAT3X3, NumberType.FLOATING_POINT, "mat3", "f32mat3x3", 32, 3, 3),
  F32MAT3X4(GLSLLexer.F32MAT3X4, NumberType.FLOATING_POINT, "mat3x4", "f32mat3x4", 32, 3, 4),
  F32MAT4X2(GLSLLexer.F32MAT4X2, NumberType.FLOATING_POINT, "mat4x2", "f32mat4x2", 32, 4, 2),
  F32MAT4X3(GLSLLexer.F32MAT4X3, NumberType.FLOATING_POINT, "mat4x3", "f32mat4x3", 32, 4, 3),
  F32MAT4X4(GLSLLexer.F32MAT4X4, NumberType.FLOATING_POINT, "mat4", "f32mat4x4", 32, 4, 4),

  FLOAT64(GLSLLexer.FLOAT64, GLSLLexer.FLOAT64CONSTANT, NumberType.FLOATING_POINT, "double", "float64_t", 64),
  F64VEC2(GLSLLexer.F64VEC2, NumberType.FLOATING_POINT, "dvec2", "f64vec2", 64, 2),
  F64VEC3(GLSLLexer.F64VEC3, NumberType.FLOATING_POINT, "dvec3", "f64vec3", 64, 3),
  F64VEC4(GLSLLexer.F64VEC4, NumberType.FLOATING_POINT, "dvec4", "f64vec4", 64, 4),
  F64MAT2X2(GLSLLexer.F64MAT2X2, NumberType.FLOATING_POINT, "dmat2", "f64mat2x2", 64, 2, 2),
  F64MAT2X3(GLSLLexer.F64MAT2X3, NumberType.FLOATING_POINT, "dmat2x3", "f64mat2x3", 64, 2, 3),
  F64MAT2X4(GLSLLexer.F64MAT2X4, NumberType.FLOATING_POINT, "dmat2x4", "f64mat2x4", 64, 2, 4),
  F64MAT3X2(GLSLLexer.F64MAT3X2, NumberType.FLOATING_POINT, "dmat3x2", "f64mat3x2", 64, 3, 2),
  F64MAT3X3(GLSLLexer.F64MAT3X3, NumberType.FLOATING_POINT, "dmat3", "f64mat3x3", 64, 3, 3),
  F64MAT3X4(GLSLLexer.F64MAT3X4, NumberType.FLOATING_POINT, "dmat3x4", "f64mat3x4", 64, 3, 4),
  F64MAT4X2(GLSLLexer.F64MAT4X2, NumberType.FLOATING_POINT, "dmat4x2", "f64mat4x2", 64, 4, 2),
  F64MAT4X3(GLSLLexer.F64MAT4X3, NumberType.FLOATING_POINT, "dmat4x3", "f64mat4x3", 64, 4, 3),
  F64MAT4X4(GLSLLexer.F64MAT4X4, NumberType.FLOATING_POINT, "dmat4", "f64mat4x4", 64, 4, 4);

  private final int tokenType;
  private final int literalTokenType;
  private final NumberType numberType;
  private final int[] dimensions;
  private final int bitDepth;
  private final String compactName;
  private final String explicitName;

  NumericType(int tokenType,
              NumberType numberType,
              String compactName,
              String explicitName,
              int bitDepth,
              int... dimensions) {
    this(tokenType, Token.INVALID_TYPE, numberType, compactName, explicitName, bitDepth, dimensions);
  }

  /**
   * Creates a new type with the given token type, number type, compact and
   * explicit name, bit depth
   * and dimensions.
   *
   * @param tokenType        The token type in the parser
   * @param literalTokenType The token type of literals of this type
   * @param numberType       The number type
   * @param compactName      The most compact name for this type
   * @param explicitName     The most explicit name for this type
   * @param bitDepth         The bit depth
   * @param dimensions       The size of each dimension, empty for scalars
   */
  NumericType(int tokenType,
              int literalTokenType,
              NumberType numberType,
              String compactName,
              String explicitName,
              int bitDepth,
              int... dimensions) {
    this.tokenType = tokenType;
    this.literalTokenType = literalTokenType;
    this.numberType = numberType;
    this.dimensions = dimensions.length == 0 ? new int[] { 1 } : dimensions;
    this.bitDepth = bitDepth;
    this.compactName = compactName;
    this.explicitName = explicitName;
  }

  /**
   * Returns the token type in the parser.
   *
   * @return The token type in the parser
   */
  public int getTokenType() {
    return tokenType;
  }

  /**
   * Returns the number type.
   *
   * @return The number type
   */
  public NumberType getNumberType() {
    return numberType;
  }

  /**
   * Returns the size of each dimension. (also called the shape)
   *
   * @return The size of each dimension
   */
  public int[] getDimensions() {
    return dimensions;
  }

  public int getDimension() {
    return dimensions.length;
  }

  public boolean isScalar() {
    return dimensions.length == 1 && dimensions[0] == 1;
  }

  public boolean isVector() {
    return dimensions.length == 1 && dimensions[0] > 1;
  }

  public boolean isMatrix() {
    return dimensions.length == 2;
  }

  /**
   * Returns the number of columns. This is 1 for scalars and vectors.
   *
   * @return The number of columns
   */
  public int getColumns() {
    return isMatrix() ? dimensions[0] : 1;
  }

  /**
   * Returns the number of rows. For vectors this is the number of components.
   *
   * @return The number of rows
   */
  public int getRows() {
    return dimensions[dimensions.length - 1];
  }

  /**
   * Returns the total number of scalar components in this type.
   *
   * @return The number of components
   */
  public int getComponentCount() {
    return getColumns() * getRows();
  }

  /**
   * Returns the scalar type of the components of this type.
   *
   * @return The scalar component type, which is this type for scalars
   */
  public NumericType getComponentType() {
    return ofShape(numberType, bitDepth, 1, 1);
  }

  /**
   * Returns the type that results from indexing into this type once: the column
   * vector type for matrices and the component type for vectors and scalars.
   *
   * @return The type of an element of this type
   */
  public NumericType getElementType() {
    return isMatrix() ? withComponentCount(getRows()) : getComponentType();
  }

  /**
   * Returns the scalar or vector type with the same component type and the given
   * number of components.
   *
   * @param componentCount The number of components from 1 to 4
   * @return The scalar or vector type, or null if there is no such type
   */
  public NumericType withComponentCount(int componentCount) {
    return ofShape(numberType, bitDepth, 1, componentCount);
  }

  /**
   * Returns the type with the same shape as this type but with the given scalar
   * type as the component type.
   *
   * @param componentType The scalar type to use for the components
   * @return The type with the same shape, or null if there is no such type (for
   *         example, there are only floating point matrices)
   */
  public NumericType withComponentType(NumericType componentType) {
    return ofShape(componentType.numberType, componentType.bitDepth, getColumns(), getRows());
  }

  /**
   * Returns the scalar or vector type with the given component type and count.
   *
   * @param componentType  The scalar type of the components
   * @param componentCount The number of components from 1 to 4
   * @return The scalar or vector type, or null if there is no such type
   */
  public static NumericType vector(NumericType componentType, int componentCount) {
    return componentType.withComponentCount(componentCount);
  }

  /**
   * Returns the matrix type with the given component type and shape.
   *
   * @param componentType The scalar type of the components
   * @param columns       The number of columns from 2 to 4
   * @param rows          The number of rows from 2 to 4
   * @return The matrix type, or null if there is no such type
   */
  public static NumericType matrix(NumericType componentType, int columns, int rows) {
    return ofShape(componentType.numberType, componentType.bitDepth, columns, rows);
  }

  /**
   * Returns the bit depth.
   *
   * @return The bit depth
   */
  public int getBitDepth() {
    return bitDepth;
  }

  /**
   * Returns the compact name of the type. Some types, notably some that are added
   * by extensions, do not have compact name.
   *
   * @return The type's compact name, or null if the type does not have a compact
   * name.
   */
  public String getCompactName() {
    return compactName;
  }

  public String getMostCompactName() {
    return compactName != null ? compactName : explicitName;
  }

  /**
   * Returns the explicit name of the type. This name uses an explicit arithmetic
   * type name that may not be compatible if the extension for these type names
   * is not available.
   *
   * @return The type's most explicit name.
   */
  public String getExplicitName() {
    return explicitName;
  }

  @Override
  public String getTypeName() {
    return getMostCompactName();
  }

  private static final Map<Integer, NumericType> tokenTypesToValues = new HashMap<>();
  private static final Map<Integer, NumericType> literalTokenTypesToValues = new HashMap<>();
  private static final Map<Integer, NumericType> shapesToValues = new HashMap<>();
  private static final Map<NumberType, EnumSet<NumericType>> numberTypesToValues = new EnumMap<>(NumberType.class);

  private static int shapeKey(NumberType numberType, int bitDepth, int columns, int rows) {
    return ((numberType.ordinal() * 128 + bitDepth) * 8 + columns) * 8 + rows;
  }

  private static NumericType ofShape(NumberType numberType, int bitDepth, int columns, int rows) {
    return shapesToValues.get(shapeKey(numberType, bitDepth, columns, rows));
  }

  static {
    for (NumberType numberType : NumberType.values()) {
      numberTypesToValues.put(numberType, EnumSet.noneOf(NumericType.class));
    }
    for (NumericType entry : values()) {
      tokenTypesToValues.put(entry.tokenType, entry);
      literalTokenTypesToValues.put(entry.literalTokenType, entry);
      shapesToValues.put(shapeKey(entry.numberType, entry.bitDepth, entry.getColumns(), entry.getRows()), entry);
      numberTypesToValues.get(entry.numberType).add(entry);
    }
    literalTokenTypesToValues.remove(Token.INVALID_TYPE);
  }

  static EnumSet<NumericType> ofNumberType(NumberType numberType) {
    return numberTypesToValues.get(numberType);
  }

  /**
   * Returns the set of types that this type can be converted to without a
   * constructor or swizzling in the latest GLSL version with the explicit
   * arithmetic types available. The set includes this type itself. Use
   * {@link Conversions#canImplicitlyConvert(Type, Type, TypeEnvironment)} to
   * check conversions for a specific GLSL version.
   *
   * @return the set of types that this type can be implicitly converted to.
   */
  public EnumSet<NumericType> getImplicitCasts() {
    var result = EnumSet.noneOf(NumericType.class);
    for (NumericType to : values()) {
      if (isImplicitlyCastableTo(to)) {
        result.add(to);
      }
    }
    return result;
  }

  public boolean isImplicitlyCastableTo(NumericType other) {
    return Conversions.canImplicitlyConvert(this, other, TypeEnvironment.LATEST);
  }

  public static NumericType fromToken(Token token) {
    return ofTokenType(token.getType());
  }

  /**
   * Returns the type for the given token type.
   *
   * @param tokenType The token type in the parser
   * @return The type for the given token type index
   * @throws IllegalArgumentException if the token type is not the token type of
   *                                  a numeric type
   */
  public static NumericType ofTokenType(int tokenType) {
    var type = tokenTypesToValues.get(tokenType);
    if (type == null) {
      throw new IllegalArgumentException("Token type is not a numeric type: " + tokenType);
    }
    return type;
  }

  /**
   * Returns the type for the given literal token type.
   *
   * @param literalTokenType The literal token type
   * @return The type for the given literal token type
   */
  public static NumericType ofLiteralTokenType(int literalTokenType) {
    var type = literalTokenTypesToValues.get(literalTokenType);
    if (type == null) {
      throw new IllegalArgumentException("Token type has no literal type: " + literalTokenType);
    }
    return type;
  }

  /**
   * Returns whether there is a literal syntax for values of this type. Only
   * scalar types can have literals, and of those the 8 bit integer types have
   * none.
   *
   * @return true if literals of this type can be written
   */
  public boolean hasLiteral() {
    return literalTokenType != Token.INVALID_TYPE;
  }
}
