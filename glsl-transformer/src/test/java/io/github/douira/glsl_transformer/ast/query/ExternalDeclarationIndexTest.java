package io.github.douira.glsl_transformer.ast.query;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class ExternalDeclarationIndexTest extends TestWithSingleASTTransformer {
  //TODO tests:
  // test querying for an existing ED
  // test querying after adding an ED
  // test querying after removing an ED
  // test querying after moving an ED around
  // test querying after renaming an identifier that changes the name of an ED (using root.rename)
  // test querying after manually changing the name of an ED's name-giving identifier
  // test querying after adding a subtree that was parsed into a separate tree first
  // test querying after moving a subtree into a separate tree without deleting it, and then moving it back
  // test querying for an ED of the types: type and init, function defintion, function declaration, variable declaration, interface block, extension, pragma, custom, include
  // test querying for an ED after renaming it by changing the name in each of the above types
  // test querying for an ED with a prefix index
  // test querying after swapping out a TypeAndInitDeclaration that has a different set of declaration members (to test the iterate subtree entries special case)
  // test querying for the same ED with multiple different names (multi-member declarations)
  // check for coverage and test that

  @Test
  void testEDQuery() {

  }
}
