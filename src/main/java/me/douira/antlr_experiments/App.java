package me.douira.antlr_experiments;

import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

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

    var editContext = new EditContext();
    translationUnitContext.children.add(2, new StringNode("\nexample declaration;"));
    System.out.println(PrintVisitor.printTree(commonTokenStream, translationUnitContext));
    ParseTreeWalker.DEFAULT.walk(new TransformationVisitor(editContext), translationUnitContext);
    editContext.finishEditing();

    System.out.println(PrintVisitor.printTree(commonTokenStream, translationUnitContext, editContext));

    var tokens = commonTokenStream.getTokens();
    for (var token : tokens) {
      System.out.println(token);
    }
  }
}
