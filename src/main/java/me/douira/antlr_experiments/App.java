package me.douira.antlr_experiments;

import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.*;

public class App {
  private static enum Input {
    SIMPLE("/input1.glsl"), SHADER("/input2.fsh"), KAPPA("/unlicensed/composite3.fsh");

    String path;

    Input(String path) {
      this.path = path;
    }
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    var selection = Input.KAPPA;
    CharStream input;
    try {
      input = CharStreams.fromStream(App.class.getResourceAsStream(selection.path));
    } catch (Exception e) {
      throw e;
    }
    var lexer = new GLSLLexer(input);
    var commonTokenStream = new CommonTokenStream(lexer);
    var parser = new GLSLParser(commonTokenStream);

    var programContext = parser.program();
    var visitor = new GLSLTreeVisitor(System.out, 3);
    var transformed = visitor.visit(programContext);
    System.out.println(transformed);
  }
}
