package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.transform.TransformationManager;

public class ASTIntegrationTest {
  public void assertReprint(String expected, String input) {
    var manager = new TransformationManager<>();
    var ast = ASTBuilder.build(manager.parse(input));
    assertEquals(expected, ASTPrinter.printSimple(ast));
  }

  public void assertReprint(String input) {
    assertReprint(input, input);
  }

  @Test
  public void testASTIntegration() {
    assertReprint("#version 330 core\n;");
    // assertReprint("#pragma STDGL debug(on)\n");
  }
}
