package io.github.douira.glsl_transformer.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.BuiltinTypeSpecifierParseableContext;
import io.github.douira.glsl_transformer.generic.ExtendedContext;
import io.github.douira.glsl_transformer.transform.TransformationManager;

/**
 * A tensor abstractly represents the many multidimensional number types that
 * GLSL has. It does this within a single datastructure by modelling scalars,
 * vectors and matrices as n-dimensional and also taking the bit depth into
 * account.
 */
public class Tensor extends ParsableASTNode {
  private static final int MAX_SPACE_DIMENSIONS = 4;
  private static final TypeRegistry TYPE_REGISTRY;

  /**
   * The different ways bits in a tensor can be interpreted.
   */
  public static enum NumberType {
    /**
     * boolean bit usage
     */
    BOOL(1, 4),

    /**
     * uint-like bit usage, non-signed
     */
    INTEGER(64, 4),

    /**
     * int-like bit usage, signed
     */
    SIGNED_INTEGER(64, 4),

    /**
     * floating point bit usage
     */
    FLOATING(64, 4, 4);

    private int[] maxShape;

    private NumberType(int... maxShape) {
      this.maxShape = maxShape;
    }
  }

  /**
   * The shape is an array of up to three integers describing how big this
   * tensor is in each dimension. The first dimension contains the number of bits
   * of each value and the following dimensions describe the actual dimensions of
   * the tensor.
   */
  public static record Type(int tokenType, NumberType numberType, int[] shape, int spaceDimensions,
      int highestDimension,
      String compactName, String explicitName) {
  }

  private static class TypeRegistry {
    int maxIndex = Integer.MIN_VALUE;
    int minIndex = Integer.MAX_VALUE;
    List<Type> entries = new ArrayList<Type>();
    Type[] types;

    void addType(int tokenType, NumberType numberType, String compactName, String explicitName, int bitDepth,
        int... rawShape) {
      var shape = new int[4];
      shape[0] = bitDepth;
      var spaceDimensions = 0;
      int highestDimension = 1;
      for (var i = 1; i < MAX_SPACE_DIMENSIONS; i++) {
        if (i < rawShape.length && rawShape[i] != 1) {
          shape[i] = rawShape[i];
          spaceDimensions++;
          highestDimension = i + 1;
        } else {
          shape[i] = 1;
        }
      }
      entries.add(
          new Type(tokenType, numberType, shape, spaceDimensions, highestDimension, compactName, explicitName));

      maxIndex = Math.max(maxIndex, tokenType);
      minIndex = Math.min(minIndex, tokenType);
    }

    void addType(int tokenType, NumberType numberType, String name, int bitDepth,
        int... rawShape) {
      addType(tokenType, numberType, name, name, bitDepth, rawShape);
    }

    void addTypeBool(int tokenType, String compactName, String explicitName, int bitDepth, int... shape) {
      addType(tokenType, NumberType.BOOL, compactName, explicitName, bitDepth, shape);
    }

    void addTypeInteger(int tokenType, String compactName, String explicitName, int bitDepth, int... shape) {
      addType(tokenType, NumberType.INTEGER, compactName, explicitName, bitDepth, shape);
    }

    void addTypeSignedInteger(int tokenType, String compactName, String explicitName, int bitDepth, int... shape) {
      addType(tokenType, NumberType.SIGNED_INTEGER, compactName, explicitName, bitDepth, shape);
    }

    void addTypeFloating(int tokenType, String compactName, String explicitName, int bitDepth, int... shape) {
      addType(tokenType, NumberType.FLOATING, compactName, explicitName, bitDepth, shape);
    }

    void addTypeBool(int tokenType, String name, int bitDepth, int... shape) {
      addType(tokenType, NumberType.BOOL, name, bitDepth, shape);
    }

    void addTypeInteger(int tokenType, String name, int bitDepth, int... shape) {
      addType(tokenType, NumberType.INTEGER, name, bitDepth, shape);
    }

    void addTypeSignedInteger(int tokenType, String name, int bitDepth, int... shape) {
      addType(tokenType, NumberType.SIGNED_INTEGER, name, bitDepth, shape);
    }

