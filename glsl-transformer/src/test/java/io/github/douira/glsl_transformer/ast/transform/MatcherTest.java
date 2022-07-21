package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.PreciseQualifier;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class MatcherTest extends TestWithASTTransformer {
  private void assertMatch(
      Matcher matcher,
      String input,
      Function<GLSLParser, ? extends ExtendedContext> parseMethod) {
    assertTrue(matcher.matches(
        transformer.parseNodeWithoutRoot(input, parseMethod)));
  }

  private void assertNoMatch(
      Matcher matcher,
      String input,
      Function<GLSLParser, ? extends ExtendedContext> parseMethod) {
    assertFalse(matcher.matches(
        transformer.parseNodeWithoutRoot(input, parseMethod)));
  }

  @Test
  void testMatches() {
    var p = new Matcher("uniform vec4 entityColor;",
        GLSLParser::externalDeclaration);
    assertFalse(p.matches(null));
    assertFalse(p.matches(new PreciseQualifier()));
    assertMatch(p, "uniform vec4 entityColor;", GLSLParser::externalDeclaration);
    assertNoMatch(p, "uniform vec3 entityColor;", GLSLParser::externalDeclaration);
    assertNoMatch(p, "in vec4 entityColor;", GLSLParser::externalDeclaration);
    assertNoMatch(p, "uniform precise vec4 entityColor;", GLSLParser::externalDeclaration);
    assertNoMatch(p, "uniform vec4 entityColo;", GLSLParser::externalDeclaration);
    assertNoMatch(p, "uniform vec4 entityColor_;", GLSLParser::externalDeclaration);
  }
}
