package io.github.douira.glsl_transformer.ast.node;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class IdentifierTest extends TestWithSingleASTTransformer {
  private void assertIdentifierThrows(String name) {
    assertThrows(IllegalArgumentException.class, () -> new Identifier(name));
  }

  @Test
  void testValidation() {
    assertDoesNotThrow(() -> {
      new Identifier("a");
      new Identifier("A");
      new Identifier("Z");
      new Identifier("z");
      new Identifier("z0");
      new Identifier("z5");
      new Identifier("z9");
      new Identifier("z54378391203432473296");
      new Identifier("_");
      new Identifier("_fdsaf6ds6");
      new Identifier("_fdsaf6ds");
    });
    assertIdentifierThrows("");
    assertIdentifierThrows("543gd_fdsafds");
    assertIdentifierThrows("453");
    assertIdentifierThrows("453_");
    assertIdentifierThrows("0");
    assertIdentifierThrows("9");
    assertIdentifierThrows("453_gfdgfd");
    assertIdentifierThrows("fdsfds.fdsfds");
    assertIdentifierThrows("fdsfds.5435");
    assertIdentifierThrows("fds,fds");
    assertIdentifierThrows("fds$fds");
    assertIdentifierThrows("fds=fds");
    assertIdentifierThrows("fds fds");
    assertIdentifierThrows("fds-fds");
    assertIdentifierThrows("fds-fds");

    p.setTransformation((tu, root) -> {
      root.identifierIndex.rename("foo", "int i = 4;");
    });
    assertThrows(IllegalArgumentException.class, () -> p.transform("int foo = 4;"));
  }
}
