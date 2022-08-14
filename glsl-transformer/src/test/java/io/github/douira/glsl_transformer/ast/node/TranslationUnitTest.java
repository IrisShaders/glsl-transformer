package io.github.douira.glsl_transformer.ast.node;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.ASTInjectionPoint;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

@ExtendWith({ SnapshotExtension.class })
public class TranslationUnitTest extends TestWithSingleASTTransformer {
  private Expect expect;

  @Test
  void testInjection() {
    p.setPrintType(PrintType.INDENTED);
    p.setTransformation(tree -> tree.injectNode(
        ASTInjectionPoint.BEFORE_FUNCTIONS,
        p.parseExternalDeclaration(tree, "float x;")));
    assertEquals(
        "int a;\nfloat x;\nvoid main() {\n}\n",
        p.transform("int a;\nvoid main() {\n}\n"));
    assertEquals(
        "int a;\nfloat x;\n",
        p.transform("int a;\n"));
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "testInjectExDecl", spacing = Spacing.TRIM_SINGLE_BOTH)
  @SnapshotName("testInject")
  void testInject(String scenario, String input) {
    p.setPrintType(PrintType.INDENTED);
    for (var location : ASTInjectionPoint.values()) {
      p.setTransformation(tree -> tree.injectNodes(
          location,
          Stream.of("x", "y")
              .<ExternalDeclaration>map(name -> p.parseExternalDeclaration(
                  tree, "float " + name + ";"))
              .collect(Collectors.toList())));
      expect
          .scenario(scenario + "/" + location.toString().toLowerCase())
          .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(
              input, p.transform(input)));
    }
  }
}
