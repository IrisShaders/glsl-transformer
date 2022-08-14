package io.github.douira.glsl_transformer.ast.print;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class ASTPrinterTest extends TestWithSingleASTTransformer {
  @Test
  void testOperatorPrecedencePrinting1() {
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          p, "b", "c + d");
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
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          p, "b", "++c");
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
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          p, "b", "c, d");
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
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(
          p, "b", "(c + d)");
    });
    assertTransform(
        "int x = a * (c + d); ",
        "int x = a * b;");
    assertTransform(
        "int x = a * (c + d); ",
        "int x = a * (b);");
  }
}
