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
}
