package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.NumericType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;

public class ConversionsTest {
  private static TypeEnvironment env(Version version, String... extensions) {
    return new TypeEnvironment(version, null, null, Set.of(extensions));
  }

  // the conversions of GL_EXT_shader_explicit_arithmetic_types as written in
  // the tables of the specification of the extension
  private static final Map<NumericType, Set<NumericType>> EXPLICIT = Map.ofEntries(
      Map.entry(BOOL, Set.of()),
      Map.entry(INT8, Set.of(INT16, INT32, INT64, UINT8, UINT16, UINT32, UINT64, FLOAT16, FLOAT32, FLOAT64)),
      Map.entry(INT16, Set.of(INT32, INT64, UINT16, UINT32, UINT64, FLOAT16, FLOAT32, FLOAT64)),
      Map.entry(INT32, Set.of(INT64, UINT32, UINT64, FLOAT32, FLOAT64)),
      Map.entry(INT64, Set.of(UINT64, FLOAT64)),
      Map.entry(UINT8, Set.of(INT16, INT32, INT64, UINT16, UINT32, UINT64, FLOAT16, FLOAT32, FLOAT64)),
      Map.entry(UINT16, Set.of(INT32, INT64, UINT32, UINT64, FLOAT16, FLOAT32, FLOAT64)),
      Map.entry(UINT32, Set.of(INT64, UINT64, FLOAT32, FLOAT64)),
      Map.entry(UINT64, Set.of(FLOAT64)),
      Map.entry(FLOAT16, Set.of(FLOAT32, FLOAT64)),
      Map.entry(FLOAT32, Set.of(FLOAT64)),
      Map.entry(FLOAT64, Set.of()));

  private static final Set<NumericType> CORE = Set.of(INT32, UINT32, FLOAT32, FLOAT64);

  /**
   * Checks the whole matrix of conversions between all numeric types in an
   * environment. The conversions between the core scalar types are given, all
   * others have to be those of the explicit arithmetic types extension.
   */
  private static void assertConversions(TypeEnvironment env, Map<NumericType, Set<NumericType>> core) {
    for (var from : NumericType.values()) {
      for (var to : NumericType.values()) {
        var fromScalar = from.getComponentType();
        var toScalar = to.getComponentType();
        var scalarConverts = CORE.contains(fromScalar) && CORE.contains(toScalar)
            ? core.getOrDefault(fromScalar, Set.of()).contains(toScalar)
            : EXPLICIT.get(fromScalar).contains(toScalar);
        var expected = from == to
            || Arrays.equals(from.getDimensions(), to.getDimensions()) && scalarConverts;
        assertEquals(expected, Conversions.canImplicitlyConvert(from, to, env),
            from + " to " + to + " in " + env);
      }
    }
  }

  private static final Map<NumericType, Set<NumericType>> NONE = Map.of();
  private static final Map<NumericType, Set<NumericType>> INT_TO_FLOAT = Map.of(INT32, Set.of(FLOAT32));
  private static final Map<NumericType, Set<NumericType>> TO_FLOAT = Map.of(
      INT32, Set.of(FLOAT32), UINT32, Set.of(FLOAT32));
  private static final Map<NumericType, Set<NumericType>> TO_FLOAT_AND_UINT = Map.of(
      INT32, Set.of(UINT32, FLOAT32), UINT32, Set.of(FLOAT32));
  private static final Map<NumericType, Set<NumericType>> TO_FLOAT_AND_DOUBLE = Map.of(
      INT32, Set.of(FLOAT32, FLOAT64), UINT32, Set.of(FLOAT32, FLOAT64), FLOAT32, Set.of(FLOAT64));
  private static final Map<NumericType, Set<NumericType>> ALL = Map.of(
      INT32, Set.of(UINT32, FLOAT32, FLOAT64), UINT32, Set.of(FLOAT32, FLOAT64), FLOAT32, Set.of(FLOAT64));

  @Test
  void testConversionMatrixPerVersion() {
    // 4.1.10 of the specification of each version. Since there is no uint before
    // 1.30, a conversion from it can be allowed from 1.20 on without consequence.
    assertConversions(env(Version.GLSL11), NONE);
    assertConversions(env(Version.GLSL12), TO_FLOAT);
    assertConversions(env(Version.GLSL13), TO_FLOAT);
    assertConversions(env(Version.GLSL14), TO_FLOAT);
    assertConversions(env(Version.GLSL15), TO_FLOAT);
    assertConversions(env(Version.GLSL33), TO_FLOAT);
    assertConversions(env(Version.GLSL40), ALL);
    assertConversions(env(Version.GLSL41), ALL);
    assertConversions(env(Version.GLSL42), ALL);
    assertConversions(env(Version.GLSL43), ALL);
    assertConversions(env(Version.GLSL44), ALL);
    assertConversions(env(Version.GLSL45), ALL);
    assertConversions(env(Version.GLSL46), ALL);
    assertConversions(TypeEnvironment.LATEST, ALL);
  }

