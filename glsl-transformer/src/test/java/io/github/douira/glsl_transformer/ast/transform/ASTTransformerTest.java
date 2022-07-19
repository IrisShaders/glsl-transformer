package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.query.*;
import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;
import io.github.douira.glsl_transformer.util.Type;

public class ASTTransformerTest extends TestWithASTTransformer {
  void assertInjectExternalDeclaration(int index, String input, String output) {
    transformer.setTransformation(translationUnit -> {
      translationUnit.children.add(index, transformer.parseNode(
          "int a;",
          translationUnit,
          GLSLParser::externalDeclaration,
          ASTBuilder::visitExternalDeclaration));
    });
    assertEquals(output, transformer.transform(PrintType.COMPACT, input));
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
    transformer.setTransformation(tree -> {
      tree.getRoot().identifierIndex.index.prefixMap("a").values()
          .stream().forEach(Index.<Identifier>iterate(node -> node.name += "b"));
    });
    assertEquals(
        "int ab, ab, c; ",
        transformer.transform(PrintType.COMPACT, "int a, a, c; "));
    assertEquals(
        "int ab = 4 + ab + aab, ab, c; ",
        transformer.transform(PrintType.COMPACT, "int a = 4 + a + aa, a, c; "));
  }

  @Test
  void testNodeQuery() {
    transformer.setTransformation(tree -> {
      tree.getRoot().nodeIndex.get(LiteralExpression.class)
          .stream().forEach(literal -> {
            literal.integerValue++;
          });
    });
    assertEquals(
        "int a = 2, b = 3, c = 4; ",
        transformer.transform(PrintType.COMPACT, "int a = 1, b = 2, c = 3; "));
    assertEquals(
        "int a = 2, 3, 4; ",
        transformer.transform(PrintType.COMPACT, "int a = 1, 2, 3; "));
  }

  @Test
  void testNodeQueryAfterModification() {
    transformer.setTransformation(tree -> {
      Root.indexBuildSession(tree, () -> {
        for (var sequence : tree.getRoot().nodeIndex
            .get(SequenceExpression.class)) {
          sequence.expressions.add(
              new LiteralExpression(Type.INT32, 1));
        }
      });
      tree.getRoot().nodeIndex.get(LiteralExpression.class)
          .stream().forEach(literal -> literal.integerValue++);
    });
    assertEquals(
        "int a = 2, b = 3, c = 4, 2; ",
        transformer.transform(PrintType.COMPACT, "int a = 1, b = 2, c = 3; "));
    assertEquals(
        "int a = 2, 3, 4, 2; ",
        transformer.transform(PrintType.COMPACT, "int a = 1, 2, 3; "));
  }

  @Test
  void testSelfReplacement() {
    transformer.setTransformation(tree -> {
      Root.indexBuildSession(tree, () -> {
        var toReplace = new ArrayList<LiteralExpression>();
        for (var node : tree.getRoot().nodeIndex
            .get(LiteralExpression.class)) {
          if (node.integerValue == 3) {
            toReplace.add(node);
          }
        }
        for (var node : toReplace) {
          var newNode = new ReferenceExpression(new Identifier("foo"));
          node.replaceByAndDelete(newNode);
        }
      });
    });
    assertEquals(
        "int a = 1, b = 2, c = foo; ",
        transformer.transform(PrintType.COMPACT, "int a = 1, b = 2, c = 3; "));
    assertEquals(
        "int a = foo, 2, foo, 5 + foo + b; ",
        transformer.transform(PrintType.COMPACT, "int a = 3, 2, 3, 5 + 3 + b; "));
  }

  @Test
  void testNodeRemovalAndQuery() {
    transformer.setTransformation(tree -> {
      var toRemove = new ArrayList<ReferenceExpression>();
      for (var node : tree.getRoot().nodeIndex
          .get(ReferenceExpression.class)) {
        if (node.getIdentifier().name.equals("a")) {
          toRemove.add(node);
        }
      }
      for (var node : toRemove) {
        node.detachAndDelete();
      }
      assertTrue(tree.getRoot().identifierIndex.get("a").isEmpty());
    });
    assertEquals(
        "int x = b, c, d; ",
        transformer.transform(PrintType.COMPACT, "int x = a, b, c, a, d; "));
  }

