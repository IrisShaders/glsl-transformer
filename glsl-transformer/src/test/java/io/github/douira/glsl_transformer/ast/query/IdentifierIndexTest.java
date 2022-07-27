package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class IdentifierIndexTest extends TestWithASTTransformer {
  @Test
  void testIdentifierChange() {
    t.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("a"));
      assertFalse(root.identifierIndex.has("b"));
      root.identifierIndex.getOne("a").setName("b");
      assertFalse(root.identifierIndex.has("a"));
      assertTrue(root.identifierIndex.has("b"));
    });
    t.transform("int a = 1;");
  }
}
