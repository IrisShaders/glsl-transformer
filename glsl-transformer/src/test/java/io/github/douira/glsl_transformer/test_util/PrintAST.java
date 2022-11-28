package io.github.douira.glsl_transformer.test_util;

import io.github.douira.glsl_transformer.ast.node.basic.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class PrintAST extends ASTListenerVisitor<Void> {
  StringBuilder builder = new StringBuilder();

  @Override
  public Void visit(ASTNode node) {
    if (!(node instanceof InnerASTNode innerNode)) {
      builder.append('(');
      builder.append(node.getClass().getSimpleName());
      builder.append(")\n");
    }
    return super.visit(node);
  }

  @Override
  public Void visitData(Object data) {
    builder.append("- ");
    if (data == null) {
      builder.append("null");
    } else {
      builder.append(data.getClass().getSimpleName());
      builder.append(' ');
      builder.append(data.toString());
    }
    builder.append('\n');
    return null;
  }

  @Override
  protected void enterNode(ASTListener listener, InnerASTNode node) {
    builder.append('(');
    builder.append(node.getClass().getSimpleName());
    builder.append('\n');
  }

  @Override
  protected void exitNode(ASTListener listener, InnerASTNode node) {
    builder.append(')');
    builder.append(node.getClass().getSimpleName());
    builder.append('\n');
  }

  public String getResult() {
    return builder.toString();
  }

  public static String print(ASTNode node) {
    var printer = new PrintAST();
    PrintAST.<Void>walkAndListen(node);
    return printer.getResult();
  }
}
