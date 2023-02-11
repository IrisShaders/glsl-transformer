package io.github.douira.glsl_transformer.ast.node.abstract_node;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.FunctionDefinition;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class ASTNodeTest extends TestWithSingleASTTransformer {
  @Test
  void testMoveInParent() {
    p.setTransformation((tree, root) -> {
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

      // assert that b has a setter for the right side
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
    p.transform("int x = a + b;");
  }

  @Test
  void testMoveInParentSimple() {
    p.setTransformation((tree, root) -> {
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
    p.transform("int x = a + b;");
  }

  @Test
  void testUnregisterEmptyReturn() {
    p.setTransformation((tree, root) -> {
      root.nodeIndex.getOne(FunctionDefinition.class).detachAndDelete();
    });
    assertDoesNotThrow(() -> p.transform("void main() { return; }"),
        "It should not throw when unregistering a null member (null expression in return statement)");
  }

  @Test
  void testChangeRootEmptyReturn() {
    p.setTransformation((tree, root) -> {
      var def = root.nodeIndex.getOne(FunctionDefinition.class);
      def.detach();
      p.parseTranslationUnit(root, ";").getChildren().add(def);
    });
    assertDoesNotThrow(() -> p.transform("void main() { return; }"),
        "It should not throw when changing the root of a null member (null expression in return statement)");
  }
}
