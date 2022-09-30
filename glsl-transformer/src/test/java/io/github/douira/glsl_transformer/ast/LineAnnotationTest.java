package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;

public class LineAnnotationTest {
  @Disabled
  @ParameterizedTest
  @TestCaseSource(caseSet = "testLineAnnotation", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    assertReprint(PrintType.INDENTED_ANNOTATED, GLSLParser::translationUnit,
        output, input);
  }
}
