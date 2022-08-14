package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression;
import io.github.douira.glsl_transformer.test_util.TestWithGroupedASTTransformer;

public class GroupedASTTransformerTest extends TestWithGroupedASTTransformer {
  @Test
  void testGroupedTransformation() {
    p.setTransformation((a, b, c, rootA, rootB, rootC) -> {
      var index = rootB.nodeIndex.getOne(LiteralExpression.class).getInteger();
      a.children.add((int) index, p.parseExternalDeclaration(a, "int z;"));
      assertTrue(rootA.identifierIndex.has("z"));

      b.children.add(c.children.get(0));
      c.children.remove(0);
      assertTrue(rootB.identifierIndex.has("bar"));
      assertFalse(rootC.identifierIndex.has("bar"));

      var zou = c.children.get(0);
      zou.detach();
      b.children.add(zou);
      assertTrue(rootB.identifierIndex.has("zou"));
      assertFalse(rootC.identifierIndex.has("zou"));
    });
    assertTransform(
        "int a; int z; int b; int c; ",
        "int f = 1; int foo = bar; int foo = zou; ",
        "",
        "int a; int b; int c; ",
        "int f = 1;",
        "int foo = bar; int foo = zou;");
  }

  @Test
  void testGroupedTransformWithNulls() {
    p.setTransformation((a, b, c, rootA, rootB, rootC) -> {
    });
    assertTransform("", null, "", "", null, "");
  }
}
