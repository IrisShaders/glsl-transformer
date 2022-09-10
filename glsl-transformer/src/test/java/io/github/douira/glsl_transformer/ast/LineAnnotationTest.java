package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;

public class LineAnnotationTest {
  @ParameterizedTest
  @TestCaseSource(caseSet = "testLineAnnotation", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    assertReprint(GLSLParser::translationUnit, output, input);
  }
}
