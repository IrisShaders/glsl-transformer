package io.github.douira.glsl_transformer.ast.node.declaration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class TypeAndInitDeclarationTest extends TestWithSingleASTTransformer {
  // test that parsing a type and init declaration yields the right amount of
  // members
  @Test
  void testMemberConstruction() {
    p.setTransformation((tree, root) -> {
      assertEquals(3, root.nodeIndex.getOne(TypeAndInitDeclaration.class).getMembers().size(), "It should have 3 members");
    });
    p.transform("int a = 1, b = 2, c = 3;");
  }
}
