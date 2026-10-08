package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;
import io.github.douira.glsl_transformer.test_util.TestCaseSource;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;
import org.junit.jupiter.params.ParameterizedTest;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.assertReprint;

public class LineAnnotationTest extends TestWithSingleASTTransformer {
  @ParameterizedTest
  @TestCaseSource(caseSet = "testLineAnnotationReprint", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testLineAnnotationReprint(String type, String input, String output) {
    assertReprint(PrintType.INDENTED_ANNOTATED, GLSLParser::translationUnit,
        output, input);
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "testLineAnnotationWithTransform", spacing = Spacing.TRIM_SINGLE_BOTH)
  void testLineAnnotationWithTransform(String type, String input, String output) {
    p.setTransformation((tree, root) -> {
      var children = tree.getChildren();
      var target = root.identifierIndex.getUnique("target").getAncestor(ExternalDeclaration.class);
      var inject = p.parseExternalDeclaration(root, "#line 10 \"injected\"\nint injected;");
      children.add(children.indexOf(target), inject);
    });
    assertTransformIA(output, input);
  }

  /**
   * TODO tests:
   * - test that no line directives are parsed with the regular ASTParser by
   * default
   * - test line directives only working on statements and external declarations
   * - test line directives being ignored within other structures
   * - test throw on broken line directives (missing line, using strings, wrong
   * syntax)
   */
}
