package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class AssertUtil {
  public static void assertRun(String message) {
    assertTrue(true, message);
  }

  public static void assertReprint(
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String expected,
      String input) {
    assertReprint(PrintType.INDENTED, parseMethod, expected, input);
  }

  public static void assertReprint(
      PrintType printType,
      Function<GLSLParser, ? extends ExtendedContext> parseMethod,
      String expected,
      String input) {
    var parser = new EnhancedParser();
    var parseTree = parser.parse(input, parseMethod);
    var ast = ASTBuilder.build(parseTree);
    var reprinted = ASTPrinter.print(printType, ast);
    assertEquals(expected, reprinted);
  }

  public static void assertQuery(Set<Object> expected, Stream<Object> result) {
    assertEquals(expected, result.collect(Collectors.toSet()));
  }
}
