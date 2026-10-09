package io.github.douira.glsl_transformer.ast.traversal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.parser.ParseShape;

public class ASTVisitorTest {
  /**
   * Counts the identifiers in the visited tree by aggregating the results of all
   * visited nodes.
   */
  private static class IdentifierCounter extends ASTBaseVisitor<Integer> {
    @Override
    public Integer visitIdentifier(Identifier node) {
      return 1;
    }

    @Override
    public Integer defaultResult() {
      return 0;
    }

    @Override
    public Integer aggregateResult(Integer aggregate, Integer nextResult) {
      return (aggregate == null ? 0 : aggregate) + (nextResult == null ? 0 : nextResult);
    }
  }

  private static int countIdentifiers(String input) {
    return new IdentifierCounter().startVisit(
        ParseShape.TRANSLATION_UNIT._parseNodeSeparateInternal(input));
  }

  @Test
  void testTranslationUnitAggregatesChildren() {
    assertEquals(2, countIdentifiers("int a; int b;"));
    assertEquals(2, countIdentifiers("#version 330 core\nint a; int b;"));
  }

  @Test
  void testTypeSpecifierArraySpecifierVisited() {
    assertEquals(2, countIdentifiers("vec3[N] x;"));
    assertEquals(2, countIdentifiers("sampler2D[N] s;"));
    assertEquals(3, countIdentifiers("S[N] v;"));
    assertEquals(3, countIdentifiers("struct { int a; }[N] t;"));
  }

  @Test
  void testWhileLoopDeclarationVisited() {
    // main, b and N
    assertEquals(3, countIdentifiers("void main() { while (bool b = N) { } }"));
  }
}
