package io.github.douira.glsl_transformer.ast.node.abstract_node;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.douira.glsl_transformer.ast.node.type.specifier.ArraySpecifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

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

  // each of these contains the identifier N in a position that the default
  // traversal used to skip (array specifiers of type specifiers and declarations
  // in while loop conditions)
  private static final String NESTED_IDENTIFIER_MESSAGE = "The identifier should be registered after parsing";

  @ParameterizedTest
  @ValueSource(strings = {
      "vec3[N] x;",
      "sampler2D[N] s;",
      "S[N] v;",
      "struct { int a; }[N] t;",
      "void main() { while (bool b = N) { } }" })
  void testUnregisterReachesAllChildren(String input) {
    var tu = p.parseSeparateTranslationUnit(input);
    var root = tu.getRoot();
    assertTrue(root.identifierIndex.has("N"), NESTED_IDENTIFIER_MESSAGE);
    tu.getChildren().get(0).detachAndDelete();
    assertFalse(root.identifierIndex.has("N"),
        "It should unregister identifiers nested in the deleted subtree");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "vec3[N] x;",
      "sampler2D[N] s;",
      "S[N] v;",
      "struct { int a; }[N] t;",
      "void main() { while (bool b = N) { } }" })
  void testChangeRootReachesAllChildren(String input) {
    var tu = p.parseSeparateTranslationUnit(input);
    var root = tu.getRoot();
    var other = p.parseSeparateTranslationUnit(";");
    var otherRoot = other.getRoot();
    assertNotSame(root, otherRoot);

    var identifier = root.identifierIndex.getOne("N");
    var declaration = tu.getChildren().get(0);
    declaration.detach();
    other.getChildren().add(declaration);

    assertSame(otherRoot, identifier.getRoot(),
        "It should change the root of identifiers nested in the moved subtree");
    assertTrue(otherRoot.identifierIndex.has("N"));
    assertFalse(root.identifierIndex.has("N"));
  }

  @Test
  void testListenerReachesTypeSpecifierArraySpecifier() {
    var counter = new AtomicInteger();
    ASTWalker.walk(new ASTListener() {
      @Override
      public void enterArraySpecifier(ArraySpecifier node) {
        counter.incrementAndGet();
      }
    }, p.parseSeparateTranslationUnit("vec3[N] x; S[N] v; sampler2D[N] s; struct { int a; }[N] t;"));
    assertEquals(4, counter.get());
  }
}
