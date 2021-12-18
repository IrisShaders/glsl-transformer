package io.github.douira.glsl_transformer.transform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

public class TransformationPhaseTest {
  class TestPhase extends TransformationPhase {
  }

  CharStream input;
  GLSLLexer lexer;
  GLSLParser parser;
  TokenStream tokenStream;
  TranslationUnitContext tree;

  @BeforeEach
  void setup() {
    input = CharStreams.fromString("");
    lexer = new GLSLLexer(input);
    tokenStream = new CommonTokenStream(lexer);
    parser = new GLSLParser(tokenStream);
    tree = parser.translationUnit();
  }

  @Test
  void testCompilePath() {

  }

  @Test
  void testCompilePattern() {

  }

  @Test
  void testCreateLocalRoot() {

  }

  @Test
  void testFindAndMatch() {

  }

  @Test
  void testGetParser() {

  }

  @Test
  void testGetSiblings() {

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
    TestPhase phase = new TestPhase();
    var collector = new PhaseCollector(parser);
    phase.setParent(collector);

    assertEquals(phase.getParser(), parser, "It should return the previously set parser inside the phase collector");
  }
}
