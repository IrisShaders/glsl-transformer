package io.github.douira.glsl_transformer.ast.node.basic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.test_util.TestWithASTTransformer;

public class ASTNodeTest extends TestWithASTTransformer {
  @Test
  void testMoveInParent() {
    t.setTransformation((tree, root) -> {
      var a = root.identifierIndex.getOneReferenceExpression("a");
      var b = root.identifierIndex.getOneReferenceExpression("b");
      assertEquals(a.getParent(), b.getParent());
      var parent = (AdditionExpression) a.getParent();
      assertEquals(a, parent.getLeft());
      assertEquals(b, parent.getRight());

      // assert that a has a setter for the left side
      a.replaceBy(b);
      assertEquals(b, parent.getLeft());
      assertEquals(b, parent.getRight());
      parent.setLeft(a);
      parent.setRight(b);
      assertEquals(a, parent.getLeft());
      assertEquals(b, parent.getRight());

      // assert that b has a settter for the right side
      b.replaceBy(a);
      assertEquals(a, parent.getLeft());
      assertEquals(a, parent.getRight());
      parent.setRight(b);

      // assert that switching internally works
      parent.setLeft(b);
      parent.setRight(a);
      assertEquals(b, parent.getLeft());
      assertEquals(a, parent.getRight());
    });
    t.transform("int x = a + b;");
  }

  @Test
  void testMoveInParentSimple() {
    t.setTransformation((tree, root) -> {
      var a = root.identifierIndex.getOneReferenceExpression("a");
      var b = root.identifierIndex.getOneReferenceExpression("b");
      assertEquals(a.getParent(), b.getParent());
      var parent = (AdditionExpression) a.getParent();
      assertEquals(a, parent.getLeft());
      assertEquals(b, parent.getRight());

      // assert that switching internally works
      parent.setLeft(b);
      parent.setRight(a);
      assertEquals(b, parent.getLeft());
      assertEquals(a, parent.getRight());

      parent.setLeft(a);
      assertEquals(a, parent.getLeft());
      assertEquals(a, parent.getRight());
    });
    t.transform("int x = a + b;");
  }
}
