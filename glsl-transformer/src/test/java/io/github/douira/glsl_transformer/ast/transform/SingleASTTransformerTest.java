package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.query.index.PrefixIdentifierIndex;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;
import io.github.douira.glsl_transformer.util.Type;

public class SingleASTTransformerTest extends TestWithSingleASTTransformer {
  void assertInjectExternalDeclaration(int index, String input, String output) {
    p.setTransformation(translationUnit -> {
      translationUnit.getChildren().add(index, p.parseExternalDeclaration(
          translationUnit, "int a;"));
    });
    assertTransform(output, input);
  }

  @Test
  void testInsertion() {
    assertInjectExternalDeclaration(0,
        "int b; int c; ",
        "int a; int b; int c; ");
    assertInjectExternalDeclaration(1,
        "int b; int c;",
        "int b; int a; int c; ");
    assertInjectExternalDeclaration(2,
        "int b; int c;",
        "int b; int c; int a; ");
  }

  @Test
  void testIdentifierQuery() {
    Root.identifierIndexFactory = PrefixIdentifierIndex::withPrefix;
    p.setTransformation((tree, root) -> {
      root.getPrefixIdentifierIndex().prefixQueryFlat("a").collect(Collectors.toList()).forEach(
          node -> node.setName(node.getName() + "b"));
    });
    assertTransform(
        "int ab, ab, c; ",
        "int a, a, c; ");
    assertTransform(
        "int ab = 4 + ab + aab, ab, c; ",
        "int a = 4 + a + aa, a, c; ");
    Root.resetRootFactories();
  }

  @Test
  void testNodeQuery() {
    p.setTransformation((tree, root) -> {
      root.nodeIndex.getStream(LiteralExpression.class)
          .forEach(literal -> literal.changeInteger(literal.getInteger() + 1));
    });
    assertTransform(
        "int a = 2, b = 3, c = 4; ",
        "int a = 1, b = 2, c = 3; ");
    assertTransform(
        "int a = 2, 3, 4; ",
        "int a = 1, 2, 3; ");
  }

