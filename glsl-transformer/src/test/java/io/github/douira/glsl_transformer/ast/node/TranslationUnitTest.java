package io.github.douira.glsl_transformer.ast.node;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestCaseProvider.Spacing;

@ExtendWith({ SnapshotExtension.class })
public class TranslationUnitTest extends TestWithASTTransformer {
  private Expect expect;

  @Test
  void testInjection() {
    transformer.setTransformation(tree -> tree.injectNode(
        ASTInjectionPoint.BEFORE_FUNCTIONS,
        transformer.parseNode(
            "float x;",
            tree,
            GLSLParser::externalDeclaration,
            ASTBuilder::visitExternalDeclaration)));
    assertEquals(
        "int a;\nfloat x;\nvoid main() {\n}\n",
        transformer.transform("int a;\nvoid main() {\n}\n"));
    assertEquals(
        "int a;\nfloat x;\n",
        transformer.transform("int a;\n"));
  }

  @ParameterizedTest
  @TestCaseSource(caseSet = "testInjectExDecl", spacing = Spacing.TRIM_SINGLE_BOTH)
  @SnapshotName("testInject")
  void testInject(String scenario, String input) {
    for (var location : ASTInjectionPoint.values()) {
      transformer.setTransformation(tree -> tree.injectNode(
          location,
          Stream.of("x", "y")
              .<ExternalDeclaration>map(name -> transformer.parseNode(
                  "float " + name + ";",
                  tree,
                  GLSLParser::externalDeclaration,
                  ASTBuilder::visitExternalDeclaration))
              .collect(Collectors.toList())));
      expect
          .scenario(scenario + "/" + location.toString().toLowerCase())
          .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(
              input, transformer.transform(input)));
    }
  }
}
