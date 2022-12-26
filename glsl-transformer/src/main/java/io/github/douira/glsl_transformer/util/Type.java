package io.github.douira.glsl_transformer.util;

import java.util.*;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.TokenTyped;

/**
 * This enum represents the type of a value in GLSL and contains easily accessible
 * data about each of them.
 * 
 * The shape is an array of up to three integers describing how big this
 * tensor is in each dimension. The first dimension contains the number of bits
 * of each value and the following dimensions describe the actual dimensions of
 * the tensor.
 */
public enum Type implements TokenTyped {
  BOOL(GLSLLexer.BOOL, GLSLLexer.BOOLCONSTANT, NumberType.BOOLEAN, "bool", "bool", 1),
  BVEC2(GLSLLexer.BVEC2, NumberType.BOOLEAN, "bvec2", "bvec2", 1, 2),
  BVEC3(GLSLLexer.BVEC3, NumberType.BOOLEAN, "bvec3", "bvec3", 1, 3),
  BVEC4(GLSLLexer.BVEC4, NumberType.BOOLEAN, "bvec4", "bvec4", 1, 4),

  INT8(GLSLLexer.INT8, NumberType.SIGNED_INTEGER, null, "int8_t", 8),
  I8VEC2(GLSLLexer.I8VEC2, NumberType.SIGNED_INTEGER, null, "i8vec2", 8, 2),
  I8VEC3(GLSLLexer.I8VEC3, NumberType.SIGNED_INTEGER, null, "i8vec3", 8, 3),
  I8VEC4(GLSLLexer.I8VEC4, NumberType.SIGNED_INTEGER, null, "i8vec4", 8, 4),
  UINT8(GLSLLexer.UINT8, NumberType.UNSIGNED_INTEGER, null, "uint8_t", 8),
  U8VEC2(GLSLLexer.U8VEC2, NumberType.UNSIGNED_INTEGER, null, "ui8vec2", 8, 2),
  U8VEC3(GLSLLexer.U8VEC3, NumberType.UNSIGNED_INTEGER, null, "ui8vec3", 8, 3),
  U8VEC4(GLSLLexer.U8VEC4, NumberType.UNSIGNED_INTEGER, null, "ui8vec4", 8, 4),