  @Test
  void testConversionMatrixES() {
    assertConversions(env(Version.GLSLES10), NONE);
    assertConversions(env(Version.GLSLES30), NONE);
    assertConversions(env(Version.GLSLES31), NONE);
    assertConversions(env(Version.GLSLES32), NONE);
    assertConversions(env(Version.GLSLES31, "GL_EXT_shader_implicit_conversions"), TO_FLOAT_AND_UINT);
    assertConversions(env(Version.GLSLES32, TypeEnvironment.ALL_EXTENSIONS), ALL);
  }

  @Test
  void testConversionMatrixExtensions() {
    assertConversions(env(Version.GLSL33, "GL_ARB_gpu_shader5"), TO_FLOAT_AND_UINT);
    assertConversions(env(Version.GLSL33, "GL_ARB_gpu_shader_fp64"), TO_FLOAT_AND_DOUBLE);
    assertConversions(env(Version.GLSL33, "GL_ARB_gpu_shader5", "GL_ARB_gpu_shader_fp64"), ALL);
    assertConversions(env(Version.GLSL33, "GL_ARB_unrelated"), TO_FLOAT);
    assertConversions(env(Version.GLSL11, "GL_EXT_shader_implicit_conversions"), TO_FLOAT_AND_UINT);
    assertConversions(env(Version.GLSL11, TypeEnvironment.ALL_EXTENSIONS), ALL);
  }

  @Test
  void testSpecificConversions() {
    // int to uint is rejected at 3.30 and accepted at 4.00
    assertFalse(Conversions.canImplicitlyConvert(INT32, UINT32, env(Version.GLSL33)));
    assertTrue(Conversions.canImplicitlyConvert(INT32, UINT32, env(Version.GLSL40)));
    assertFalse(Conversions.canImplicitlyConvert(I32VEC3, U32VEC3, env(Version.GLSL33)));
    assertTrue(Conversions.canImplicitlyConvert(I32VEC3, U32VEC3, env(Version.GLSL40)));

    // no conversions at 1.10
    assertFalse(Conversions.canImplicitlyConvert(INT32, FLOAT32, env(Version.GLSL11)));
    assertTrue(Conversions.canImplicitlyConvert(INT32, FLOAT32, env(Version.GLSL12)));
  }

  private static final Type STRUCT_A = new StructType("A", new LinkedHashMap<>(Map.of("x", FLOAT32)), null);
  private static final Type STRUCT_B = new StructType("A", new LinkedHashMap<>(Map.of("x", FLOAT32)), null);

  private static ArrayType array(Type element, ArraySize size) {
    return new ArrayType(element, size);
  }

