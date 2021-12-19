package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.IntegratedTest;

public class TransformationPhaseTest extends IntegratedTest {
  class TestPhase extends TransformationPhase {
  }

  TestPhase phase;
  PhaseCollector collector;

  @BeforeAll
  static void setupInput() {
    readInput(TransformationPhaseTest.class);
  }

  @BeforeEach
  void setup() {
    collector = new PhaseCollector(parser);
    phase = new TestPhase();
    phase.setParent(collector);
  }

  @Test
  void testCompilePath() {
    var path = phase.compilePath("/translationUnit/externalDeclaration");
    assertEquals(path.evaluate(tree).size(), tree.getChildCount() - 2, "It should compile a functioning xpath");
  }

  @Test
  void testCompilePattern() {
    var pattern = phase.compilePattern("varying <type:typeSpecifier> varyVec;", GLSLParser.RULE_externalDeclaration);
    var match = pattern.match(tree.getChild(5));
    assertTrue(match.succeeded(), "It should compile a functioning pattern");
    assertEquals("vec2", match.get("type").getText(), "It should compile a functioning pattern");
  }

  @Test
  void testCreateLocalRoot() {

  }

  @Test
  void testFindAndMatch() {

  }

  @Test
  void testGetSiblings() {
    assertEquals(tree.children, TransformationPhase.getSiblings(tree.versionStatement()),
        "It should find the siblings of a node");
  }

  @Test
  void testInjectExternalDeclaration() {

  }

  @Test
  void testInjectNode() {

  }

  @Test
  void testIsActive() {
    TestPhase phase = new TestPhase();
    assertTrue(phase.isActive(), "It should always be active");
  }

  @Test
  void testRemoveNode() {

  }

  @Test
  void testReplaceNode() {

  }

  @Test
  void testSetParent() {
    phase.setParent(collector);

    assertEquals(parser, phase.getParser(), "It should return the previously set parser inside the phase collector");
  }
}
