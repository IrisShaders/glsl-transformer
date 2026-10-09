package io.github.douira.glsl_transformer.test_util;

import io.github.douira.glsl_transformer.ast.node.abstract_node.*;
import io.github.douira.glsl_transformer.ast.traversal.ASTListener;
import io.github.douira.glsl_transformer.ast.typing.*;

/**
 * Prints the AST like {@link PrintAST} but annotates each node that has a type
 * with that type and lists the diagnostics of the type analysis at the end.
 */
public class PrintTypedAST extends PrintAST {
  private final TypeAnalysis analysis;

  public PrintTypedAST(TypeAnalysis analysis) {
    this.analysis = analysis;
  }

  private void appendType(ASTNode node) {
    var type = analysis.typeOf(node);
    if (type != null) {
      builder.append(" : ").append(type.getTypeName());
    }
  }

  @Override
  protected void enterNode(ASTListener listener, InnerASTNode node) {
    builder.append('(');
    builder.append(node.getClass().getSimpleName());
    appendType(node);
    builder.append('\n');
  }

  public static String print(ASTNode node) {
    var analysis = new TypeAnalyzer().analyze(node);
    var printer = new PrintTypedAST(analysis);
    printer.visit(node);
    for (var diagnostic : analysis.diagnostics()) {
      printer.builder.append("! ").append(diagnostic).append('\n');
    }
    return printer.getResult();
  }
}
