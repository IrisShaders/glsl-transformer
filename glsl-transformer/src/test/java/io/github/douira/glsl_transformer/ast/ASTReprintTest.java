package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTReprintTest {
  static Map<String, Function<GLSLParser, ? extends ExtendedContext>> parseMethodNames = new HashMap<>() {
    {
      put("translationUnit", GLSLParser::translationUnit);
      put("expression", GLSLParser::expression);
      put("statement", GLSLParser::statement);
      put("fullySpecifiedType", GLSLParser::fullySpecifiedType);
    }
  };

  @ParameterizedTest
  @TestCaseSource(caseSet = "testReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    var parseMethod = parseMethodNames.get(type);
    if (parseMethod == null) {
      throw new IllegalArgumentException("Unknown parse method type: " + type);
    }
    assertReprint(parseMethod, output, input);
  }
}
