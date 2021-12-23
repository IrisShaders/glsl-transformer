package io.github.douira.glsl_transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.Tensor;
import io.github.douira.glsl_transformer.generic.PrintVisitor;
import io.github.douira.glsl_transformer.iris.ComplexTransformations;
import io.github.douira.glsl_transformer.transform.PhaseCollector;

@Disabled
public class Demo {
  private static enum Input {
    TINY("/tiny.glsl"),
    DIRECTIVE_TEST("/directiveTest.glsl"),
    SHADER("/shader.glsl"),
    KAPPA("/unlicensed/composite3.glsl"),
    BENCHMARK1("/unlicensed/benchmark1.glsl"),
    BENCHMARK2("/unlicensed/benchmark2.glsl"),
    TEST("/unlicensed/test.glsl"),
    TYPE_TEST("/typeTest.glsl");

    String path;

    Input(String path) {
      this.path = path;
    }
  }

  private static Set<String> bannedFilenameFragments = Set.of("ray", "preprocessor");

  @Test
  public void main() throws IOException, URISyntaxException {
    var tensor = Tensor.parseFromTokenType(GLSLLexer.F16MAT2X2);
    var compactName = tensor.getCompactName();

    processInput(Input.TYPE_TEST);
    processDirectory("/glslang-test");
  }

  private static void processDirectory(String path) throws IOException, URISyntaxException {
    Files.walk(Paths.get(Demo.class.getResource(path).toURI())).filter(file -> {
      var fileName = file.getFileName().toString().toLowerCase();
      return !bannedFilenameFragments.stream().anyMatch(banned -> fileName.contains(banned));
    }).map(Path::toFile).forEach(file -> {
      if (file.isDirectory()) {
        return;
      }
      System.out.println("Processing file " + file.getName());
      try {
        processFile(file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      System.out.println();
    });
  }

  private static void processInput(Input input) throws IOException {
    processFile(Demo.class.getResource(input.path).openStream());
  }

  private static void processFile(File file) throws IOException {
    processFile(new FileInputStream(file));
  }

  private static void processFile(InputStream inputStream) throws IOException {
    var input = CharStreams.fromStream(inputStream);
    inputStream.close();

    var lexer = new GLSLLexer(input);
    var commonTokenStream = new CommonTokenStream(lexer);

    var startNanos = System.nanoTime();
    var parser = new GLSLParser(commonTokenStream);
    var tree = parser.translationUnit();
    System.out.println("parsing took " + (System.nanoTime() - startNanos) / 1e6 + " ms.");

    // new DebugVisitor().visit(tree);

    // before any edits
    // System.out.println(PrintVisitor.printTree(commonTokenStream, tree));

    // var tokens = commonTokenStream.getTokens();
    // for (var token : tokens) {
    // System.out.println(token);
    // }

    var transformer = new PhaseCollector(parser);
    transformer.registerTransformationMultiple(ComplexTransformations::registerWith);

    transformer.transformTree(tree, commonTokenStream);

    // after edits
    // startNanos = System.nanoTime();
    // var printResult = PrintVisitor.printTree(commonTokenStream, tree);
    // System.out.println("printing took " + (System.nanoTime() - startNanos) / 1e6
    // + " ms.");
    // System.out.println(printResult);
  }
}
