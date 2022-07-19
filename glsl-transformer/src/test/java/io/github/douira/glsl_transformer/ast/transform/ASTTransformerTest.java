package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.query.*;
import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;
import io.github.douira.glsl_transformer.util.Type;

/**
 * TODO: scenarios
 * add already registered subtree to tree
 * 
 * add new subtree with different root to tree and register
 * 
 * move subtree within tree
 * 
 * delete subtree from tree and unregister
 * 
 * detach subtree from tree without unregistering for later insertion?
 * 
 */
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
          node.replaceBy(newNode);
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
        node.deleteFromParent();
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
    transformer.setTransformation(ASTTransformer.wrapTransformation(transformer, (tree, root) -> {
      var firstDeclaration = tree.children.get(0);
      var toMove = new ArrayList<LiteralExpression>();
      for (var node : root.nodeIndex
          .getOne(SequenceExpression.class).expressions) {
        if (!(node instanceof LiteralExpression)) {
          continue;
        }
        var literalExpression = (LiteralExpression) node;
        if (literalExpression.integerValue == 3
            && literalExpression.getFirstParentOfType(ExternalDeclaration.class) == firstDeclaration) {
          toMove.add(literalExpression);
        }
      }
      var secondDeclaration = transformer.parseNode(
          "int x = 4, 4;",
          tree,
          GLSLParser::externalDeclaration,
          ASTBuilder::visitExternalDeclaration);
      tree.children.add(secondDeclaration);
      //TODO: find a better way of selecting the sequence expression of the second declaration
      var sequenceExpression = secondDeclaration.getRoot().nodeIndex
          .getOne(SequenceExpression.class);
      sequenceExpression.expressions.clear();
      sequenceExpression.expressions.addAll(toMove);
    }));
    assertEquals(
        "int y = 1, 2, 4, 5; int x = 3, 3; ",
        transformer.transform(PrintType.COMPACT, "int y = 1, 2, 3, 4, 3, 5; "));
  }

  @Test
  void testAddMatchingRootTree() {
    transformer.setTransformation(ASTTransformer.wrapTransformation(transformer, (tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.identifierIndex.getOne("bar").replaceBy(new Identifier("foo"));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    }));
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar; "));
  }

  @Test
  void testAddMatchingRootTreeNested() {
    transformer.setTransformation(ASTTransformer.wrapTransformation(transformer, (tree, root) -> {
      Root.indexBuildSession(tree, () -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceBy(
            new ReferenceExpression(new Identifier("foo")));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    }));
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar;"));
  }

  @Test
  void testAddNewRootTree() {
    transformer.setTransformation(ASTTransformer.wrapTransformation(transformer, (tree, root) -> {
      Root.indexSeparateTrees(register -> {
        assertTrue(root.identifierIndex.has("bar"));
        root.nodeIndex.getOne(ReferenceExpression.class).replaceBy(
            register.apply(new ReferenceExpression(new Identifier("foo"))));
        assertFalse(root.identifierIndex.has("bar"));
        assertTrue(root.identifierIndex.has("foo"));
      });
    }));
    assertEquals(
        "int x = foo; ",
        transformer.transform(PrintType.COMPACT, "int x = bar;"));
  }

  @Test
  void testMoveSubtreeInternal() {
    transformer.setTransformation(ASTTransformer.wrapTransformation(transformer, (tree, root) -> {
      assertTrue(root.identifierIndex.has("bar"));
      assertTrue(root.identifierIndex.has("foo"));

      var bar = root.identifierIndex.getOne("bar");
      var foo = root.identifierIndex.getOne("foo");
      var prevFooParent = (ReferenceExpression) foo.getParent();
      prevFooParent.setIdentifier(null);
      bar.replaceBy(foo);
      prevFooParent.setIdentifier(bar);
      assertEquals(1, root.identifierIndex.get("bar").size());
      assertEquals(1, root.identifierIndex.get("foo").size());
    }));
    assertEquals(
        "int x = foo; int y = bar; ",
        transformer.transform(PrintType.COMPACT, "int x = bar; int y = foo;"));
  }
}
