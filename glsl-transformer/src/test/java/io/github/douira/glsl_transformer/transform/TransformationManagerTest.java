package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class TransformationManagerTest {
  TransformationManager manager;

  @BeforeEach
  void setup() {
    manager = new TransformationManager();
    manager.registerTransformation(new Transformation(new RunPhase() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        injectExternalDeclaration("//thing\n;", InjectionPoint.BEFORE_VERSION);
      }
    }));
  }

  @Test
  void testGetLexer() {
    assertNotNull(manager.getParser(), "It should have a parser");
  }

  @Test
  void testGetParser() {
    assertNotNull(manager.getParser(), "It should have a lexer");
  }

  @Test
  void testTransform() {
    assertEquals(
        "//present\n//thing\n;",
        manager.transform("//present\n"));
  }

  @Test
  void testTransformStream() {
    assertEquals(
        "//present\n//thing\n;",
        manager.transformStream(CharStreams.fromString("//present\n")));
  }
}
