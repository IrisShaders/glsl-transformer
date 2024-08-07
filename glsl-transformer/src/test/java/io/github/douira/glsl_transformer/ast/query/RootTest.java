package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.parser.ParseShape;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class RootTest extends TestWithSingleASTTransformer {
  @Test
  void testRename() {
    p.supplyRoot().indexSeparateTrees(register -> {
      var a = new Identifier("a");
      var b = new Identifier("b");
      register.apply(a);
      register.apply(b);
      var root = a.getRoot();
      var index = root.identifierIndex;
      assertTrue(index.has("a"));
      assertTrue(index.has("b"));
      assertFalse(index.has("c"));
      root.rename("a", "c");
      assertDoesNotThrow(() -> root.rename("foo", "c"));
      assertFalse(index.has("a"));
      assertTrue(index.has("b"));
      assertTrue(index.has("c"));
    });
  }

  @Test
  void testReplace() {
    p.setTransformation((tree, root) -> {
      root.process("foo",
          id -> id.getParent().replaceByAndDelete(
              p.parseExpression(root, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + fooo; ",
        "int x = foo + bar + fooo;");
  }

  @Test
  void testReplaceMultiple() {
    p.setRootSupplier(RootSupplier.PREFIX_UNORDERED);
    p.setTransformation((tree, root) -> {
      root.process(root.getPrefixIdentifierIndex().prefixQueryFlat("f"),
          id -> id.getParent().replaceByAndDelete(
              p.parseExpression(root, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + bam + spam; ",
        "int x = foo + bar + fan;");
  }

  @Test
  void testNullReplaceStream() {
    p.setTransformation((tree, root) -> {
      root.process(Stream.of((Identifier) null), id -> {
      });
    });
    assertDoesNotThrow(() -> p.transform(""));
  }

  @Test
  void testReplaceReferenceExpressionsPrefix() {
    p.setRootSupplier(RootSupplier.PREFIX_UNORDERED);
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(p,
          root.getPrefixIdentifierIndex().prefixQueryFlat("f"),
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + bam + spam; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testReplaceReferenceExpressionsExact() {
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(p,
          "foo",
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + fan; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testHintedMatcherProcessing() {
    var matcher = new HintedMatcher<>("foo[1]", ParseShape.EXPRESSION, "foo");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testHintedMatcherProcessingHintSpecificity() {
    var matcher = new HintedMatcher<>("foo[1]", ParseShape.EXPRESSION, "bar");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + foo[1] + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessing() {
    var matcher = new AutoHintedMatcher<>("foo[1]", ParseShape.EXPRESSION);
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessingWildcard() {
    var matcher = new AutoHintedMatcher<>("a[___f + 5]", ParseShape.EXPRESSION, "___");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertEquals("a", matcher.getHint());
    assertTransform(
        "int foo = bam + bar + 4 + a[bar + 4]; ",
        "int foo = bam + a[bar + 5] + a[bar + 4];");
  }

  @Test
  void testNullIndexes() {
    assertCall(3, (callback) -> {
      RootSupplier.EMPTY.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertThrows(NullPointerException.class, () -> index.has("a"));
        assertThrows(NullPointerException.class, () -> root.rename("foo", "c"));
        callback.run();
      });
      RootSupplier.ONLY_IDENTIFIER_INDEX.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertDoesNotThrow(() -> index.has("a"));
        assertThrows(NullPointerException.class, () -> root.nodeIndex.has(Identifier.class));
        callback.run();
      });
      RootSupplier.ONLY_NODE_INDEX.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertThrows(NullPointerException.class, () -> index.has("a"));
        assertDoesNotThrow(() -> root.nodeIndex.has(Identifier.class));
        callback.run();
      });
    });
  }
}
