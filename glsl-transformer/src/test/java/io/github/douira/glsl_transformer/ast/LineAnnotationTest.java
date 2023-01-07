package io.github.douira.glsl_transformer.ast;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;

import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;

public class LineAnnotationTest {
  @ParameterizedTest
  @TestCaseSource(caseSet = "testLineAnnotationReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testASTIntegration(String type, String input, String output) {
    assertReprint(PrintType.INDENTED_ANNOTATED, GLSLParser::translationUnit,
        output, input);
  }

  /**
   * TODO tests:
   * - test that no line directives are parsed with the regular ASTParser by
   * default
   * - test setting only lines on already set sources
   * - test setting lines if there are no sources set at all (should then never
   * show sources)
   * - test repeated directives only producing one line directive
   * - test line directives only working on statements and external declarations
   * - test line directives being ignored within other structures
   * - test throw on broken line directives (missing line, using strings, wrong
   * syntax)
   * - test cloning of source locations (from the cache but also just manually)
   */
}
