package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTIntegrationTest {
  public void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String expected,
      String input) {
    var manager = new TransformationManager<>();
    var parseTree = manager.parse(input, parseMethod);
    var ast = ASTBuilder.build(parseTree);
    var reprinted = ASTPrinter.printSimple(ast);
    assertEquals(expected, reprinted);
  }

  public void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String input) {
    assertReprint(parseMethod, input, input);
  }

  public void assertReprint(String expected, String input) {
    assertReprint(GLSLParser::translationUnit, expected, input);
  }

  public void assertReprint(String input) {
    assertReprint(input, input);
  }

  public void assertReprintExpression(String expected, String input) {
    assertReprint(GLSLParser::expression, expected, input);
  }

  public void assertReprintExpression(String input) {
    assertReprintExpression(input, input);
  }

  @Test
  public void testASTIntegration() {
    assertReprint(";\n");
    assertReprint("");
    assertReprint("#version 330 core\n;\n");
    assertReprint("#pragma STDGL debug(on)\n");
    assertReprint("#pragma optimize(off)\n");
    assertReprint("#pragma invariant(all)\n");
    assertReprint("#pragma foobar\n");
    assertReprint("#extension foobar: require\n");
    assertReprint("#extension foobar: enable\n");
    assertReprint("#extension foobar: warn\n");
    assertReprint("#extension foobar: disable\n");
    assertReprintExpression("1 + 2");
    assertReprintExpression("1 | 2");
    assertReprintExpression("((a + b) * c)");
    assertReprintExpression("true ? 1.0 : bar.length()");
    assertReprintExpression("a, b, c");
    assertReprintExpression("(a, b, c, d)");
    assertReprintExpression(
        "1 + 2us + 3ul + 4u + 5s + 0.1 + 0.2 + 0.3hf + 0.4lf",
        "1 + 2us + 3ul + 4u + 5s + 0.1 + 0.2f + 0.3hf + 0.4lf");
  }
}
