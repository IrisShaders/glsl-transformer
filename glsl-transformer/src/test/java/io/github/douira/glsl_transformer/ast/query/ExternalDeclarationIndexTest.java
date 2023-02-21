package io.github.douira.glsl_transformer.ast.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.declaration.DeclarationMember;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.query.index.*;
import io.github.douira.glsl_transformer.ast.query.index.ExternalDeclarationIndex.DeclarationEntry;
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
  void testQueryAfterMoveAndDetach() {
    // test querying after moving an ED around, also detaching it
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertTrue(edi.has("c"));

      var a = tree.getChildren().get(0);
      a.detach();

      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertTrue(edi.has("c"));

      tree.getChildren().add(0, a);

      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertTrue(edi.has("c"));

      a.detachAndDelete();

      assertFalse(edi.has("a"));

      tree.getChildren().add(1, a);

      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));

      var tree2 = p.parseSeparateTranslationUnit("int d = 4;");
      a.detachAndDelete();
      tree2.getChildren().add(a);

      assertFalse(edi.has("a"));
      assertFalse(edi.has("d"));
      assertTrue(tree2.getRoot().externalDeclarationIndex.has("a"));
    });
    p.transform("int a = 1; int b = 2; int c = 3;");
  }

  @Test
  void testQueryAfterRelevantIdentifierRename() {
    // test querying after renaming an identifier that changes the name of an ED
    // (using root.rename)
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertTrue(edi.has("a"));

      root.rename("a", "d");

      assertFalse(edi.has("a"));
      assertTrue(edi.has("d"));
    });
    p.transform("int a = 1; int b = 2; int c = 3;");
  }

  @Test
  void testQueryAfterIdentifierNameChange() {
    // test querying after manually changing the name of an ED's name-giving
    // identifier
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;

      // test renaming an identifier in a type and init declaration
      assertTrue(edi.has("a"));

      ((DeclarationMember) edi.getOne("a").keyMember()).getName().setName("d");

      assertFalse(edi.has("a"));
      assertTrue(edi.has("d"));

      // test renaming an identifier in a variable declaration
      assertTrue(edi.has("u"));

      ((Identifier) edi.getOne("u").keyMember()).setName("e");

      assertFalse(edi.has("u"));
      assertTrue(edi.has("e"));
    });
    p.transform("int a = 1; int b = 2; int c = 3; uniform u;");
  }

  @Test
  void testQueryAddSeparateRootSubtree() {
    // test querying after adding a subtree that was parsed into a separate tree
    // first
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertFalse(edi.has("b"));
      assertFalse(edi.has("c"));

      tree.getChildren().add(p.parseSeparateExternalDeclaration("int b = 3;"));
      tree.getChildren().add(p.parseSeparateExternalDeclaration("void main() { int c = 4; }"));

      assertTrue(edi.has("b"));
      assertFalse(edi.has("c"));

      ((FunctionDefinition) tree.getChildren().get(tree.getChildren().size() - 1))
          .getFunctionPrototype().getName().setName("foo");

      assertTrue(edi.has("b"));
      assertFalse(edi.has("c"));
      assertTrue(edi.has("foo"));

      root.identifierIndex.getOne("c").setName("bar");
      assertFalse(edi.has("c"));
      assertFalse(edi.has("bar"));
    });
    p.transform("int a = 1;");
  }

  @Test
  void testQueryWithTypes() {
    // test querying for an ED of the types: type and init, function definition,
    // function declaration, variable declaration, interface block, extension,
    // custom, include
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.getLexer().enableCustomDirective = true;
    p.getLexer().enableIncludeDirective = true;
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertTrue(edi.has("typeAndInit"));
      assertTrue(edi.has("functionDef"));
      assertTrue(edi.has("uni"));
      assertTrue(edi.has("interfaceBlockDecl"));
      assertFalse(edi.has("interfaceBlockDeclName"));
      assertTrue(edi.has("variableDecl"));
      assertTrue(edi.has("ext"));
      assertFalse(edi.has("prag"));
      assertTrue(edi.has("cust"));
      assertTrue(edi.has("incl"));

      // test renaming each one
      root.rename("typeAndInit", "a");
      root.rename("functionDef", "b");
      root.rename("functionDecl", "bb");
      root.rename("uni", "c");
      root.rename("interfaceBlockDecl", "d");
      root.rename("interfaceBlockDeclName", "d_");
      root.rename("variableDecl", "e");
      root.nodeIndex.getOne(ExtensionDirective.class).setName("f");
      root.nodeIndex.getOne(CustomDirective.class).setContent("h");
      root.nodeIndex.getOne(IncludeDirective.class).setContent("i");

      assertFalse(root.identifierIndex.has("typeAndInit"));
      assertFalse(root.identifierIndex.has("functionDef"));
      assertFalse(root.identifierIndex.has("functionDecl"));
      assertFalse(root.identifierIndex.has("uni"));
      assertFalse(root.identifierIndex.has("interfaceBlockDecl"));
      assertFalse(root.identifierIndex.has("interfaceBlockDeclName"));
      assertFalse(root.identifierIndex.has("variableDecl"));

      assertFalse(edi.has("typeAndInit"));
      assertFalse(edi.has("functionDef"));
      assertFalse(edi.has("functionDecl"));
      assertFalse(edi.has("uni"));
      assertFalse(edi.has("interfaceBlockDecl"));
      assertFalse(edi.has("interfaceBlockDeclName"));
      assertFalse(edi.has("variableDecl"));
      assertFalse(edi.has("ext"));
      assertFalse(edi.has("cust"));
      assertFalse(edi.has("incl"));
    });
    p.transform(
        "int typeAndInit = 1; void functionDef() {} void functionDecl(); uniform uni; precise interfaceBlockDecl { int a; } interfaceBlockDeclName; in variableDecl; #extension ext\n #pragma prag\n #custom cust\n #include <incl>\n");
  }

  @Test
  @SuppressWarnings("unchecked")
  void testQueryPrefixIndex() {
    // test querying for an ED with a prefix index
    p.setRootSupplier(new RootSupplier(
        NodeIndex::withUnordered,
        IdentifierIndex::withOnlyExact,
        PrefixExternalDeclarationIndex::withPrefix));
    p.setTransformation((tree, root) -> {
      var edi = root.getPrefixExternalDeclarationIndex();
      assertEquals(4, edi.prefixQuery("a").count());
      assertEquals(1, edi.prefixQuery("ab").count());
      assertEquals(1, edi.prefixQuery("aa").count());
      assertEquals(0, edi.prefixQuery("abb").count());
    });
    p.transform("int a = 1; int aabb = 2; int abc = 3; void amain() {} uniform uni;");

    p.setRootSupplier(new RootSupplier(
        NodeIndex::withUnordered,
        IdentifierIndex::withOnlyExact,
        PrefixExternalDeclarationIndex::withPermuterm));
    p.setTransformation((tree, root) -> {
      var edi = (PrefixExternalDeclarationIndex<HashSet<DeclarationEntry>, PermutermTrie<HashSet<DeclarationEntry>, DeclarationEntry>>) root.externalDeclarationIndex;
      assertEquals(5, edi.index.prefixQueryFlat("a").count());
      assertEquals(1, edi.index.prefixQueryFlat("ab").count());
      assertEquals(1, edi.index.prefixQueryFlat("aa").count());
      assertEquals(0, edi.index.prefixQueryFlat("abb").count());

      assertEquals(6, edi.index.infixQueryFlat("a").count());
      assertEquals(2, edi.index.infixQueryFlat("bb").count());
      assertEquals(4, edi.index.suffixQueryFlat("a").count());

    });
    p.transform("int a = 1; int a = 1; int aabba = 2; int abc = 3; void amain() {} uniform uni; void babba();");
  }

  @Test
  void testQuerySwapTypeAndInitMemberSet() {
    // test querying after swapping out a TypeAndInitDeclaration that has a
    // different set of declaration members (to test the iterate subtree entries
    // special case)
    p.setRootSupplier(RootSupplier.EXACT_UNORDERED_ED_EXACT);
    p.setTransformation((tree, root) -> {
      var edi = root.externalDeclarationIndex;
      assertTrue(edi.has("a"));
      assertTrue(edi.has("b"));
      assertTrue(edi.has("c"));
      assertEquals(2, edi.get("c").size());

      tree.getChildren().get(0).replaceByAndDelete(p.parseSeparateExternalDeclaration("int a = 1, e = 2, f = 3;"));

      assertTrue(edi.has("a"));
      assertTrue(edi.has("e"));
      assertTrue(edi.has("f"));
      assertFalse(edi.has("b"));
      assertEquals(1, edi.get("c").size());
    });
    p.transform("int a = 1, b = 2, c = 3; int c = 4;");
  }
}
