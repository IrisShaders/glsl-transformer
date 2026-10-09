package io.github.douira.glsl_transformer.ast.node.expression;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.traversal.ASTVoidVisitor;
import io.github.douira.glsl_transformer.parser.ParseShape;

import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression.IntegerFormat;
import io.github.douira.glsl_transformer.ast.typing.*;

public class LiteralExpressionTest {
  @Test
  void testConstructorValidation() {
    assertThrows(IllegalArgumentException.class, () -> new LiteralExpression(null));
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

    // there is no syntax for literals of the 8 bit integer types
    assertThrows(IllegalArgumentException.class, () -> new LiteralExpression(NumericType.INT8, 0));
    assertThrows(IllegalArgumentException.class, () -> new LiteralExpression(NumericType.UINT8, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new LiteralExpression(NumericType.INT8, 0, IntegerFormat.HEXADECIMAL));
    var literal = new LiteralExpression(NumericType.INT32, 1);
    assertThrows(IllegalArgumentException.class, () -> literal.setInteger(NumericType.UINT8, 1));
    assertEquals(NumericType.INT32, literal.getNumericType());
    assertThrows(NullPointerException.class, () -> literal.setInteger(null, 1));

    // Disabled because of very large unsigned longs being put in signed long fields
    // assertThrows(IllegalArgumentException.class,
    // () -> new LiteralExpression(Type.UINT32, -1));
  }

  @Test
  void testChangeString() {
    var e = new LiteralExpression("test");
    assertEquals("test", e.getString());
    e.setString("test2");
    assertEquals("test2", e.getString());
    assertThrows(IllegalStateException.class, () -> e.changeInteger(1));
    assertThrows(IllegalStateException.class, () -> e.changeBoolean(true));
    assertThrows(IllegalStateException.class, () -> e.changeFloating(0.1));
    assertThrows(IllegalArgumentException.class, () -> e.changeString(null));
  }

  @Test
  void testStringProperties() {
    var e = new LiteralExpression("test");
    assertEquals("test", e.getString());
    assertEquals(StringType.INSTANCE, e.getLiteralType());
    assertFalse(e.isBoolean());
    assertFalse(e.isFloatingPoint());
    assertFalse(e.isInteger());
    assertFalse(e.isNonZero());
    assertFalse(e.isPositive());
    assertNull(e.getIntegerFormat());

    assertThrows(IllegalArgumentException.class, () -> e.setString(null));
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
    // the boolean value false is zero
    assertFalse(new LiteralExpression(false).isNonZero());
    assertTrue(new LiteralExpression(true).isNonZero());
    var changed = new LiteralExpression(true);
    changed.changeBoolean(false);
    assertFalse(changed.isNonZero());
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

  private static void assertOnlyString(LiteralExpression e, String value) {
    assertTrue(e.isString());
    assertSame(StringType.INSTANCE, e.getLiteralType());
    assertEquals(value, e.getString());
    assertFalse(e.getBoolean());
    assertEquals(0, e.getInteger());
    assertNull(e.getIntegerFormat());
    assertEquals(0.0, e.getFloating());
    assertFalse(e.isNumeric());
    assertThrows(IllegalStateException.class, e::getNumericType);
    assertThrows(IllegalStateException.class, e::getNumberType);
  }

  private static void assertOnlyBoolean(LiteralExpression e, boolean value) {
    assertTrue(e.isBoolean());
    assertSame(NumericType.BOOL, e.getLiteralType());
    assertEquals(value, e.getBoolean());
    assertNull(e.getString());
    assertEquals(0, e.getInteger());
    assertNull(e.getIntegerFormat());
    assertEquals(0.0, e.getFloating());
  }

  private static void assertOnlyInteger(LiteralExpression e, NumericType type, long value, IntegerFormat format) {
    assertTrue(e.isInteger());
    assertSame(type, e.getLiteralType());
    assertEquals(value, e.getInteger());
    assertSame(format, e.getIntegerFormat());
    assertNull(e.getString());
    assertFalse(e.getBoolean());
    assertEquals(0.0, e.getFloating());
  }

  private static void assertOnlyFloating(LiteralExpression e, NumericType type, double value) {
    assertTrue(e.isFloatingPoint());
    assertSame(type, e.getLiteralType());
    assertEquals(value, e.getFloating());
    assertNull(e.getString());
    assertFalse(e.getBoolean());
    assertEquals(0, e.getInteger());
    assertNull(e.getIntegerFormat());
  }

  @Test
  void testSettersResetOtherKinds() {
    // every transition between the kinds of literals leaves no stale state behind
    var e = new LiteralExpression("text");
    assertOnlyString(e, "text");
    e.setBoolean(true);
    assertOnlyBoolean(e, true);
    e.setInteger(NumericType.UINT32, 7, IntegerFormat.OCTAL);
    assertOnlyInteger(e, NumericType.UINT32, 7, IntegerFormat.OCTAL);
    e.setFloating(NumericType.FLOAT64, 2.5);
    assertOnlyFloating(e, NumericType.FLOAT64, 2.5);
    e.setString("again");
    assertOnlyString(e, "again");
    e.setInteger(NumericType.INT64, 9);
    assertOnlyInteger(e, NumericType.INT64, 9, IntegerFormat.DECIMAL);
    e.setBoolean(true);
    assertOnlyBoolean(e, true);
    e.setFloating(1.5f);
    assertOnlyFloating(e, NumericType.FLOAT32, 1.5);
    e.setInteger(3);
    assertOnlyInteger(e, NumericType.INT32, 3, IntegerFormat.DECIMAL);
    e.setInteger(NumericType.INT16, 4, IntegerFormat.HEXADECIMAL);
    assertOnlyInteger(e, NumericType.INT16, 4, IntegerFormat.HEXADECIMAL);
    e.setString("last");
    assertOnlyString(e, "last");
    e.setFloating(NumericType.FLOAT16, 0.5);
    assertOnlyFloating(e, NumericType.FLOAT16, 0.5);
    e.setBoolean(false);
    assertOnlyBoolean(e, false);
    e.setString("");
    assertOnlyString(e, "");
    assertThrows(IllegalArgumentException.class, () -> e.setString(null));

    // clones are independent
    var original = new LiteralExpression(NumericType.INT32, 5);
    var clone = original.clone();
    clone.setFloating(1.0f);
    assertOnlyInteger(original, NumericType.INT32, 5, IntegerFormat.DECIMAL);
    assertOnlyFloating(clone, NumericType.FLOAT32, 1.0);
  }

  @Test
  void testDefaultNumericValueOfNumberType() {
    var bool = (LiteralExpression) LiteralExpression.getDefaultNumericValue(NumberType.BOOLEAN);
    assertOnlyBoolean(bool, false);
    var signed = (LiteralExpression) LiteralExpression.getDefaultNumericValue(NumberType.SIGNED_INTEGER);
    assertOnlyInteger(signed, NumericType.INT32, 0, IntegerFormat.DECIMAL);
    var unsigned = (LiteralExpression) LiteralExpression.getDefaultNumericValue(NumberType.UNSIGNED_INTEGER);
    assertOnlyInteger(unsigned, NumericType.UINT32, 0, IntegerFormat.DECIMAL);
    var floating = (LiteralExpression) LiteralExpression.getDefaultNumericValue(NumberType.FLOATING_POINT);
    assertOnlyFloating(floating, NumericType.FLOAT32, 0.0);
  }

  @Test
  void testDefaultNumericValueHasExactType() {
    var root = RootSupplier.DEFAULT.get();
    for (var type : NumericType.values()) {
      var value = root.indexNodes(() -> LiteralExpression.getDefaultNumericValue(type));

      // scalars with a literal syntax are literals, everything else is constructed
      assertEquals(type.hasLiteral(), value instanceof LiteralExpression, type.toString());
      assertEquals(!type.hasLiteral(), value instanceof FunctionCallExpression, type.toString());

      // the value has exactly the requested type and is valid
      var analysis = new TypeAnalyzer().analyze(value);
      assertSame(type, analysis.typeOf(value), type.toString());
      assertSame(type, value.getType(), type.toString());
      assertTrue(analysis.diagnostics().isEmpty(), type + ": " + analysis.diagnostics());

      // and it still does after printing and parsing it again
      var printed = ASTPrinter.printSimple(value);
      var parsed = ParseShape.EXPRESSION._parseNodeSeparateInternal(printed);
      assertSame(type, parsed.getType(), printed);
      assertTrue(parsed.getTypeAnalysis().diagnostics().isEmpty(), printed);
    }
    assertEquals("vec3(0.0f)", ASTPrinter.printSimple(
        root.indexNodes(() -> LiteralExpression.getDefaultNumericValue(NumericType.F32VEC3))));
    assertEquals("i8vec2(0)", ASTPrinter.printSimple(
        root.indexNodes(() -> LiteralExpression.getDefaultNumericValue(NumericType.I8VEC2))));
    assertEquals("0ul", ASTPrinter.printSimple(
        root.indexNodes(() -> LiteralExpression.getDefaultNumericValue(NumericType.UINT64))));
  }

  @Test
  void testVisitStringLiteral() {
    // the default traversal used to throw for string literals
    var data = new ArrayList<Object>();
    new ASTVoidVisitor() {
      @Override
      public Void visitData(Object object) {
        data.add(object);
        return null;
      }
    }.visit(new LiteralExpression("text"));
    assertEquals(List.of(StringType.INSTANCE, "text"), data);

    data.clear();
    new ASTVoidVisitor() {
      @Override
      public Void visitData(Object object) {
        data.add(object);
        return null;
      }
    }.visit(new LiteralExpression(NumericType.INT32, 3, IntegerFormat.OCTAL));
    assertEquals(List.of(NumericType.INT32, 3, IntegerFormat.OCTAL), data);
  }
}
