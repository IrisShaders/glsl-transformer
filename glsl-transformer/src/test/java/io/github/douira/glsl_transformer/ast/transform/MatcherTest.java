package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.DeclarationMember;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class MatcherTest extends TestWithSingleASTTransformer {
  private void assertNoMatchED(Matcher<ExternalDeclaration> m, String input) {
    assertFalse(m.matches(p.parseSeparateExternalDeclaration(input)));
  }

  private void assertMatchED(Matcher<ExternalDeclaration> m, String input) {
    assertTrue(m.matches(p.parseSeparateExternalDeclaration(input)));
  }

  private void assertExtractED(Matcher<ExternalDeclaration> m, String input) {
    assertTrue(m.matchesExtract(p.parseSeparateExternalDeclaration(input)));
  }

  private void assertNoExtractTU(Matcher<TranslationUnit> m, String input) {
    assertFalse(m.matchesExtract(p.parseTranslationUnit(input)));
  }

  private void assertExtractTU(Matcher<TranslationUnit> m, String input) {
    assertTrue(m.matchesExtract(p.parseTranslationUnit(input)));
  }

  private void assertMatchTU(Matcher<TranslationUnit> m, String input) {
    assertTrue(m.matches(p.parseTranslationUnit(input)));
  }

  private void assertNoMatchTU(Matcher<TranslationUnit> m, String input) {
    assertFalse(m.matches(p.parseTranslationUnit(input)));
  }

  private void assertMatchEx(Matcher<Expression> m, String input) {
    assertTrue(m.matches(p.parseSeparateExpression(input)));
  }

  private void assertNoMatchEx(Matcher<Expression> m, String input) {
    assertFalse(m.matches(p.parseSeparateExpression(input)));
  }

  @Test
  void testMatches() {
    var m = new Matcher<>("uniform vec4 entityColor;",
        Matcher.externalDeclarationPattern);

    assertFalse(m.matches(null));
    assertMatchED(m, "uniform vec4 entityColor;");
    assertNoMatchED(m, "uniform vec3 entityColor;");
    assertNoMatchED(m, "in vec4 entityColor;");
    assertNoMatchED(m, "uniform precise vec4 entityColor;");
    assertNoMatchED(m, "uniform vec4 entityColo;");
    assertNoMatchED(m, "uniform vec4 entityColor_;");
  }

  @Test
  void testMatchesDataWildcard() {
    var m = new Matcher<>("uniform vec4 ___ = foo + ___;",
        Matcher.externalDeclarationPattern, "___");

    assertFalse(m.matches(null));
    assertMatchED(m, "uniform vec4 foo = foo + foo;");
    assertNoMatchED(m, "uniform vec3 foo = foo + foo;");
    assertNoMatchED(m, "in vec4 foo = foo + foo;");
    assertNoMatchED(m, "uniform precise vec4 foo = foo + foo;");
    assertNoMatchED(m, "uniform vec4 foo = bar + foo;");
    assertNoMatchED(m, "uniform vec4 foo = __ + foo;");
  }

  @Test
  void testExtractDataWildcards() {
    var m = new Matcher<>("uniform vec4 ___a = foo + ___b;",
        Matcher.externalDeclarationPattern, "___");

    assertFalse(m.matchesExtract(null));
    assertTrue(m.getDataMatches().isEmpty());
    assertTrue(m.getNodeMatches().isEmpty());

    assertExtractED(m, "uniform vec4 bar = foo + bam;");
    var dataMatches = m.getDataMatches();
    assertEquals("bar", dataMatches.get("a"));
    assertEquals("bam", dataMatches.get("b"));
    assertEquals(dataMatches.get("b"), m.getDataMatch("b"));
    assertEquals(dataMatches.get("b"), m.getStringDataMatch("b"));
    assertTrue(m.getNodeMatches().isEmpty());

    assertNoMatchED(m, "uniform vec4 bar = foo;");
    // It should not match Identifiers with LiteralExpressions
    assertNoMatchED(m, "uniform vec4 bar = foo + 5;");
  }

  @Test
  void testExtractNodeWildcards() {
    var m = new Matcher<>("uniform vec4 a = foo + b;", Matcher.externalDeclarationPattern) {
      {
        markAnyWildcard("aNode",
            pattern.getRoot().identifierIndex.getOne("a"));
        markAnyWildcard("bNode",
            pattern.getRoot().identifierIndex.getOne("b")
                .getAncestor(ReferenceExpression.class));
      }
    };

    assertExtractED(m, "uniform vec4 bam = foo + bar;");
    assertTrue(m.getDataMatches().isEmpty());
    assertEquals("bam",
        m.getNodeMatch("aNode", Identifier.class).getName());
    assertEquals("bar",
        m.getNodeMatch("bNode", ReferenceExpression.class)
            .getIdentifier().getName());

    assertExtractED(m, "uniform vec4 bam = foo + a();");
    assertTrue(m.getDataMatches().isEmpty());
    assertNotNull(m.getNodeMatch("bNode", FunctionCallExpression.class));

    assertExtractED(m, "uniform vec4 bam = foo + 5;");
    assertEquals(5,
        m.getNodeMatch("bNode", LiteralExpression.class).getNumber());
    assertExtractED(m, "uniform vec4 bam = foo + 5f;");
    assertEquals(5f,
        m.getNodeMatch("bNode", LiteralExpression.class).getNumber());
  }

  @Test
  void testExtractNodeWildcardsList() {
    var m = new Matcher<>("int ___a; int x = a; b; float ___b;", Matcher.translationUnitPattern, "___") {
      {
        markAnyWildcard("aNode", pattern.getRoot().identifierIndex
            .getOne("a").getAncestor(Expression.class));
        markAnyWildcard("bNode", pattern.getAncestor(
            TranslationUnit.class).getChildren().get(2));
      }
    };

    assertExtractTU(m, "int foo; int x = 45 + 5; void main() {} float bar;");
    assertEquals("foo", m.getStringDataMatch("a"));
    assertEquals("bar", m.getStringDataMatch("b"));
    assertEquals(AdditionExpression.class,
        m.getNodeMatch("aNode").getClass());
    assertEquals("main",
        m.getNodeMatch("bNode", FunctionDefinition.class)
            .getFunctionPrototype().getName().getName());
  }

  @Test
  void testPredicatedWildcard() {
    var m = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markPredicatedWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            node -> node instanceof EmptyDeclaration);
      }
    };

    assertNoExtractTU(m, "foo;");
    assertExtractTU(m, ";");
  }

  @Test
  void testClassWildcard1() {
    var m = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            EmptyDeclaration.class);
      }
    };

    assertNoExtractTU(m, "foo;");
    assertExtractTU(m, ";");
  }

  @Test
  void testNodeWildcardWithoutExtract() {
    var m = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            EmptyDeclaration.class);
      }
    };

    assertNoMatchTU(m, "foo;");
    assertMatchTU(m, ";");
  }

  @Test
  void testClassWildcard2() {
    var m = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            InnerASTNode.class);
      }
    };

    assertExtractTU(m, "foo;");
    assertExtractTU(m, ";");
  }

  @Test
  void testClassedPredicateWildcard() {
    var m = new Matcher<>("a;", Matcher.translationUnitPattern) {
      {
        markClassedPredicateWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            FunctionDefinition.class,
            functionCall -> functionCall.getFunctionPrototype()
                .getName().getName().equals("main"));
      }
    };

    assertNoExtractTU(m, "foo;");
    assertNoExtractTU(m, ";");
    assertExtractTU(m, "void main() {}");
    assertNoExtractTU(m, "void foo() {}");
  }

  @Test
  void testLiteralExpressionWildcard() {
    var m = new Matcher<>("int x = foo[index];", Matcher.translationUnitPattern) {
      {
        markClassedPredicateWildcard("index",
            pattern.getRoot().identifierIndex.getOne("index").getAncestor(ReferenceExpression.class),
            LiteralExpression.class,
            literalExpression -> {
              if (!literalExpression.isInteger()) {
                return false;
              }
              long index = literalExpression.getInteger();
              return index >= 0 && index < 8;
            });
      }
    };

    assertNoExtractTU(m, "int x = foo[-1];");
    assertNoExtractTU(m, "int x = foo[9];");
    assertNoExtractTU(m, "int x = foo[8];");
    assertExtractTU(m, "int x = foo[4];");
    assertExtractTU(m, "int x = foo[7];");
    assertExtractTU(m, "int x = foo[0];");
    assertNoExtractTU(m, ";");
    assertNoMatchTU(m, "int x = foo[8];");
    assertMatchTU(m, "int x = foo[4];");
  }

  @Test
  void testAutoHintedMatcher() {
    var m = new AutoHintedMatcher<Expression>(
        "gl_TextureMatrix[index]", Matcher.expressionPattern) {
      {
        markClassedPredicateWildcard("index",
            pattern.getRoot().identifierIndex.getOne("index").getAncestor(ReferenceExpression.class),
            LiteralExpression.class,
            literalExpression -> {
              if (!literalExpression.isInteger()) {
                return false;
              }
              long index = literalExpression.getInteger();
              return index >= 0 && index < 8;
            });
      }
    };
    assertMatchEx(m, "gl_TextureMatrix[1]");
    assertNoMatchEx(m, "gl_TextureMatrix[-1]");
    assertNoMatchEx(m, "gl_TextureMatrix[8]");
    assertNoMatchEx(m, "gl_TextureMatrix[foo]");
    assertNoMatchEx(m, "gl_TextureMatrix[1.0]");
  }

  @Test
  void testMatchOnlyUniform() {
    p.setTransformation((tree, root) -> {
      root.process(root.identifierIndex.getStream("texture")
          .filter(id -> !(id.getParent() instanceof FunctionCallExpression)),
          id -> {
            id.setName("gtexture");
          });
    });

    assertTransform("int x = texture(shadowtex0, shadowPos.xy + offset * shadowPos.w).r; ",
        "int x = texture(shadowtex0, shadowPos.xy + offset * shadowPos.w).r;");
    assertTransform("int x = texture(gtexture, vec2(0)); ", "int x = texture(texture, vec2(0));");
  }

  @Test
  void testMatchList() {
    var m = new Matcher<>("int a = foo + 4;", Matcher.externalDeclarationPattern) {
      {
        markClassWildcard("member*",
            pattern.getRoot().identifierIndex.getOne("a").getAncestor(DeclarationMember.class),
            DeclarationMember.class);
      }
    };

    assertMatchED(m, "int foo, bar;");
    assertMatchED(m, "int foo, bar, a, b, c, d;");
    assertMatchED(m, "int foo;");
    assertNoMatchED(m, "out int foo, bar;");
  }
}
