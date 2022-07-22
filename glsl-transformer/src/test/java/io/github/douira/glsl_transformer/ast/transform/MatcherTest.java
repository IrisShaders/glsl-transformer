package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.PreciseQualifier;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class MatcherTest extends TestWithASTTransformer {
  Function<GLSLParser, ? extends ExtendedContext> parseMethod;
  Matcher p;

  @BeforeEach
  void setup() {
    parseMethod = null;
    p = null;
  }

  private void assertNoMatch(String input) {
    assertFalse(p.matches(transformer.parseNode(input, parseMethod)));
  }

  private void assertMatch(String input) {
    assertTrue(p.matches(transformer.parseNode(input, parseMethod)));
  }

  private void assertNoExtract(String input) {
    assertFalse(p.matchesExtract(transformer.parseNode(input, parseMethod)));
  }

  private void assertExtract(String input) {
    assertTrue(p.matchesExtract(transformer.parseNode(input, parseMethod)));
  }

  @Test
  void testMatches() {
    parseMethod = GLSLParser::externalDeclaration;
    p = new Matcher("uniform vec4 entityColor;", parseMethod);

    assertFalse(p.matches(null));
    assertFalse(p.matches(new PreciseQualifier()));
    assertMatch("uniform vec4 entityColor;");
    assertNoMatch("uniform vec3 entityColor;");
    assertNoMatch("in vec4 entityColor;");
    assertNoMatch("uniform precise vec4 entityColor;");
    assertNoMatch("uniform vec4 entityColo;");
    assertNoMatch("uniform vec4 entityColor_;");
  }

  @Test
  void testMatchesDataWildcard() {
    parseMethod = GLSLParser::externalDeclaration;
    p = new Matcher("uniform vec4 ___ = foo + ___;",
        parseMethod, "___");

    assertFalse(p.matches(null));
    assertFalse(p.matches(new PreciseQualifier()));
    assertMatch("uniform vec4 foo = foo + foo;");
    assertNoMatch("uniform vec3 foo = foo + foo;");
    assertNoMatch("in vec4 foo = foo + foo;");
    assertNoMatch("uniform precise vec4 foo = foo + foo;");
    assertNoMatch("uniform vec4 foo = bar + foo;");
    assertNoMatch("uniform vec4 foo = __ + foo;");
  }

  @Test
  void testExtractDataWildcards() {
    parseMethod = GLSLParser::externalDeclaration;
    p = new Matcher("uniform vec4 ___a = foo + ___b;",
        parseMethod, "___");

    assertFalse(p.matchesExtract(null));
    assertTrue(p.getDataMatches().isEmpty());
    assertTrue(p.getNodeMatches().isEmpty());

    assertFalse(p.matchesExtract(new PreciseQualifier()));
    assertTrue(p.getDataMatches().isEmpty());
    assertTrue(p.getNodeMatches().isEmpty());

    assertExtract("uniform vec4 bar = foo + bam;");
    var dataMatches = p.getDataMatches();
    assertEquals("bar", dataMatches.get("a"));
    assertEquals("bam", dataMatches.get("b"));
    assertEquals(dataMatches.get("b"), p.getDataMatch("b"));
    assertEquals(dataMatches.get("b"), p.getStringDataMatch("b"));
    assertTrue(p.getNodeMatches().isEmpty());

    assertNoMatch("uniform vec4 bar = foo;");
    // It should not match Identifiers with LiteralExpressions
    assertNoMatch("uniform vec4 bar = foo + 5;");
  }

  @Test
  void testExtractNodeWildcards() {
    parseMethod = GLSLParser::externalDeclaration;
    p = new Matcher("uniform vec4 a = foo + b;", parseMethod) {
      {
        markAnyWildcard("aNode",
            pattern.getRoot().identifierIndex.getOne("a"));
        markAnyWildcard("bNode",
            pattern.getRoot().identifierIndex.getOne("b")
                .getAncestor(ReferenceExpression.class));
      }
    };

    assertExtract("uniform vec4 bam = foo + bar;");
    assertTrue(p.getDataMatches().isEmpty());
    assertEquals("bam",
        p.getNodeMatch("aNode", Identifier.class).getName());
    assertEquals("bar",
        p.getNodeMatch("bNode", ReferenceExpression.class)
            .getIdentifier().getName());

    assertExtract("uniform vec4 bam = foo + a();");
    assertTrue(p.getDataMatches().isEmpty());
    assertNotNull(p.getNodeMatch("bNode", FunctionCallExpression.class));

    assertExtract("uniform vec4 bam = foo + 5;");
    assertEquals(5,
        p.getNodeMatch("bNode", LiteralExpression.class).getNumber());
    assertExtract("uniform vec4 bam = foo + 5f;");
    assertEquals(5f,
        p.getNodeMatch("bNode", LiteralExpression.class).getNumber());
  }

  @Test
  void testExtractNodeWildcardsList() {
    parseMethod = GLSLParser::translationUnit;
    p = new Matcher("int ___a; int x = a; b; float ___b;", parseMethod, "___") {
      {
        markAnyWildcard("aNode", pattern.getRoot().identifierIndex
            .getOne("a").getAncestor(Expression.class));
        markAnyWildcard("bNode", pattern.getAncestor(
            TranslationUnit.class).getChildren().get(2));
      }
    };

    assertExtract("int foo; int x = 45 + 5; void main() {} float bar;");
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
    parseMethod = GLSLParser::translationUnit;
    p = new Matcher("a;", parseMethod) {
      {
        markPredicatedWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            node -> node instanceof EmptyDeclaration);
      }
    };

    assertNoExtract("foo;");
    assertExtract(";");
  }

  @Test
  void testClassWildcard1() {
    parseMethod = GLSLParser::translationUnit;
    p = new Matcher("a;", parseMethod) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            EmptyDeclaration.class);
      }
    };

    assertNoExtract("foo;");
    assertExtract(";");
  }

  @Test
  void testClassWildcard2() {
    parseMethod = GLSLParser::translationUnit;
    p = new Matcher("a;", parseMethod) {
      {
        markClassWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            InnerASTNode.class);
      }
    };

    assertExtract("foo;");
    assertExtract(";");
  }

  @Test
  void testClassedPredicateWildcard() {
    parseMethod = GLSLParser::translationUnit;
    p = new Matcher("a;", parseMethod) {
      {
        markClassedPredicateWildcard("bNode",
            pattern.getAncestor(TranslationUnit.class)
                .getChildren().get(0),
            FunctionDefinition.class,
            functionCall -> functionCall.getFunctionPrototype()
                .getName().getName().equals("main"));
      }
    };

    assertNoExtract("foo;");
    assertNoExtract(";");
    assertExtract("void main() {}");
    assertNoExtract("void foo() {}");
  }
}
