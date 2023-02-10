package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import java.util.*;
import java.util.function.Function;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

@ExtendWith({ SnapshotExtension.class })
public class ASTReprintTest {
  static Map<String, Function<GLSLParser, ? extends ParserRuleContext>> parseMethodNames = new HashMap<>() {
    {
      put("translationUnit", GLSLParser::translationUnit);
      put("expression", GLSLParser::expression);
      put("statement", GLSLParser::statement);
      put("fullySpecifiedType", GLSLParser::fullySpecifiedType);
    }
  };

  private Expect expect;

  private static Function<GLSLParser, ? extends ParserRuleContext> getParseMethod(String type) {
    var parseMethod = parseMethodNames.get(type);
    if (parseMethod == null) {
      throw new IllegalArgumentException("Unknown parse method type: " + type);
    }
    return parseMethod;
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "testReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    assertReprint(getParseMethod(type), output, input);
  }

  @ParameterizedTest
  @SnapshotName("testASTTree")
  @TestCaseSource(caseSet = "testReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTTree(String type, String input, String output) {
    var ast = parseAST(getParseMethod(type), input);
    var astPrinter = new PrintAST();
    astPrinter.visit(ast);
    expect
        .scenario(type + "_" + getBase64Hash(input))
        .toMatchSnapshot(
            SnapshotUtil.inputOutputSnapshot(input, astPrinter.getResult()));
  }
}
