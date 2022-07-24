package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.query.match.Matcher;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class MatcherTest extends TestWithASTTransformer {
  private void assertNoMatchED(Matcher<ExternalDeclaration> p, String input) {
    assertFalse(p.matches(transformer.parseSeparateExternalDeclaration(input)));
  }

  private void assertMatchED(Matcher<ExternalDeclaration> p, String input) {
    assertTrue(p.matches(transformer.parseSeparateExternalDeclaration(input)));
  }

  private void assertExtractED(Matcher<ExternalDeclaration> p, String input) {
    assertTrue(p.matchesExtract(transformer.parseSeparateExternalDeclaration(input)));
  }

  private void assertNoExtractTU(Matcher<TranslationUnit> p, String input) {
    assertFalse(p.matchesExtract(transformer.parseTranslationUnit(input)));
  }

  private void assertExtractTU(Matcher<TranslationUnit> p, String input) {
    assertTrue(p.matchesExtract(transformer.parseTranslationUnit(input)));
  }

  @Test
  void testMatches() {
    var p = new Matcher<>("uniform vec4 entityColor;",
        Matcher.externalDeclarationPattern);

    assertFalse(p.matches(null));
    assertMatchED(p, "uniform vec4 entityColor;");
    assertNoMatchED(p, "uniform vec3 entityColor;");
    assertNoMatchED(p, "in vec4 entityColor;");
    assertNoMatchED(p, "uniform precise vec4 entityColor;");
    assertNoMatchED(p, "uniform vec4 entityColo;");
    assertNoMatchED(p, "uniform vec4 entityColor_;");
  }

  @Test
  void testMatchesDataWildcard() {
    var p = new Matcher<>("uniform vec4 ___ = foo + ___;",
        Matcher.externalDeclarationPattern, "___");

    assertFalse(p.matches(null));
    assertMatchED(p, "uniform vec4 foo = foo + foo;");
    assertNoMatchED(p, "uniform vec3 foo = foo + foo;");
    assertNoMatchED(p, "in vec4 foo = foo + foo;");
    assertNoMatchED(p, "uniform precise vec4 foo = foo + foo;");
    assertNoMatchED(p, "uniform vec4 foo = bar + foo;");
    assertNoMatchED(p, "uniform vec4 foo = __ + foo;");
  }

  @Test
  void testExtractDataWildcards() {
    var p = new Matcher<>("uniform vec4 ___a = foo + ___b;",
        Matcher.externalDeclarationPattern, "___");

    assertFalse(p.matchesExtract(null));
    assertTrue(p.getDataMatches().isEmpty());
    assertTrue(p.getNodeMatches().isEmpty());

    assertExtractED(p, "uniform vec4 bar = foo + bam;");
    var dataMatches = p.getDataMatches();
    assertEquals("bar", dataMatches.get("a"));
    assertEquals("bam", dataMatches.get("b"));
    assertEquals(dataMatches.get("b"), p.getDataMatch("b"));
    assertEquals(dataMatches.get("b"), p.getStringDataMatch("b"));
    assertTrue(p.getNodeMatches().isEmpty());

    assertNoMatchED(p, "uniform vec4 bar = foo;");
    // It should not match Identifiers with LiteralExpressions
    assertNoMatchED(p, "uniform vec4 bar = foo + 5;");
  }

  @Test
  void testExtractNodeWildcards() {
    var p = new Matcher<>("uniform vec4 a = foo + b;", Matcher.externalDeclarationPattern) {
      {
        markAnyWildcard("aNode",
            pattern.getRoot().identifierIndex.getOne("a"));
        markAnyWildcard("bNode",
            pattern.getRoot().identifierIndex.getOne("b")
                .getAncestor(ReferenceExpression.class));
      }
    };

    assertExtractED(p, "uniform vec4 bam = foo + bar;");
    assertTrue(p.getDataMatches().isEmpty());
    assertEquals("bam",
        p.getNodeMatch("aNode", Identifier.class).getName());
    assertEquals("bar",
        p.getNodeMatch("bNode", ReferenceExpression.class)
            .getIdentifier().getName());

    assertExtractED(p, "uniform vec4 bam = foo + a();");
    assertTrue(p.getDataMatches().isEmpty());
    assertNotNull(p.getNodeMatch("bNode", FunctionCallExpression.class));

    assertExtractED(p, "uniform vec4 bam = foo + 5;");
    assertEquals(5,
        p.getNodeMatch("bNode", LiteralExpression.class).getNumber());
    assertExtractED(p, "uniform vec4 bam = foo + 5f;");
    assertEquals(5f,
        p.getNodeMatch("bNode", LiteralExpression.class).getNumber());
  }

  @Test
  void testExtractNodeWildcardsList() {
    var p = new Matcher<>("int ___a; int x = a; b; float ___b;", Matcher.translationUnitPattern, "___") {
      {
        markAnyWildcard("aNode", pattern.getRoot().identifierIndex
            .getOne("a").getAncestor(Expression.class));
        markAnyWildcard("bNode", pattern.getAncestor(
            TranslationUnit.class).getChildren().get(2));
      }
    };

    assertExtractTU(p, "int foo; int x = 45 + 5; void main() {} float bar;");
    assertEquals("foo", p.getStringDataMatch("a"));
    assertEquals("bar", p.getStringDataMatch("b"));
    assertEquals(AdditionExpression.class,
        p.getNodeMatch("aNode").getClass());
    assertEquals("main",
        p.getNodeMatch("bNode", FunctionDefinition.class)
            .getFunctionPrototype().getName().getName());
  }

  @Test
  void testPredicatedWildcard() {
    var p = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markPredicatedWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            node -> node instanceof EmptyDeclaration);
      }
    };

    assertNoExtractTU(p, "foo;");
    assertExtractTU(p, ";");
  }

  @Test
  void testClassWildcard1() {
    var p = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            EmptyDeclaration.class);
      }
    };

    assertNoExtractTU(p, "foo;");
    assertExtractTU(p, ";");
  }

  @Test
  void testNodeWildcardWithoutExtract() {
    var p = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            EmptyDeclaration.class);
      }
    };

    assertFalse(p.matches(transformer.parseTranslationUnit("foo;")));
    assertTrue(p.matches(transformer.parseTranslationUnit(";")));
  }

  @Test
  void testClassWildcard2() {
    var p = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            InnerASTNode.class);
      }
    };

    assertExtractTU(p, "foo;");
    assertExtractTU(p, ";");
  }

  @Test
  void testClassedPredicateWildcard() {
    var p = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassedPredicateWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            FunctionDefinition.class,
            functionCall -> functionCall.getFunctionPrototype()
                .getName().getName().equals("main"));
      }
    };

    assertNoExtractTU(p, "foo;");
    assertNoExtractTU(p, ";");
    assertExtractTU(p, "void main() {}");
    assertNoExtractTU(p, "void foo() {}");
  }

  @Test
  void testLiteralExpressionWildcard() {
    var p = new Matcher<>("int x = foo[index];", Matcher.translationUnitPattern) {
      {
        markClassedPredicateWildcard("index",
            pattern.getRoot().identifierIndex.getOne("index").getAncestor(ReferenceExpression.class),
            LiteralExpression.class,
            literalExpression -> {
              if (!literalExpression.isInteger()) {
                return false;
              }
              long index = literalExpression.integerValue;
              return index >= 0 && index < 8;
            });
      }
    };

    assertNoExtractTU(p, "int x = foo[-1];");
    assertNoExtractTU(p, "int x = foo[9];");
    assertNoExtractTU(p, "int x = foo[8];");
    assertExtractTU(p, "int x = foo[4];");
    assertExtractTU(p, "int x = foo[7];");
    assertExtractTU(p, "int x = foo[0];");
    assertNoExtractTU(p, ";");
  }
}
