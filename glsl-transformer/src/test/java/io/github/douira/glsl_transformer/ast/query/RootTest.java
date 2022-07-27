package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class RootTest extends TestWithASTTransformer {
  @Test
  void testRename() {
    Root.indexSeparateTrees(register -> {
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
    t.setTransformation((tree, root) -> {
      root.process("foo",
          id -> id.getParent().replaceByAndDelete(
              t.parseExpression(tree, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + fooo; ",
        "int x = foo + bar + fooo;");
  }

  @Test
  void testReplaceMultiple() {
    t.setTransformation((tree, root) -> {
      root.process(root.identifierIndex.prefixQueryFlat("f"),
          id -> id.getParent().replaceByAndDelete(
              t.parseExpression(tree, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + bam + spam; ",
        "int x = foo + bar + fan;");
  }

  @Test
  void testNullReplaceStream() {
    t.setTransformation((tree, root) -> {
      root.process(Stream.of((Identifier) null), id -> {
      });
    });
    assertDoesNotThrow(() -> t.transform(""));
  }

  @Test
  void testReplaceReferenceExpressionsPrefix() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(t,
          root.identifierIndex.prefixQueryFlat("f"),
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + bam + spam; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testReplaceReferenceExpressionsExact() {
    t.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(t,
          "foo",
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + fan; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testHintedMatcherProcessing() {
    var matcher = new HintedMatcher<>("foo[1]", Matcher.expressionPattern, "foo");
    t.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(t, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testHintedMatcherProcessingHintSpecificity() {
    var matcher = new HintedMatcher<>("foo[1]", Matcher.expressionPattern, "bar");
    t.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(t, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + foo[1] + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessing() {
    var matcher = new AutoHintedMatcher<>("foo[1]", Matcher.expressionPattern);
    t.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(t, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessingWildcard() {
    var matcher = new AutoHintedMatcher<>("a[___f + 5]", Matcher.expressionPattern, "___");
    t.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(t, matcher, "bar + 4");
    });
    assertEquals("a", matcher.getHint());
    assertTransform(
        "int foo = bam + bar + 4 + a[bar + 4]; ",
        "int foo = bam + a[bar + 5] + a[bar + 4];");
  }
}
