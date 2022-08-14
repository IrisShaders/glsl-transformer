package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.test_util.TestWithGroupedASTTransformer;

public class GroupedASTTransformerTest extends TestWithGroupedASTTransformer {
  @Test
  void testInsertion() {
    // TODO: add tri ast transformer test
    // p.setTransformation(translationUnit -> {
    //   translationUnit.children.add(index, p.parseExternalDeclaration(
    //       translationUnit, "int a;"));
    // });
    // assertEquals(output, p.transform(PrintType.COMPACT, input));
  }
}
