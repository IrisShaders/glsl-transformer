package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

public abstract class IntegratedTest {
  public static String testCode;
  public CharStream input;
  public GLSLLexer lexer;
  public GLSLParser parser;
  public TokenStream tokenStream;
  public TranslationUnitContext tree;

  public static void readInput(Class<?> caller) {
    testCode = assertDoesNotThrow(
        () -> Files.readString(Paths.get(caller.getResource("/"
            + caller.getSimpleName() + ".glsl").toURI()),
            StandardCharsets.UTF_8));
  }

  @BeforeEach
  void setupParsing() {
    input = CharStreams.fromString(testCode);
    lexer = new GLSLLexer(input);
    tokenStream = new CommonTokenStream(lexer);
    parser = new GLSLParser(tokenStream);
    tree = parser.translationUnit();
  }
}