  INT16(GLSLLexer.INT16, GLSLLexer.INT16CONSTANT, NumberType.SIGNED_INTEGER, null, "int16_t", 16),
  I16VEC2(GLSLLexer.I16VEC2, NumberType.SIGNED_INTEGER, null, "i16vec2", 16, 2),
  I16VEC3(GLSLLexer.I16VEC3, NumberType.SIGNED_INTEGER, null, "i16vec3", 16, 3),
  I16VEC4(GLSLLexer.I16VEC4, NumberType.SIGNED_INTEGER, null, "i16vec4", 16, 4),
  UINT16(GLSLLexer.UINT16, GLSLLexer.UINT16CONSTANT, NumberType.UNSIGNED_INTEGER, null, "uint16_t", 16),
  U16VEC2(GLSLLexer.U16VEC2, NumberType.UNSIGNED_INTEGER, null, "ui16vec2", 16, 2),
  U16VEC3(GLSLLexer.U16VEC3, NumberType.UNSIGNED_INTEGER, null, "ui16vec3", 16, 3),
  U16VEC4(GLSLLexer.U16VEC4, NumberType.UNSIGNED_INTEGER, null, "ui16vec4", 16, 4),

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
  U64VEC2(GLSLLexer.U64VEC2, NumberType.UNSIGNED_INTEGER, null, "ui64vec2", 64, 2),
  U64VEC3(GLSLLexer.U64VEC3, NumberType.UNSIGNED_INTEGER, null, "ui64vec3", 64, 3),
  U64VEC4(GLSLLexer.U64VEC4, NumberType.UNSIGNED_INTEGER, null, "ui64vec4", 64, 4),

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
    private EnumSet<Type> registeredTypes; // lazy init

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
     * @return An EnumSet of all the Types which use this number type. This is
     *         created
     *         after all Types have been created.
     */
    public EnumSet<Type> getRegisteredTypes() {
      return registeredTypes;
    }
  }

  private final int tokenType;
  private final int literalTokenType;
  private final NumberType numberType;
  private final int[] dimensions;
  private final int bitDepth;
  private final String compactName;
  private final String explicitName;

  // lazily created
  private EnumSet<Type> implicitCastTypes;

  // can't be static
  private final int[] SCALAR_DIMENSIONS = { 1 };

  Type(int tokenType,
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
   * @param tokenType    The token type in the parser
   * @param numberType   The number type
   * @param compactName  The most compact name for this type
   * @param explicitName The most explicit name for this type
   * @param bitDepth     The bit depth
   * @param dimensions   The size of each dimension
   */
  Type(int tokenType,
      int literalTokenType,
      NumberType numberType,
      String compactName,
      String explicitName,
      int bitDepth,
      int... dimensions) {
    // verify inputs
    if (bitDepth > numberType.getMaxBitDepth()) {
      throw new IllegalArgumentException(
          "Bit depth provided is larger than maximum bit depth for type " + numberType);
    }

    if (dimensions.length < 1) {
      dimensions = SCALAR_DIMENSIONS;
    } else {
      int[] maxDimensions = numberType.getMaxDimensions();
      if (dimensions.length > maxDimensions.length) {
        throw new IllegalArgumentException(
            "Dimensions provided is longer than maximum dimensions for type " + numberType);
      }

      for (int i = 0; i < dimensions.length; i++) {
        int dimSize = dimensions[i];
        int maxDimSize = maxDimensions[i];
        if (dimSize > maxDimSize) {
          throw new IllegalArgumentException("Dimensions provided exceeds maximum dimensions for type " + numberType);
        }
      }
    }

    this.tokenType = tokenType;
    this.literalTokenType = literalTokenType;
    this.numberType = numberType;
    this.dimensions = dimensions;
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
    return dimensions == SCALAR_DIMENSIONS;
  }

  public boolean isVector() {
    return dimensions.length == 1;
  }

  public boolean isMatrix() {
    return dimensions.length == 2;
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
   *         name.
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

  /**
   * Returns the set of types that this type can be converted to without a
   * constructor or swizzling.
   *
   * @return the set of types that this type can be implicitly converted to.
   */
  public EnumSet<Type> getImplicitCasts() {
    return implicitCastTypes;
  }

  private static final Type[] tokenTypesToValues;
  private static final Map<Integer, Type> literalTokenTypesToValues;
  private static final int minIndex;

  static {
    // figure out the token indexes of the token types used for the type enum
    int localMinIndex = Integer.MAX_VALUE;
    int localMaxIndex = Integer.MIN_VALUE;
    for (Type entry : values()) {
      int tokenType = entry.getTokenType();
      if (tokenType < localMinIndex) {
        localMinIndex = tokenType;
      }
      if (tokenType > localMaxIndex) {
        localMaxIndex = tokenType;
      }
    }

    // create a mapping array between the token types and the type enum
    minIndex = localMinIndex;
    Type[] localTokensTypesToValues = new Type[localMaxIndex - localMinIndex + 1];
    literalTokenTypesToValues = new HashMap<>();
    for (Type entry : values()) {
      int index = entry.tokenType - minIndex;
      if (localTokensTypesToValues[index] != null) {
        throw new AssertionError(
            "A type was registered multiple times for the same token. Fix the Tensor class' initialization!");
      }
      localTokensTypesToValues[index] = entry;

      if (entry.literalTokenType != Token.INVALID_TYPE) {
        literalTokenTypesToValues.put(entry.literalTokenType, entry);
      }
    }
    tokenTypesToValues = localTokensTypesToValues;

    // register the types to enum sets for each of the number types
    // (inverse mapping)
    for (Type entry : values()) {
      EnumSet<Type> registeredTypes = entry.numberType.registeredTypes;
      if (registeredTypes != null) {
        registeredTypes.add(entry);
      } else {
        entry.numberType.registeredTypes = EnumSet.of(entry);
      }
    }

    // calculate possible implicit casts for each type
    for (Type t1 : values()) {
      EnumSet<Type> implicitCastTypes = EnumSet.noneOf(Type.class);
      t1.implicitCastTypes = implicitCastTypes;
      for (Type t2 : values()) {
        boolean canCast = t1.equals(t2) || (Arrays.equals(t1.dimensions, t2.dimensions) && switch (t1.numberType) {
          case BOOLEAN -> false;
          case SIGNED_INTEGER -> switch (t2.numberType) {
            case UNSIGNED_INTEGER, SIGNED_INTEGER, FLOATING_POINT -> t2.bitDepth >= t1.bitDepth;
            default -> false;
          };
          case UNSIGNED_INTEGER -> switch (t2.numberType) {
            case UNSIGNED_INTEGER, SIGNED_INTEGER -> t2.bitDepth > t1.bitDepth;
            case FLOATING_POINT -> t2.bitDepth >= t1.bitDepth;
            default -> false;
          };
          case FLOATING_POINT -> t2.numberType.equals(NumberType.FLOATING_POINT) && t2.bitDepth >= t1.bitDepth;
        });

        if (canCast) {
          implicitCastTypes.add(t2);
        }
      }
    }
  }

  public static Type fromToken(Token token) {
    return ofTokenType(token.getType());
  }

  /**
   * Returns the type for the given token type.
   * 
   * @param tokenType The token type in the parser
   * @return The type for the given token type index
   */
  public static Type ofTokenType(int tokenType) {
    return tokenTypesToValues[tokenType - minIndex];
  }

  /**
   * Returns the type for the given literal token type.
   *
   * @param literalTokenType The literal token type
   * @return The type for the given literal token type
   */
  public static Type ofLiteralTokenType(int literalTokenType) {
    var type = literalTokenTypesToValues.get(literalTokenType);
    if (type == null) {
      throw new IllegalArgumentException("Token type has no literal type: " + literalTokenType);
    }
    return type;
  }
}
