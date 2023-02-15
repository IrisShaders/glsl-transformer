package io.github.douira.glsl_transformer.ast.query;

// import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.declaration.DeclarationMember;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.transform.ASTInjectionPoint;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class ExternalDeclarationIndexTest extends TestWithSingleASTTransformer {
  @Test
  void testQuery() {
    // test querying for an existing ED
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var entry = root.externalDeclarationIndex.getOne("a");
      assertNotNull(entry);
      assertEquals("a", ((DeclarationMember) entry.keyMember()).getName().getName());
      assertSame(
          root.identifierIndex.getOne("a").getAncestor(ExternalDeclaration.class),
          entry.declaration());
    });
    p.transform("int a = 1; int b = 2; int c = 3;");
  }

  @Test
  void testQueryAfterAddAndRemove() {
    // test querying after adding and removing an ED
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertFalse(edi.has("c"));

      tree.parseAndInjectNode(p, ASTInjectionPoint.BEFORE_DECLARATIONS, "int c = 3;");

      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertTrue(edi.has("c"));

      edi.getOne("c").declaration().detachAndDelete();

      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertFalse(edi.has("c"));

      edi.getOne("a").declaration().detachAndDelete();

      assertFalse(edi.has("a"));
      assertTrue(edi.has("b"));
      assertFalse(edi.has("c"));
    });
    p.transform("int a = 1; int b = 2;");
  }

  @Test
  void testQueryAfterMove() {
    // test querying after moving an ED around
    // TODO
  }

  @Test
  void testQueryAfterRelevantIdentifierRename() {
    // test querying after renaming an identifier that changes the name of an ED
    // (using root.rename)
    // TODO
  }

  @Test
  void testQueryAfterIdentifierNameChange() {
    // test querying after manually changing the name of an ED's name-giving
    // identifier
    // TODO
  }

  @Test
  void testQueryAddSeparateRootSubtree() {
    // test querying after adding a subtree that was parsed into a separate tree
    // first
    // TODO
  }

  @Test
  void testQuerySeparateSubtreeOut() {
    // test querying after moving a subtree into a separate tree without deleting
    // it, and then moving it back
    // TODO
  }

  @Test
  void testQueryWithTypes() {
    // test querying for an ED of the types: type and init, function definition,
    // function declaration, variable declaration, interface block, extension,
    // pragma, custom, include
    // TODO
  }

  @Test
  void testQueryAfterRenameTypeSpecific() {
    // test querying for an ED after renaming it by changing the name in each of the
    // above types
    // TODO
  }

  @Test
  void testQueryPrefixIndex() {
    // test querying for an ED with a prefix index
    // TODO
  }

  @Test
  void testQurySwapTypeAndInitMemberSet() {
    // test querying after swapping out a TypeAndInitDeclaration that has a
    // different set of declaration members (to test the iterate subtree entries
    // special case)
    // TODO
  }

  @Test
  void testQueryMultipleEntries() {
    // test querying for the same ED with multiple different names (multi-member
    // declarations)
    // TODO
  }

  @Test
  void test__() {
    // check for coverage and test that
    // TODO
  }
}