  @Test
  void testNodeQueryAfterModification() {
    p.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        for (var sequence : root.nodeIndex
            .get(SequenceExpression.class)) {
          sequence.getExpressions().add(
              new LiteralExpression(Type.INT32, 1));
        }
      });
      root.nodeIndex.getStream(LiteralExpression.class)
          .forEach(literal -> literal.changeInteger(literal.getInteger() + 1));
    });
    assertTransform(
        "int a = 2, b = 3, c = 4, 2; ",
        "int a = 1, b = 2, c = 3; ");
    assertTransform(
        "int a = 2, 3, 4, 2; ",
        "int a = 1, 2, 3; ");
  }

  @Test
  void testSelfReplacement() {
    p.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        var toReplace = new ArrayList<LiteralExpression>();
        for (var node : root.nodeIndex
            .get(LiteralExpression.class)) {
          if (node.getInteger() == 3) {
            toReplace.add(node);
          }
        }
        for (var node : toReplace) {
          var newNode = new ReferenceExpression(new Identifier("foo"));
          node.replaceByAndDelete(newNode);
        }
      });
    });
    assertTransform(
        "int a = 1, b = 2, c = foo; ",
        "int a = 1, b = 2, c = 3; ");
    assertTransform(
        "int a = foo, 2, foo, 5 + foo + b; ",
        "int a = 3, 2, 3, 5 + 3 + b; ");
  }

  @Test
  void testNodeRemovalAndQuery() {
    p.setTransformation((tree, root) -> {
      var toRemove = new ArrayList<ReferenceExpression>();
      for (var node : root.nodeIndex
          .get(ReferenceExpression.class)) {
        if (node.getIdentifier().getName().equals("a")) {
          toRemove.add(node);
        }
      }
      for (var node : toRemove) {
        node.detachAndDelete();
      }
      assertTrue(root.identifierIndex.get("a").isEmpty());
    });
    assertTransform(
        "int x = b, c, d; ",
        "int x = a, b, c, a, d; ");
  }

  @Test
  void testJobParameters() {
    var jobParameters = JobParameters.EMPTY;
    p.setTransformation((tree, root) -> {
      assertEquals(p.getJobParameters(), jobParameters);
    });
    p.setPrintType(PrintType.COMPACT);
    p.transform("", jobParameters);
  }

  @Test
  void testMoveNodeInternal() {
    p.setTransformation((tree, root) -> {
      var firstDeclaration = tree.getChildren().get(0);
      var toMove = new ArrayList<LiteralExpression>();
      for (var node : root.nodeIndex
          .getOne(SequenceExpression.class).getExpressions()) {
        if (node instanceof LiteralExpression literalExpression) {
          if (literalExpression.getInteger() == 3
              && literalExpression.getAncestor(ExternalDeclaration.class) == firstDeclaration) {
            toMove.add(literalExpression);
          }
        } else {
          continue;
        }
      }
      var secondDeclaration = p.parseExternalDeclaration(tree, "int x = 4, 4;");
      var sequenceExpression = secondDeclaration.getRoot().nodeIndex
          .getStream(SequenceExpression.class)
          .filter(e -> e.hasAncestor(secondDeclaration)).findAny().get();
      sequenceExpression.getExpressions().clear();
      tree.getChildren().add(secondDeclaration);
      for (var node : toMove) {
        node.detach();
        sequenceExpression.getExpressions().add(node);
      }
    });
    assertTransform(
        "int y = 1, 2, 4, 5; int x = 3, 3; ",
        "int y = 1, 2, 3, 4, 3, 5; ");
  }

  @Test
  void testAddMatchingRootTree() {
    p.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.identifierIndex.getOne("bar").replaceByAndDelete(new Identifier("foo"));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertTransform(
        "int x = foo; ",
        "int x = bar; ");
  }

  // add already registered subtree to tree
  @Test
  void testAddMatchingRootTreeNested() {
    p.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceByAndDelete(
            new ReferenceExpression(new Identifier("foo")));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertTransform(
        "int x = foo; ",
        "int x = bar;");
  }

  // add new subtree with different root to tree and register
  @Test
  void testAddNewRootTree() {
    p.setTransformation((tree, root) -> {
      Root.indexSeparateTrees(register -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceByAndDelete(
            register.apply(new ReferenceExpression(new Identifier("foo"))));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertTransform(
        "int x = foo; ",
        "int x = bar;");
  }

  // move subtree within tree
  @Test
  void testSubtreeMoveSwap() {
    p.setTransformation((tree, root) -> {
      assertEquals(1, root.identifierIndex.get("bar").size());
      assertEquals(1, root.identifierIndex.get("foo").size());
      ASTNode.swap(
          root.identifierIndex.getOne("bar"),
          root.identifierIndex.getOne("foo"));
      assertEquals(1, root.identifierIndex.get("bar").size());
      assertEquals(1, root.identifierIndex.get("foo").size());
    });
    assertTransform(
        "int x = foo; int y = bar; ",
        "int x = bar; int y = foo;");
  }

  // delete subtree from tree and unregister
  @Test
  void testSubtreeDeletion() {
    p.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
      root.identifierIndex.getOne("bar")
          .getAncestor(ReferenceExpression.class).detachAndDelete();
      assertFalse(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
    });
    assertTransform(
        "int x = foo; ",
        "int x = bar, foo;");
  }

  // move subtree without swapping with another subtree
  @Test
  void testSubtreeMoveWithoutSwap() {
    p.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
      var foo = root.identifierIndex.getOne("foo")
          .getAncestor(ReferenceExpression.class);
      Root.indexBuildSession(tree, () -> {
        foo.replaceBy(new ReferenceExpression(new Identifier("hello")));
      });
      root.identifierIndex.getOne("bar")
          .getAncestor(ReferenceExpression.class)
          .replaceByAndDelete(foo);
      assertEquals(1, root.identifierIndex.get("hello").size());
      assertEquals(1, root.identifierIndex.get("foo").size());
      assertTrue(root.identifierIndex.get("bar").isEmpty());
    });
    assertTransform(
        "int x = foo; int y = hello; ",
        "int x = bar; int y = foo;");
  }
}
