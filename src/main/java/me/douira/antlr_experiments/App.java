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
    var selection = Input.SHADER;
    CharStream input;

    try {
      input = CharStreams.fromStream(App.class.getResourceAsStream(selection.path));
    } catch (Exception e) {
      throw e;
    }
    var lexer = new GLSLLexer(input);
    var commonTokenStream = new CommonTokenStream(lexer);

    var parser = new GLSLParser(commonTokenStream);

    var TUContext = parser.translationUnit();
    var visitor = new GLSLTreeVisitor(System.out);
    var transformed = visitor.visit(TUContext);
    System.out.println(transformed);

    var tokens = commonTokenStream.getTokens();
    for (var token : tokens) {
      System.out.println(token.getChannel() + " " + token.getType() + "\t" + token.getText());
    }
  }
}