  @Test
  void testSameType() {
    assertTrue(Conversions.isSameType(FLOAT32, FLOAT32));
    assertFalse(Conversions.isSameType(FLOAT32, INT32));
    assertTrue(Conversions.isSameType(FixedType.SAMPLER2D, FixedType.SAMPLER2D));
    assertFalse(Conversions.isSameType(FixedType.SAMPLER2D, FixedType.VOID));
    assertFalse(Conversions.isSameType(FixedType.VOID, FLOAT32));
    assertTrue(Conversions.isSameType(StringType.INSTANCE, StringType.INSTANCE));
    assertFalse(Conversions.isSameType(StringType.INSTANCE, FLOAT32));

    // structs are nominal
    assertTrue(Conversions.isSameType(STRUCT_A, STRUCT_A));
    assertFalse(Conversions.isSameType(STRUCT_A, STRUCT_B));

    // the error type is the same as anything
    assertTrue(Conversions.isSameType(ErrorType.INSTANCE, FLOAT32));
    assertTrue(Conversions.isSameType(STRUCT_A, ErrorType.INSTANCE));
    assertTrue(Conversions.isSameType(ErrorType.INSTANCE, ErrorType.INSTANCE));

    // arrays are the same if the elements are and the sizes can be
    assertTrue(Conversions.isSameType(array(FLOAT32, ArraySize.of(2)), array(FLOAT32, ArraySize.of(2))));
    assertFalse(Conversions.isSameType(array(FLOAT32, ArraySize.of(2)), array(FLOAT32, ArraySize.of(3))));
    assertFalse(Conversions.isSameType(array(FLOAT32, ArraySize.of(2)), array(INT32, ArraySize.of(2))));
    assertTrue(Conversions.isSameType(array(FLOAT32, ArraySize.of(2)), array(FLOAT32, ArraySize.UNSIZED)));
    assertTrue(Conversions.isSameType(array(FLOAT32, ArraySize.UNKNOWN), array(FLOAT32, ArraySize.of(3))));
    assertTrue(Conversions.isSameType(array(FLOAT32, ArraySize.UNKNOWN), array(FLOAT32, ArraySize.UNSIZED)));
    assertTrue(Conversions.isSameType(array(ErrorType.INSTANCE, ArraySize.of(2)), array(FLOAT32, ArraySize.of(2))));
    assertFalse(Conversions.isSameType(array(FLOAT32, ArraySize.of(2)), FLOAT32));
    assertFalse(Conversions.isSameType(FLOAT32, array(FLOAT32, ArraySize.of(2))));
    assertTrue(Conversions.isSameType(
        array(array(FLOAT32, ArraySize.of(2)), ArraySize.of(3)),
        array(array(FLOAT32, ArraySize.of(2)), ArraySize.of(3))));
    assertFalse(Conversions.isSameType(
        array(array(FLOAT32, ArraySize.of(2)), ArraySize.of(3)),
        array(array(FLOAT32, ArraySize.of(4)), ArraySize.of(3))));
  }

  @Test
  void testNonNumericConversions() {
    var env = TypeEnvironment.LATEST;
    assertTrue(Conversions.canImplicitlyConvert(STRUCT_A, STRUCT_A, env));
    assertFalse(Conversions.canImplicitlyConvert(STRUCT_A, STRUCT_B, env));
    assertFalse(Conversions.canImplicitlyConvert(STRUCT_A, FLOAT32, env));
    assertFalse(Conversions.canImplicitlyConvert(FLOAT32, STRUCT_A, env));
    assertTrue(Conversions.canImplicitlyConvert(ErrorType.INSTANCE, STRUCT_A, env));
    assertTrue(Conversions.canImplicitlyConvert(FLOAT32, ErrorType.INSTANCE, env));

    // there are no conversions between arrays
    assertFalse(Conversions.canImplicitlyConvert(
        array(INT32, ArraySize.of(2)), array(FLOAT32, ArraySize.of(2)), env));
    assertTrue(Conversions.canImplicitlyConvert(
        array(INT32, ArraySize.of(2)), array(INT32, ArraySize.UNSIZED), env));
  }

  @Test
  void testBinaryResult() {
    var env = TypeEnvironment.LATEST;
    assertSame(INT32, Conversions.binaryResult(INT32, INT32, env));
    assertSame(UINT32, Conversions.binaryResult(INT32, UINT32, env));
    assertSame(UINT32, Conversions.binaryResult(UINT32, INT32, env));
    assertSame(FLOAT32, Conversions.binaryResult(INT32, FLOAT32, env));
    assertSame(FLOAT64, Conversions.binaryResult(FLOAT64, UINT32, env));
    assertSame(BOOL, Conversions.binaryResult(BOOL, BOOL, env));
    assertNull(Conversions.binaryResult(BOOL, INT32, env));
    assertNull(Conversions.binaryResult(FLOAT32, BOOL, env));

    // the rules of the explicit arithmetic types
    assertSame(FLOAT16, Conversions.binaryResult(INT8, FLOAT16, env));
    assertSame(INT64, Conversions.binaryResult(UINT32, INT64, env));
    assertSame(UINT64, Conversions.binaryResult(UINT64, INT64, env));
    assertSame(INT32, Conversions.binaryResult(UINT8, INT32, env));
    assertSame(UINT16, Conversions.binaryResult(INT8, UINT16, env));
    assertSame(INT16, Conversions.binaryResult(INT16, INT8, env));
    assertNull(Conversions.binaryResult(INT32, FLOAT16, env));

    var old = env(Version.GLSL33);
    assertNull(Conversions.binaryResult(INT32, UINT32, old));
    assertSame(FLOAT32, Conversions.binaryResult(UINT32, FLOAT32, old));
    assertNull(Conversions.binaryResult(INT32, FLOAT32, env(Version.GLSL11)));
  }

