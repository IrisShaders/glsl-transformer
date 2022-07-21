package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class IdentifierIndexTest extends TestWithASTTransformer {
  @Test
  void testIdentifierChange() {
    transformer.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("a"));
      assertFalse(root.identifierIndex.has("b"));
      root.identifierIndex.getOne("a").setName("b");
      assertFalse(root.identifierIndex.has("a"));
      assertTrue(root.identifierIndex.has("b"));
    });
    transformer.transform("int a = 1;");
  }

  @Test
  void testRenameAll() {
    Root.indexSeparateTrees(register -> {
      var a = new Identifier("a");
      var b = new Identifier("b");
      register.apply(a);
      register.apply(b);
      var index = a.getRoot().identifierIndex;
      assertTrue(index.has("a"));
      assertTrue(index.has("b"));
      assertFalse(index.has("c"));
      index.renameAll("a", "c");
      assertDoesNotThrow(() -> index.renameAll("foo", "c"));
      assertFalse(index.has("a"));
      assertTrue(index.has("b"));
      assertTrue(index.has("c"));
    });
  }

  @Test
  void testReplaceAll() {
    transformer.setTransformation((tree, root) -> {
      root.identifierIndex.replaceAll("foo",
          id -> id.getParent().replaceByAndDelete(
              transformer.parseExpression(tree, "bam + spam")));
    });
    assertEquals(
        "int x = bam + spam + bar; ",
        transformer.transform(PrintType.COMPACT, "int x = foo + bar;"));
  }

  @Test
  void testReplaceAllMultiple() {
    transformer.setTransformation((tree, root) -> {
      root.identifierIndex.replaceAll(root.identifierIndex.prefixQueryFlat("f"),
          id -> id.getParent().replaceByAndDelete(
              transformer.parseExpression(tree, "bam + spam")));
    });
    assertEquals(
        "int x = bam + spam + bar + bam + spam; ",
        transformer.transform(PrintType.COMPACT, "int x = foo + bar + fan;"));
  }
}
