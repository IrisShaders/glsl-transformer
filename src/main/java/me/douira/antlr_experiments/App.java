package me.douira.antlr_experiments;

import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;

public class App {
  private static enum Input {
    SIMPLE("/input1.glsl"), SHADER("/input2.fsh");

    String path;

    Input(String path) {
      this.path = path;
    }
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    var selection = Input.SIMPLE;
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
    var visitor = new GLSLTreeVisitor(System.out);
    visitor.visit(programContext);
    System.out
        .println(input.getText(new Interval(programContext.start.getStartIndex(), programContext.stop.getStopIndex())));
  }
}
