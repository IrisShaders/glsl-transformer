package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class IdentifierIndexTest extends TestWithSingleASTTransformer {
  @Test
  void testIdentifierChange() {
    p.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("a"));
      assertFalse(root.identifierIndex.has("b"));
      root.identifierIndex.getOne("a").setName("b");
      assertFalse(root.identifierIndex.has("a"));
      assertTrue(root.identifierIndex.has("b"));
    });
    p.transform("int a = 1;");
  }

  @Test
  void testGetUnique() {
    p.setTransformation((tree, root) -> {
      assertNotNull(root.identifierIndex.getUnique("a"));
      assertThrows(IllegalStateException.class, () -> root.identifierIndex.getUnique("b"));
      assertThrows(IllegalStateException.class, () -> root.identifierIndex.getUnique("f"));
    });
    p.transform("int a = 1, b, c, b;");
  }
}
