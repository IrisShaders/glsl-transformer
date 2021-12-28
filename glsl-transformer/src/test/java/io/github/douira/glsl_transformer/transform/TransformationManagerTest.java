package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class TransformationManagerTest {
  TransformationManager manager;
  Exception storeException;

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

  void assertParseErrorType(Class<? extends RecognitionException> type, Executable executable, String message) {
    assertThrows(ParseCancellationException.class, () -> {
      try {
        executable.execute();
      } catch (ParseCancellationException exception) {
        storeException = exception;
        throw exception;
      }
    });
    assertSame(type, storeException.getCause().getClass(),
        "It should throw a ParseCancellationException with the cause " + type.getSimpleName());
  }

  @Test
  void testTransform() {
    assertEquals(
        "//present\n//thing\n;",
        manager.transform("//present\n"));

    assertParseErrorType(
        InputMismatchException.class, () -> manager.transform(
            "//present"),
        "It should throw on an incomplete input");
    assertParseErrorType(
        NoViableAltException.class, () -> manager.transform(
            "foo"),
        "It should throw when there is no viable alternative while parsing the input");
    assertParseErrorType(
        LexerNoViableAltException.class, () -> manager.transform(
            "§"),
        "It should throw when there is no viable alternative while tokenizing the input");

    // FailedPredicateException is difficult to test and may never actually occur
  }

  @Test
  void testTransformStream() {
    assertEquals(
        "//present\n//thing\n;",
        manager.transformStream(CharStreams.fromString("//present\n")));
  }
}
