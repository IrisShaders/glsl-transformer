package io.github.douira.glsl_transformer.ast.print;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class ASTPrinterTest extends TestWithASTTransformer {
  @Test
  void testOperatorPrecedencePrinting1() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          t, "b", "c + d");
    });
    assertTransform(
        "int x = a * (c + d); ",
        "int x = a * b;");
    assertTransform(
        "int x = a + c + d; ",
        "int x = a + b;");
    assertTransform(
        "int x = a | c + d; ",
        "int x = a | b;");
    assertTransform(
        "int x = (c + d)++; ",
        "int x = b++;");
    assertTransform(
        "int x = ++(c + d)++; ",
        "int x = ++b++;");
    assertTransform(
        "int x = ++(c + d); ",
        "int x = ++b;");
  }

  @Test
  void testOperatorPrecedencePrinting2() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          t, "b", "++c");
    });
    assertTransform(
        "int x = ++++c; ",
        "int x = ++b;");
    assertTransform(
        "int x = (++c)++; ",
        "int x = b++;");
    assertTransform(
        "int x = (++c)++; ",
        "int x = b++;");
    assertTransform(
        "int x = a + ++c; ",
        "int x = a + b;");
    assertTransform(
        "int x = a + (++c)++; ",
        "int x = a + b++;");
  }

  @Test
  void testOperatorPrecedencePrinting3() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          t, "b", "c, d");
    });
    assertTransform(
        "int x = ++(c, d); ",
        "int x = ++b;");
    assertTransform(
        "int x = ++(c, d); ",
        "int x = ++(b);");
    assertTransform(
        "int x = (a, c, d); ",
        "int x = (a, b);");
    assertTransform(
        "int x = a, c, d; ",
        "int x = a, b;");
  }

  @Test
  void testOperatorPrecedencePrinting4() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          t, "b", "(c + d)");
    });
    assertTransform(
        "int x = a * (c + d); ",
        "int x = a * b;");
    assertTransform(
        "int x = a * (c + d); ",
        "int x = a * (b);");
  }
}
