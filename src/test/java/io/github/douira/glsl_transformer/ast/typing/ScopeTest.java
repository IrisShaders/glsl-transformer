package io.github.douira.glsl_transformer.ast.typing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.typing.FunctionSignature.*;

public class ScopeTest {
  private static VariableSymbol variable(String name, Type type) {
    return new VariableSymbol(name, type, true, null, null);
  }

  private static FunctionSignature signature(String name, Type returnType, Type... parameters) {
    return new FunctionSignature(name, returnType,
        Arrays.stream(parameters).map(type -> new Parameter(type, Direction.IN)).toList(), false, null);
  }

  @Test
  void testLookup() {
    var outer = new Scope(null);
    var inner = new Scope(outer);
    assertNull(outer.getParent());
    assertSame(outer, inner.getParent());

    var a = variable("a", NumericType.FLOAT32);
    var b = variable("b", NumericType.INT32);
    var shadow = variable("a", NumericType.BOOL);
    assertTrue(outer.declare(a));
    assertTrue(outer.declare(b));
    assertTrue(inner.declare(shadow));

    assertSame(a, outer.lookup("a"));
    assertSame(shadow, inner.lookup("a"));
    assertSame(b, inner.lookup("b"));
    assertNull(inner.lookupLocal("b"));
    assertSame(shadow, inner.lookupLocal("a"));
    assertNull(inner.lookup("missing"));
    assertNull(outer.lookupLocal("missing"));

    assertEquals(List.of("a", "b"), List.copyOf(outer.getSymbols().keySet()));
    assertThrows(UnsupportedOperationException.class, () -> outer.getSymbols().clear());
  }

  @Test
  void testSingleNamespace() {
    var scope = new Scope(null);
    var variable = variable("x", NumericType.FLOAT32);
    assertTrue(scope.declare(variable));
    assertFalse(scope.declare(variable("x", NumericType.FLOAT32)));
    assertFalse(scope.declare(new TypeSymbol("x", NumericType.FLOAT32, null)));
    assertFalse(scope.declare(new FunctionSymbol("x", signature("x", FixedType.VOID))));
    assertSame(variable, scope.lookup("x"));

    var type = new TypeSymbol("T", NumericType.FLOAT32, null);
    assertTrue(scope.declare(type));
    assertFalse(scope.declare(variable("T", NumericType.FLOAT32)));
    assertFalse(scope.declare(new TypeSymbol("T", NumericType.FLOAT32, null)));
    assertSame(type, scope.lookup("T"));

    assertTrue(scope.declare(new FunctionSymbol("f", signature("f", FixedType.VOID))));
    assertFalse(scope.declare(variable("f", NumericType.FLOAT32)));
    assertFalse(scope.declare(new TypeSymbol("f", NumericType.FLOAT32, null)));
  }

  @Test
  void testFunctionOverloads() {
    var scope = new Scope(null);
    var first = signature("f", FixedType.VOID, NumericType.INT32);
    var second = signature("f", FixedType.VOID, NumericType.FLOAT32);
    var symbol = new FunctionSymbol("f", first);
    assertTrue(scope.declare(symbol));
    assertTrue(scope.declare(new FunctionSymbol("f", second)));
    assertSame(symbol, scope.lookup("f"));
    assertEquals(List.of(first, second), symbol.overloads());
    assertThrows(UnsupportedOperationException.class, () -> symbol.overloads().clear());

    // redeclaring the same function is allowed and does not add an overload
    assertTrue(scope.declare(new FunctionSymbol("f", signature("f", FixedType.VOID, NumericType.INT32))));
    assertEquals(2, symbol.overloads().size());

    // but not with a different return type
    assertFalse(scope.declare(new FunctionSymbol("f", signature("f", NumericType.INT32, NumericType.INT32))));
    assertEquals(2, symbol.overloads().size());

    assertEquals("f", symbol.name());
    assertNull(symbol.declaration());
    assertEquals("f[void f(int), void f(float)]", symbol.toString());
  }

