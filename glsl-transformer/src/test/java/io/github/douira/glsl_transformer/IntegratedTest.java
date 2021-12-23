package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.PhaseCollector;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

public abstract class IntegratedTest {
  public String testCode;
  public CharStream input;
  public GLSLLexer lexer;
  public GLSLParser parser;
  public BufferedTokenStream tokenStream;
  public TranslationUnitContext tree;

  public static String testResourceInput;
  public static void readInput(Class<?> caller) {
    testResourceInput = assertDoesNotThrow(
        () -> Files.readString(Paths.get(caller.getResource("/"
            + caller.getSimpleName() + ".glsl").toURI()),
            StandardCharsets.UTF_8));
  }

  @BeforeEach
  public void setupParsing() {
    setupParsingWith(testResourceInput);
  }

  public void setupParsingWith(String code) {
    testCode = code;
    input = CharStreams.fromString(testCode);
    lexer = new GLSLLexer(input);
    tokenStream = new CommonTokenStream(lexer);
    parser = new GLSLParser(tokenStream);
    tree = parser.translationUnit();
  }

  public void wrapRunTransform(TransformationPhase phase) {
    var transformation = new Transformation() {
      @Override
      protected void createPhases() {
        addPhase(phase);
      }
    };
    var collector = new PhaseCollector(parser);
    collector.registerTransformation(transformation);
    collector.transformTree(tree, tokenStream);
  }
}
