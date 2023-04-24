package io.github.douira.glsl_transformer.ast.typing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.parser.ParseShape;

public class TypeAnalyzerTest {
  @Disabled
  @Test
  public void testTypeAnalysis() {
    var tu = ParseShape.TRANSLATION_UNIT._parseNodeSeparateInternal("void main() {}");
    assertNotNull(tu);
    var analyzer = new TypeAnalyzer();
    analyzer.analyze(tu);
    assertDoesNotThrow(() -> tu.getType());
  }
}
