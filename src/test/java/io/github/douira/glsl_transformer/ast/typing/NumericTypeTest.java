package io.github.douira.glsl_transformer.ast.typing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLLexer;

public class NumericTypeTest {
  @Test
  void testShapeHelpersAreExclusive() {
    for (var type : NumericType.values()) {
      var shapes = (type.isScalar() ? 1 : 0) + (type.isVector() ? 1 : 0) + (type.isMatrix() ? 1 : 0);
      assertEquals(1, shapes, "Exactly one of isScalar, isVector and isMatrix should hold for " + type);
      assertEquals(type.isMatrix() ? 2 : 1, type.getDimension(), type.toString());
      assertEquals(type.getColumns() * type.getRows(), type.getComponentCount(), type.toString());
      assertTrue(type.getComponentType().isScalar(), type.toString());
      assertSame(type.getNumberType(), type.getComponentType().getNumberType(), type.toString());
      assertEquals(type.getBitDepth(), type.getComponentType().getBitDepth(), type.toString());
      assertSame(type, type.withComponentType(type.getComponentType()), type.toString());
      assertNotNull(type.getTypeName());
      assertEquals(type.getMostCompactName(), type.getTypeName());
      assertEquals(type.getTypeName(), type.getCompactName() == null ? type.getExplicitName() : type.getCompactName());
      assertTrue(type.getNumberType().getRegisteredTypes().contains(type));
      assertEquals(type.isScalar() && type.getBitDepth() != 8, type.hasLiteral(), type.toString());
    }
  }

  @Test
  void testScalarsAreNotVectors() {
    assertFalse(NumericType.FLOAT32.isVector());
    assertTrue(NumericType.FLOAT32.isScalar());
    assertFalse(NumericType.BOOL.isVector());
    assertTrue(NumericType.F32VEC2.isVector());
    assertFalse(NumericType.F32VEC2.isScalar());
    assertFalse(NumericType.F32MAT2X2.isVector());
    assertFalse(NumericType.F32MAT2X2.isScalar());
    assertArrayEquals(new int[] { 1 }, NumericType.INT32.getDimensions());
    assertArrayEquals(new int[] { 3 }, NumericType.I32VEC3.getDimensions());
    assertArrayEquals(new int[] { 2, 4 }, NumericType.F32MAT2X4.getDimensions());
  }

  @Test
  void testShapes() {
    assertEquals(1, NumericType.FLOAT32.getColumns());
    assertEquals(1, NumericType.FLOAT32.getRows());
    assertEquals(1, NumericType.F32VEC3.getColumns());
    assertEquals(3, NumericType.F32VEC3.getRows());
    assertEquals(2, NumericType.F32MAT2X4.getColumns());
    assertEquals(4, NumericType.F32MAT2X4.getRows());
    assertEquals(8, NumericType.F32MAT2X4.getComponentCount());

    assertSame(NumericType.FLOAT32, NumericType.F32VEC3.getElementType());
    assertSame(NumericType.FLOAT32, NumericType.FLOAT32.getElementType());
    assertSame(NumericType.F32VEC4, NumericType.F32MAT2X4.getElementType());
    assertSame(NumericType.F64VEC3, NumericType.F64MAT3X3.getElementType());

    assertSame(NumericType.I32VEC2, NumericType.INT32.withComponentCount(2));
    assertSame(NumericType.INT32, NumericType.I32VEC4.withComponentCount(1));
    assertSame(NumericType.F32VEC2, NumericType.F32MAT3X3.withComponentCount(2));
    assertNull(NumericType.INT32.withComponentCount(5));

    assertSame(NumericType.F64MAT2X3, NumericType.F32MAT2X3.withComponentType(NumericType.FLOAT64));
    assertSame(NumericType.U32VEC3, NumericType.F32VEC3.withComponentType(NumericType.UINT32));
    assertNull(NumericType.F32MAT2X3.withComponentType(NumericType.INT32));

    assertSame(NumericType.BVEC3, NumericType.vector(NumericType.BOOL, 3));
    assertSame(NumericType.BOOL, NumericType.vector(NumericType.BOOL, 1));
    assertSame(NumericType.F16MAT4X2, NumericType.matrix(NumericType.FLOAT16, 4, 2));
    assertSame(NumericType.F32VEC3, NumericType.matrix(NumericType.FLOAT32, 1, 3));
    assertNull(NumericType.matrix(NumericType.INT32, 2, 2));
  }