  @Test
  void testCommonType() {
    var env = TypeEnvironment.LATEST;
    assertSame(F32VEC3, Conversions.commonType(I32VEC3, F32VEC3, env));
    assertSame(F32VEC3, Conversions.commonType(F32VEC3, I32VEC3, env));
    assertSame(F64MAT2X2, Conversions.commonType(F32MAT2X2, F64MAT2X2, env));
    assertSame(BVEC2, Conversions.commonType(BVEC2, BVEC2, env));
    assertNull(Conversions.commonType(F32VEC3, F32VEC2, env));
    assertNull(Conversions.commonType(F32VEC3, FLOAT32, env));
    assertNull(Conversions.commonType(BOOL, INT32, env));
    assertSame(STRUCT_A, Conversions.commonType(STRUCT_A, STRUCT_A, env));
    assertNull(Conversions.commonType(STRUCT_A, STRUCT_B, env));
    assertNull(Conversions.commonType(STRUCT_A, FLOAT32, env));
    assertNull(Conversions.commonType(FLOAT32, STRUCT_A, env));
    assertSame(FLOAT32, Conversions.commonType(ErrorType.INSTANCE, FLOAT32, env));
    assertSame(FLOAT32, Conversions.commonType(FLOAT32, ErrorType.INSTANCE, env));
    assertSame(STRUCT_A, Conversions.commonType(STRUCT_A, ErrorType.INSTANCE, env));
    assertSame(ErrorType.INSTANCE, Conversions.commonType(ErrorType.INSTANCE, ErrorType.INSTANCE, env));
  }

  @Test
  void testBetterConversion() {
    // an exact match is better than a conversion
    assertTrue(Conversions.isBetterConversion(INT32, INT32, FLOAT32));
    assertFalse(Conversions.isBetterConversion(INT32, FLOAT32, INT32));
    assertFalse(Conversions.isBetterConversion(INT32, INT32, INT32));
    assertFalse(Conversions.isBetterConversion(INT32, FLOAT32, FLOAT32));

    // float to double is better than any other conversion
    assertTrue(Conversions.isBetterConversion(FLOAT32, FLOAT64, F32VEC2));
    assertTrue(Conversions.isBetterConversion(FLOAT16, FLOAT64, FLOAT32));
    assertFalse(Conversions.isBetterConversion(FLOAT16, FLOAT32, FLOAT64));

    // int to float is better than int to double
    assertTrue(Conversions.isBetterConversion(INT32, FLOAT32, FLOAT64));
    assertFalse(Conversions.isBetterConversion(INT32, FLOAT64, FLOAT32));
    assertTrue(Conversions.isBetterConversion(UINT32, FLOAT32, FLOAT64));
    assertTrue(Conversions.isBetterConversion(I32VEC2, F32VEC2, F64VEC2));

    // neither int to uint nor int to float is better
    assertFalse(Conversions.isBetterConversion(INT32, UINT32, FLOAT32));
    assertFalse(Conversions.isBetterConversion(INT32, FLOAT32, UINT32));
    assertFalse(Conversions.isBetterConversion(INT32, UINT32, FLOAT64));
    assertFalse(Conversions.isBetterConversion(FLOAT16, FLOAT32, F32VEC2));

    // integral promotions of the explicit arithmetic types are better
    assertTrue(Conversions.isBetterConversion(INT16, INT32, INT64));
    assertFalse(Conversions.isBetterConversion(INT16, INT64, INT32));
    assertTrue(Conversions.isBetterConversion(UINT8, INT32, UINT32));
    assertTrue(Conversions.isBetterConversion(INT16, INT32, FLOAT64));
    assertFalse(Conversions.isBetterConversion(INT16, FLOAT64, INT32));
    assertFalse(Conversions.isBetterConversion(INT64, INT32, FLOAT64));
    assertFalse(Conversions.isBetterConversion(UINT32, INT32, UINT64));

    // types that are not numeric are never converted
    assertFalse(Conversions.isBetterConversion(STRUCT_A, STRUCT_A, STRUCT_B));
    assertFalse(Conversions.isBetterConversion(ErrorType.INSTANCE, INT32, FLOAT32));
    assertFalse(Conversions.isBetterConversion(INT32, STRUCT_A, FLOAT32));
    assertFalse(Conversions.isBetterConversion(INT32, FLOAT32, STRUCT_A));
  }
}
