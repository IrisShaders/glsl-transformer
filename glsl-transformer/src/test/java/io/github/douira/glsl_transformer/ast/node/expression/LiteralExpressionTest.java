package io.github.douira.glsl_transformer.ast.node.expression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression.IntegerFormat;
import io.github.douira.glsl_transformer.ast.typing.*;

public class LiteralExpressionTest {
  @Test
  void testConstructorValidation() {
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.BOOL, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.FLOAT16, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.FLOAT16, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.I16VEC2, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.F32MAT2X2, 0.0));
    // Disabled because of very large unsigned longs being put in signed long fields
    // assertThrows(IllegalArgumentException.class,
    // () -> new LiteralExpression(Type.UINT32, -1));
  }

  @Test
  void testChangeBoolean() {
    var e = new LiteralExpression(true);
    assertTrue(e.getBoolean());
    e.setBoolean(false);
    assertThrows(IllegalStateException.class, () -> e.changeInteger(1));
    assertFalse(e.getBoolean());
    e.setInteger(1);
    assertThrows(IllegalStateException.class, () -> e.changeBoolean(true));
    assertThrows(IllegalStateException.class, () -> e.changeFloating(0.1));
  }

  @Test
  void testChangeFloating() {
    var e = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(10.0, e.getFloating());
    e.setFloating(1.0f);
    assertEquals(1.0, e.getFloating());
  }

  @Test
  void testChangeInteger() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getInteger());
    e.setInteger(1);
    assertEquals(1, e.getInteger());
  }

  @Test
  void testGetBoolean() {
    var e = new LiteralExpression(true);
    assertTrue(e.getBoolean());
    var f = new LiteralExpression(NumericType.INT32, 10);
    assertFalse(f.getBoolean());
  }

  @Test
  void testGetFloating() {
    var e = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(10.0, e.getFloating());
    var f = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(0.0, f.getFloating());
  }

  @Test
  void testGetInteger() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getInteger());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(0, f.getInteger());
  }

  @Test
  void testGetIntegerFormat() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(IntegerFormat.DECIMAL, e.getIntegerFormat());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertNull(f.getIntegerFormat());
  }

  @Test
  void testGetIntegerRadix() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getIntegerRadix());
    var a = new LiteralExpression(NumericType.INT32, 10, IntegerFormat.HEXADECIMAL);
    assertEquals(16, a.getIntegerRadix());
    var b = new LiteralExpression(NumericType.INT32, 10, IntegerFormat.OCTAL);
    assertEquals(8, b.getIntegerRadix());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertThrows(NullPointerException.class, () -> f.getIntegerRadix());
  }

  @Test
  void testGetNumber() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getNumber());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(10.0f, f.getNumber());
    var g = new LiteralExpression(NumericType.FLOAT64, 10.0);
    assertEquals(10.0d, g.getNumber());
  }

  @Test
  void testGetNumberType() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(NumberType.SIGNED_INTEGER, e.getNumberType());
    var a = new LiteralExpression(NumericType.UINT16, 10);
    assertEquals(NumberType.UNSIGNED_INTEGER, a.getNumberType());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(NumberType.FLOATING_POINT, f.getNumberType());
    var g = new LiteralExpression(NumericType.FLOAT64, 10.0);
    assertEquals(NumberType.FLOATING_POINT, g.getNumberType());
  }

  @Test
  void testGetType() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(NumericType.INT32, e.getNumericType());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertEquals(NumericType.FLOAT32, f.getNumericType());
  }

  @Test
  void testIsBoolean() {
    var e = new LiteralExpression(true);
    assertTrue(e.isBoolean());
    var f = new LiteralExpression(NumericType.INT32, 10);
    assertFalse(f.isBoolean());
    var g = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertFalse(g.isBoolean());
  }

  @Test
  void testIsFloatingPoint() {
    var e = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertTrue(e.isFloatingPoint());
    var f = new LiteralExpression(NumericType.INT32, 10);
    assertFalse(f.isFloatingPoint());
    var g = new LiteralExpression(false);
    assertFalse(g.isFloatingPoint());
  }

  @Test
  void testIsInteger() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertTrue(e.isInteger());
    var f = new LiteralExpression(NumericType.FLOAT32, 10.0);
    assertFalse(f.isInteger());
    var g = new LiteralExpression(false);
    assertFalse(g.isInteger());
  }

  @Test
  void testIsNonZero() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertTrue(e.isNonZero());
    var a = new LiteralExpression(NumericType.INT32, -10);
    assertTrue(a.isNonZero());
    var f = new LiteralExpression(NumericType.FLOAT32, 0.0);
    assertFalse(f.isNonZero());
    var g = new LiteralExpression(false);
    assertTrue(g.isNonZero());
  }

  @Test
  void testIsPositive() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertTrue(e.isPositive());
    var a = new LiteralExpression(NumericType.INT32, -10);
    assertFalse(a.isPositive());
    var f = new LiteralExpression(NumericType.FLOAT32, 0.0);
    assertFalse(f.isPositive());
    var g = new LiteralExpression(false);
    assertFalse(g.isPositive());
    var h = new LiteralExpression(true);
    assertTrue(h.isPositive());
  }

  @Test
  void testSetBoolean() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getInteger());
    assertFalse(e.getBoolean());
    e.setBoolean(true);
    assertEquals(0, e.getInteger());
    assertTrue(e.getBoolean());
  }

  @Test
  void testSetFloating() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    assertEquals(10, e.getInteger());
    assertEquals(0.0, e.getFloating());
    assertFalse(e.getBoolean());
    e.setFloating(NumericType.FLOAT32, 10.0);
    assertEquals(0, e.getInteger());
    assertEquals(10.0, e.getFloating());
    assertFalse(e.getBoolean());
  }

  @Test
  void testSetFloating2() {
    var e = new LiteralExpression(NumericType.INT32, 10);
    e.setFloating(10.0f);
    assertEquals(NumericType.FLOAT32, e.getNumericType());
  }

  @Test
  void testSetInteger() {
    var e = new LiteralExpression(NumericType.FLOAT16, 10.0);
    assertEquals(10.0, e.getFloating());
    assertEquals(0, e.getInteger());
    assertFalse(e.getBoolean());
    e.setInteger(NumericType.INT32, 10);
    assertEquals(0.0, e.getFloating());
    assertEquals(10, e.getInteger());
    assertFalse(e.getBoolean());
  }

  @Test
  void testSetInteger2() {
    var e = new LiteralExpression(NumericType.FLOAT16, 10.0);
    e.setInteger(10);
    assertEquals(NumericType.INT32, e.getNumericType());
  }

  @Test
  void testSetInteger3() {
    var e = new LiteralExpression(NumericType.FLOAT16, 10.0);
    e.setInteger(NumericType.INT16, 10, IntegerFormat.HEXADECIMAL);
    assertEquals(NumericType.INT16, e.getNumericType());
    assertEquals(IntegerFormat.HEXADECIMAL, e.getIntegerFormat());
  }

  @Test
  void testSetIntegerFormat() {
    var e = new LiteralExpression(NumericType.FLOAT16, 10.0);
    assertThrows(IllegalStateException.class,
        () -> e.setIntegerFormat(IntegerFormat.HEXADECIMAL));
    var f = new LiteralExpression(NumericType.INT16, 10);
    assertEquals(IntegerFormat.DECIMAL, f.getIntegerFormat());
    f.setIntegerFormat(IntegerFormat.HEXADECIMAL);
    assertEquals(IntegerFormat.HEXADECIMAL, f.getIntegerFormat());
  }
}