  @Test
  void testTokenLookup() {
    var tokenTypes = new HashSet<Integer>();
    for (var type : NumericType.values()) {
      assertSame(type, NumericType.ofTokenType(type.getTokenType()));
      assertSame(type, NumericType.fromToken(new CommonToken(type.getTokenType())));
      tokenTypes.add(type.getTokenType());
    }

    // below and above the range of token types of the numeric types and in gaps
    // of the range, of which there are none with the current lexer grammar
    var min = Collections.min(tokenTypes);
    var max = Collections.max(tokenTypes);
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofTokenType(min - 1));
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofTokenType(max + 1));
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofTokenType(-1));
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofTokenType(Integer.MAX_VALUE));
    for (var tokenType = 0; tokenType <= GLSLLexer.VOCABULARY.getMaxTokenType(); tokenType++) {
      if (!tokenTypes.contains(tokenType)) {
        var other = tokenType;
        assertThrows(IllegalArgumentException.class, () -> NumericType.ofTokenType(other));
      }
    }
  }

  @Test
  void testLiteralTokenLookup() {
    assertSame(NumericType.INT32, NumericType.ofLiteralTokenType(GLSLLexer.INT32CONSTANT));
    assertSame(NumericType.UINT64, NumericType.ofLiteralTokenType(GLSLLexer.UINT64CONSTANT));
    assertSame(NumericType.FLOAT16, NumericType.ofLiteralTokenType(GLSLLexer.FLOAT16CONSTANT));
    assertSame(NumericType.BOOL, NumericType.ofLiteralTokenType(GLSLLexer.BOOLCONSTANT));
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofLiteralTokenType(GLSLLexer.IDENTIFIER));
    assertThrows(IllegalArgumentException.class, () -> NumericType.ofLiteralTokenType(0));
  }

  @Test
  void testImplicitCasts() {
    assertEquals(
        EnumSet.of(NumericType.INT32, NumericType.UINT32, NumericType.INT64, NumericType.UINT64,
            NumericType.FLOAT32, NumericType.FLOAT64),
        NumericType.INT32.getImplicitCasts());
    assertEquals(EnumSet.of(NumericType.F64MAT2X3), NumericType.F64MAT2X3.getImplicitCasts());
    assertEquals(EnumSet.of(NumericType.BVEC2), NumericType.BVEC2.getImplicitCasts());
    assertTrue(NumericType.I32VEC2.isImplicitlyCastableTo(NumericType.F32VEC2));
    assertTrue(NumericType.I32VEC2.isImplicitlyCastableTo(NumericType.I32VEC2));
    assertFalse(NumericType.I32VEC2.isImplicitlyCastableTo(NumericType.F32VEC3));
    assertFalse(NumericType.UINT32.isImplicitlyCastableTo(NumericType.INT32));
  }

  @Test
  void testNumberType() {
    assertTrue(NumberType.BOOLEAN.isBoolean());
    assertFalse(NumberType.SIGNED_INTEGER.isBoolean());
    assertTrue(NumberType.SIGNED_INTEGER.isInteger());
    assertTrue(NumberType.UNSIGNED_INTEGER.isInteger());
    assertFalse(NumberType.FLOATING_POINT.isInteger());
    assertFalse(NumberType.BOOLEAN.isInteger());
    assertTrue(NumberType.FLOATING_POINT.isFloatingPoint());
    assertFalse(NumberType.UNSIGNED_INTEGER.isFloatingPoint());
    assertTrue(NumberType.SIGNED_INTEGER.isSigned());
    assertTrue(NumberType.FLOATING_POINT.isSigned());
    assertFalse(NumberType.UNSIGNED_INTEGER.isSigned());
    assertFalse(NumberType.BOOLEAN.isSigned());
    assertEquals(1, NumberType.BOOLEAN.getMaxBitDepth());
    assertEquals(64, NumberType.FLOATING_POINT.getMaxBitDepth());
    assertArrayEquals(new int[] { 4 }, NumberType.SIGNED_INTEGER.getMaxDimensions());
    assertArrayEquals(new int[] { 4, 4 }, NumberType.FLOATING_POINT.getMaxDimensions());
    assertEquals(EnumSet.of(NumericType.BOOL, NumericType.BVEC2, NumericType.BVEC3, NumericType.BVEC4),
        NumberType.BOOLEAN.getRegisteredTypes());
    assertEquals(16, NumberType.SIGNED_INTEGER.getRegisteredTypes().size());
    assertEquals(39, NumberType.FLOATING_POINT.getRegisteredTypes().size());
  }

  @Test
  void testFixedType() {
    assertEquals("void", FixedType.VOID.getTypeName());
    assertEquals("sampler2D", FixedType.SAMPLER2D.getTypeName());
    assertEquals("usampler2DMSArray", FixedType.USAMPLER2DMSARRAY.getTypeName());
    assertEquals("atomic_uint", FixedType.ATOMIC_UINT.getTypeName());
    assertEquals("accelerationStructureEXT", FixedType.ACCELERATION_STRUCTURE.getTypeName());
    assertFalse(FixedType.VOID.isOpaque());
    assertTrue(FixedType.IMAGE2D.isOpaque());
    for (var type : FixedType.values()) {
      assertNotNull(type.getTypeName(), type.toString());
      assertSame(type, FixedType.fromToken(new CommonToken(type.getTokenType())));
      assertEquals(type.tokenType, type.getTokenType());
      assertFalse(type.isError());
    }
  }
}
