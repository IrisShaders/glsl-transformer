package me.douira.antlr_experiments;

import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class App {
  private static enum Input {
    TINY("/tiny.glsl"), DIRECTIVE_TEST("/directiveTest.glsl"), SHADER("/shader.glsl"),
    KAPPA("/unlicensed/composite3.glsl"), BENCHMARK1("/unlicensed/benchmark1.glsl"), BENCHMARK2("/unlicensed/benchmark2.glsl"), TEST(
        "/unlicensed/test.glsl");

    String path;

    Input(String path) {
      this.path = path;
    }
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    var selection = Input.TEST;
    CharStream input;

    try {
      input = CharStreams.fromStream(App.class.getResourceAsStream(selection.path));
    } catch (Exception e) {
      throw e;
    }
    var lexer = new GLSLLexer(input);
    var commonTokenStream = new CommonTokenStream(lexer);

    var startNanos = System.nanoTime();
    var parser = new GLSLParser(commonTokenStream);
    var translationUnitContext = parser.translationUnit();
    System.out.println("parsing took " + (System.nanoTime() - startNanos) / 1e6 + " ms.");

    new DebugVisitor().visit(translationUnitContext);
    
    System.out.println(translationUnitContext.toInfoString(parser));

    var editContext = new EditContext();
    translationUnitContext.children.add(2, new StringNode("\nexample declaration;"));
    System.out.println(PrintVisitor.printTree(commonTokenStream, translationUnitContext));
    ParseTreeWalker.DEFAULT.walk(new TransformationVisitor(editContext), translationUnitContext);
    editContext.finishEditing();

    startNanos = System.nanoTime();
    var printResult = PrintVisitor.printTree(commonTokenStream, translationUnitContext, editContext);
    System.out.println("printing took " + (System.nanoTime() - startNanos) / 1e6 + " ms.");
    System.out.println(printResult);

    var tokens = commonTokenStream.getTokens();
    for (var token : tokens) {
      System.out.println(token);
    }
  }
}
