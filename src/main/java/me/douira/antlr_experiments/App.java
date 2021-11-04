package me.douira.antlr_experiments;

import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.*;

public class App {
  private static enum Input {
    TINY("/tiny.glsl"), DIRECTIVE_TEST("/directiveTest.glsl"), SHADER("/shader.glsl"),
    KAPPA("/unlicensed/composite3.glsl");

    String path;

    Input(String path) {
      this.path = path;
    }
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    var selection = Input.TINY;
    CharStream input;

    try {
      input = CharStreams.fromStream(App.class.getResourceAsStream(selection.path));
    } catch (Exception e) {
      throw e;
    }
    var lexer = new GLSLLexer(input);
    var commonTokenStream = new CommonTokenStream(lexer);

    var parser = new GLSLParser(commonTokenStream);

    var translationUnitContext = parser.translationUnit();

    // var debugVisitor = new DebugVisitor();
    // var transformed = debugVisitor.visit(translationUnitContext);
    // System.out.println(transformed);

    translationUnitContext.children.add(new ReplacementNode("foo"));

    var printVisitor = new PrintVisitor(commonTokenStream);
    System.out.println(printVisitor.visitAndJoin(translationUnitContext));

    var tokens = commonTokenStream.getTokens();
    for (var token : tokens) {
      System.out.println(token);
    }
  }
}