  @Test
  void testUnsizedArrayRedeclaration() {
    var scope = new Scope(null);
    var unsized = new ArrayType(NumericType.FLOAT32, ArraySize.UNSIZED);
    var sized = new ArrayType(NumericType.FLOAT32, ArraySize.of(3));
    assertTrue(scope.declare(variable("a", unsized)));
    var redeclared = variable("a", sized);
    assertTrue(scope.declare(redeclared));
    assertSame(redeclared, scope.lookup("a"));

    // a sized array cannot be redeclared
    assertFalse(scope.declare(variable("a", sized)));
    assertFalse(scope.declare(variable("a", unsized)));

    // an unsized array can only be redeclared as an array of the same elements
    assertTrue(scope.declare(variable("b", unsized)));
    assertFalse(scope.declare(variable("b", new ArrayType(NumericType.INT32, ArraySize.of(3)))));
    assertFalse(scope.declare(variable("b", NumericType.FLOAT32)));
    assertFalse(scope.declare(new TypeSymbol("b", sized, null)));
    assertFalse(scope.declare(new FunctionSymbol("b", signature("b", FixedType.VOID))));

    // variables that are not arrays cannot be redeclared
    assertTrue(scope.declare(variable("c", NumericType.FLOAT32)));
    assertFalse(scope.declare(variable("c", sized)));
  }

  @Test
  void testSymbols() {
    var node = new Identifier("node");
    var constant = new VariableSymbol("c", NumericType.INT32, false, ConstantValue.of(NumericType.INT32, 3), node);
    assertTrue(constant.isConstant());
    assertFalse(variable("v", NumericType.INT32).isConstant());
    assertSame(node, constant.declaration());
    assertEquals("c", constant.name());
    assertFalse(constant.assignable());

    var type = new TypeSymbol("T", NumericType.INT32, node);
    assertSame(node, type.declaration());
    assertEquals("T", type.name());
    assertSame(NumericType.INT32, type.type());

    var signature = new FunctionSignature("f", NumericType.FLOAT32, List.of(
        new Parameter(NumericType.INT32, Direction.IN),
        new Parameter(NumericType.F32VEC2, Direction.OUT),
        new Parameter(NumericType.BOOL, Direction.INOUT)), false, node);
    assertEquals("float f(int, out vec2, inout bool)", signature.toString());
    assertSame(node, new FunctionSymbol("f", signature).declaration());
    assertTrue(signature.hasSameParameterTypes(
        signature("g", FixedType.VOID, NumericType.INT32, NumericType.F32VEC2, NumericType.BOOL)));
    assertFalse(signature.hasSameParameterTypes(signature("f", NumericType.FLOAT32, NumericType.INT32)));
    assertThrows(UnsupportedOperationException.class, () -> signature.parameters().clear());

    var variadic = new FunctionSignature("p", FixedType.VOID,
        List.of(new Parameter(StringType.INSTANCE, Direction.IN)), true, null);
    assertEquals("void p(string, ...)", variadic.toString());
  }

  @Test
  void testConstantValue() {
    assertEquals(new ConstantValue(NumericType.INT32, -1), ConstantValue.of(NumericType.INT32, 0xFFFFFFFFL));
    assertEquals(new ConstantValue(NumericType.UINT32, 0xFFFFFFFFL), ConstantValue.of(NumericType.UINT32, -1));
    assertEquals(new ConstantValue(NumericType.INT8, -128), ConstantValue.of(NumericType.INT8, 128));
    assertEquals(new ConstantValue(NumericType.UINT8, 1), ConstantValue.of(NumericType.UINT8, 257));
    assertEquals(new ConstantValue(NumericType.INT16, -1), ConstantValue.of(NumericType.INT16, 65535));
    assertEquals(new ConstantValue(NumericType.INT64, -5), ConstantValue.of(NumericType.INT64, -5));
    assertEquals(new ConstantValue(NumericType.UINT64, -5), ConstantValue.of(NumericType.UINT64, -5));
    assertEquals(new ConstantValue(NumericType.BOOL, 1), ConstantValue.of(NumericType.BOOL, 5));
    assertEquals(new ConstantValue(NumericType.BOOL, 0), ConstantValue.of(NumericType.BOOL, 0));

    assertSame(ConstantValue.UNKNOWN, ConstantValue.of(NumericType.FLOAT32, 1));
    assertSame(ConstantValue.UNKNOWN, ConstantValue.of(NumericType.I32VEC2, 1));
    assertSame(ConstantValue.UNKNOWN, ConstantValue.of(FixedType.VOID, 1));
    assertSame(ConstantValue.UNKNOWN, ConstantValue.of(ErrorType.INSTANCE, 1));

    assertFalse(ConstantValue.UNKNOWN.isKnown());
    assertTrue(ConstantValue.of(NumericType.INT32, 1).isKnown());
    assertTrue(ConstantValue.of(NumericType.UINT16, 1).isUnsigned());
    assertFalse(ConstantValue.of(NumericType.INT16, 1).isUnsigned());
    assertFalse(ConstantValue.of(NumericType.BOOL, 1).isUnsigned());
  }
}