  @Test
  void testJobParameters() {
    var jobParameters = new NonFixedJobParameters();
    transformer.setTransformation(tree -> {
      assertEquals(transformer.getJobParameters(), jobParameters);
    });
    transformer.transform(PrintType.COMPACT, "", jobParameters);
  }

  @Test
  void testMoveNodeInternal() {
    transformer.setTransformation((tree, root) -> {
      var firstDeclaration = tree.children.get(0);
      var toMove = new ArrayList<LiteralExpression>();
      for (var node : root.nodeIndex
          .getOne(SequenceExpression.class).expressions) {
        if (!(node instanceof LiteralExpression)) {
          continue;
        }
        var literalExpression = (LiteralExpression) node;
        if (literalExpression.integerValue == 3
            && literalExpression.getAncestor(ExternalDeclaration.class) == firstDeclaration) {
          toMove.add(literalExpression);
        }
      }
      var secondDeclaration = transformer.parseNode(
          "int x = 4, 4;",
          tree,
          GLSLParser::externalDeclaration,
          ASTBuilder::visitExternalDeclaration);
      var sequenceExpression = secondDeclaration.getRoot().nodeIndex
          .get(SequenceExpression.class).stream()
          .filter(e -> e.hasAncestor(secondDeclaration)).findAny().get();
      sequenceExpression.expressions.clear();
      tree.children.add(secondDeclaration);
      for (var node : toMove) {
        node.detach();
        sequenceExpression.expressions.add(node);
      }
    });
    assertEquals(
        "int y = 1, 2, 4, 5; int x = 3, 3; ",
        transformer.transform(PrintType.COMPACT, "int y = 1, 2, 3, 4, 3, 5; "));
  }

  @Test
  void testAddMatchingRootTree() {
    transformer.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.identifierIndex.getOne("bar").replaceByAndDelete(new Identifier("foo"));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar; "));
  }

  // add already registered subtree to tree
  @Test
  void testAddMatchingRootTreeNested() {
    transformer.setTransformation((tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceByAndDelete(
            new ReferenceExpression(new Identifier("foo")));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar;"));
  }

  // add new subtree with different root to tree and register
  @Test
  void testAddNewRootTree() {
    transformer.setTransformation((tree, root) -> {
      Root.indexSeparateTrees(register -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceByAndDelete(
            register.apply(new ReferenceExpression(new Identifier("foo"))));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    });
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar;"));
  }

  // move subtree within tree
  @Test
  void testSubtreeMoveSwap() {
    transformer.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
      ASTNode.swap(
          root.identifierIndex.getOne("bar"),
          root.identifierIndex.getOne("foo"));
      assertEquals(1, root.identifierIndex.get("bar").size());
      assertEquals(1, root.identifierIndex.get("foo").size());
    });
    assertEquals(
        "int x = foo; int y = bar; ",
        transformer.transform(PrintType.COMPACT, "int x = bar; int y = foo;"));
  }

  // delete subtree from tree and unregister
  @Test
  void testSubtreeDeletion() {
    transformer.setTransformation((tree, root) -> {
      assertTrue(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
      root.identifierIndex.getOne("bar")
          .getAncestor(ReferenceExpression.class).detachAndDelete();
      assertFalse(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));
    });
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar, foo;"));
  }

  // move subtree without swapping with another subtree
  @Test
  void testSubtreeMoveWithoutSwap() {
    transformer.setTransformation((tree, root) -> {
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
    assertEquals(
        "int x = foo; int y = hello; ",
        transformer.transform(PrintType.COMPACT, "int x = bar; int y = foo;"));
  }
}