    void addTypeFloating(int tokenType, String name, int bitDepth, int... shape) {
      addType(tokenType, NumberType.FLOATING, name, bitDepth, shape);
    }

    void collect() {
      types = new Type[maxIndex - minIndex + 1];
      for (var entry : entries) {
        var index = entry.tokenType - minIndex;
        if (types[index] != null) {
          throw new Error(
              "A type was registered multiple times for the same token. Fix the Tensor class' initialization!");
        }
        types[index] = entry;
      }
    }

    Type getByTokenType(int tokenType) {
      return types[tokenType - minIndex];
    }
  }

  static {
    TYPE_REGISTRY = new TypeRegistry();

    TYPE_REGISTRY.addTypeBool(GLSLLexer.BOOL, "bool", 1);
    TYPE_REGISTRY.addTypeBool(GLSLLexer.BVEC2, "bvec2", 1, 2);
    TYPE_REGISTRY.addTypeBool(GLSLLexer.BVEC3, "bvec3", 1, 3);
    TYPE_REGISTRY.addTypeBool(GLSLLexer.BVEC4, "bvec4", 1, 4);

    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.INT8, "int8_t", 8);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I8VEC2, "i8vec2", 8, 2);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I8VEC3, "i8vec3", 8, 3);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I8VEC4, "i8vec4", 8, 4);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UINT8, "uint8_t", 8);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI8VEC2, "ui8vec2", 8, 2);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI8VEC3, "ui8vec3", 8, 3);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI8VEC4, "ui8vec4", 8, 4);

    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.INT16, "int16_t", 16);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I16VEC2, "i16vec2", 16, 2);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I16VEC3, "i16vec3", 16, 3);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I16VEC4, "i16vec4", 16, 4);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UINT16, "uint16_t", 16);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI16VEC2, "ui16vec2", 16, 2);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI16VEC3, "ui16vec3", 16, 3);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI16VEC4, "ui16vec4", 16, 4);

    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.INT32, "int", "int32_t", 32);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I32VEC2, "ivec2", "i32vec2", 32, 2);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I32VEC3, "ivec3", "i32vec3", 32, 3);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I32VEC4, "ivec4", "i32vec4", 32, 4);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UINT32, "uint", "uint32_t", 32);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI32VEC2, "uvec2", "u32vec2", 32, 2);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI32VEC3, "uvec3", "u32vec3", 32, 3);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI32VEC4, "uvec4", "u32vec4", 32, 4);

    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.INT64, "int64_t", 64);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I64VEC2, "i64vec2", 64, 2);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I64VEC3, "i64vec3", 64, 3);
    TYPE_REGISTRY.addTypeSignedInteger(GLSLLexer.I64VEC4, "i64vec4", 64, 4);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UINT64, "uint64_t", 64);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI64VEC2, "ui64vec2", 64, 2);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI64VEC3, "ui64vec3", 64, 3);
    TYPE_REGISTRY.addTypeInteger(GLSLLexer.UI64VEC4, "ui64vec4", 64, 4);

    TYPE_REGISTRY.addTypeFloating(GLSLLexer.FLOAT16, "float16_t", 16);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16VEC2, "f16vec2", 16, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16VEC3, "f16vec3", 16, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16VEC4, "f16vec4", 16, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT2X2, "f16mat2", "f16mat2x2", 16, 2, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT2X3, "f16mat2x3", 16, 2, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT2X4, "f16mat2x4", 16, 2, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT3X2, "f16mat3x2", 16, 3, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT3X3, "f16mat3", "f16mat3x3", 16, 3, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT3X4, "f16mat3x4", 16, 3, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT4X2, "f16mat4x2", 16, 4, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT4X3, "f16mat4x3", 16, 4, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F16MAT4X4, "f16mat4", "f16mat4x4", 16, 4, 4);

    TYPE_REGISTRY.addTypeFloating(GLSLLexer.FLOAT32, "float", "float32_t", 32);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32VEC2, "vec2", "f32vec2", 32, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32VEC3, "vec3", "f32vec3", 32, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32VEC4, "vec4", "f32vec4", 32, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT2X2, "mat2", "f32mat2x2", 32, 2, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT2X3, "mat2x3", "f32mat2x3", 32, 2, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT2X4, "mat2x4", "f32mat2x4", 32, 2, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT3X2, "mat3x2", "f32mat3x2", 32, 3, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT3X3, "mat3", "f32mat3x3", 32, 3, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT3X4, "mat3x4", "f32mat3x4", 32, 3, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT4X2, "mat4x2", "f32mat4x2", 32, 4, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT4X3, "mat4x3", "f32mat4x3", 32, 4, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F32MAT4X4, "mat4", "f32mat4x4", 32, 4, 4);

    TYPE_REGISTRY.addTypeFloating(GLSLLexer.FLOAT64, "double", "float64_t", 64);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64VEC2, "dvec2", "f64vec2", 64, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64VEC3, "dvec3", "f64vec3", 64, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64VEC4, "dvec4", "f64vec4", 64, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT2X2, "dmat2", "f64mat2x2", 64, 2, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT2X3, "dmat2x3", "f64mat2x3", 64, 2, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT2X4, "dmat2x4", "f64mat2x4", 64, 2, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT3X2, "dmat3x2", "f64mat3x2", 64, 3, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT3X3, "dmat3", "f64mat3x3", 64, 3, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT3X4, "dmat3x4", "f64mat3x4", 64, 3, 4);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT4X2, "dmat4x2", "f64mat4x2", 64, 4, 2);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT4X3, "dmat4x3", "f64mat4x3", 64, 4, 3);
    TYPE_REGISTRY.addTypeFloating(GLSLLexer.F64MAT4X4, "dmat4", "f64mat4x4", 64, 4, 4);

    TYPE_REGISTRY.collect();
  }

  // TODO: getter/setters when there are more Tensor features in general
  private Type type;

  /**
   * Creates a new tensor with the given type.
   * 
   * @param type The type of the new tensor
   */
  public Tensor(Type type) {
    this.type = type;
  }

  /**
   * Creates a new tensor with the type given as a lexer token index.
   * 
   * @see io.github.douira.glsl_transformer.GLSLLexer
   * 
   * @param tokenType The token type index
   */
  public Tensor(int tokenType) {
    this(TYPE_REGISTRY.getByTokenType(tokenType));
  }

  /**
   * Creates a new tensor from the given type specifier parse tree node.
   * 
   * @param ctx The parse tree node to read the type from
   */
  public Tensor(BuiltinTypeSpecifierParseableContext ctx) {
    this(getTypeSpecifierType(ctx));
  }

  /**
   * Creates a new tensor by parsing the type specified in the given string. The
   * string should contain a builtin type specifier. See the parser grammar for
   * details.
   * 
   * @param str The string to parse as a GLSL type specifier
   */
  public Tensor(String str) {
    this(TransformationManager.INTERNAL.parse(str, GLSLParser::builtinTypeSpecifierParseable));
  }

  private static int getTypeSpecifierType(BuiltinTypeSpecifierParseableContext ctx) {
    var children = ctx.children;
    if (children.size() != 1) {
      throw new IllegalArgumentException("Invalid type specifier context given. It must have exactly one child.");
    }

    var child = children.get(0);
    Token token;
    if (child instanceof TerminalNode terminalNode) {
      token = terminalNode.getSymbol();
    } else {
      throw new IllegalArgumentException(
          "Type specifier context child has the wrong structure. It should be a terminal node.");
    }

    return token.getType();
  }

  /**
   * Returns the compact name of the contained type. This is the shortest
   * available name that specifies this type.
   * 
   * @return The type's most compact name
   */
  public String getCompactName() {
    return type.compactName();
  }

  /**
   * Returns the explicit name of the contained type. This name uses an explicit
   * arithmetic type name that may not be compatible if the extension for these
   * type names is not available.
   * 
   * @return The type's most explicit name
   */
  public String getExplicitName() {
    return type.explicitName();
  }

  @Override
  protected String getPrinted() {
    return type.compactName();
  }

  @Override
  protected Function<GLSLParser, ExtendedContext> getOutputParseMethod() {
    return GLSLParser::builtinTypeSpecifierParseable;
  }
}
