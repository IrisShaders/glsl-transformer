package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTIntegrationTest {
  void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String expected,
      String input) {
    var manager = new TransformationManager<>();
    var parseTree = manager.parse(input, parseMethod);
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

  @ParameterizedTest
  @TestCaseSource(caseSet = "testReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    switch (type) {
      case "translationUnit":
        assertReprint(output, input);
        break;
      case "expression":
        assertReprintExpression(output, input);
        break;
      case "statement":
        assertReprintStatement(output, input);
        break;
      default:
        throw new IllegalArgumentException("Unknown type");
    }

    // assertReprint(";\n");
    // assertReprintExpression("1 + 2");
    // assertReprintStatement("if (a && b) {\n1;\n}\n");
  }
}
