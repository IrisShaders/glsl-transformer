package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTReprintTest {
  void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String expected,
      String input) {
    var parser = new EnhancedParser();
    var parseTree = parser.parse(input, parseMethod);
    var ast = ASTBuilder.build(parseTree);
    var reprinted = ASTPrinter.printedIndented(ast);
    assertEquals(expected, reprinted);
  }

  void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String input) {
    assertReprint(parseMethod, input, input);
  }

  void assertReprint(String expected, String input) {
    assertReprint(GLSLParser::translationUnit, expected, input);
  }

  void assertReprint(String input) {
    assertReprint(input, input);
  }

  void assertReprintExpression(String expected, String input) {
    assertReprint(GLSLParser::expression, expected, input);
  }

  void assertReprintExpression(String input) {
    assertReprintExpression(input, input);
  }

  void assertReprintStatement(String expected, String input) {
    assertReprint(GLSLParser::statement, expected, input);
  }

  void assertReprintStatement(String input) {
    assertReprintStatement(input, input);
  }

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

    // assertReprint(";\n");
    // assertReprintExpression("1 + 2");
    // assertReprintStatement("if (a && b) {\n1;\n}\n");
  }
}
