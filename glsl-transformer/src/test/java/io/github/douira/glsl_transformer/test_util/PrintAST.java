package io.github.douira.glsl_transformer.test_util;

import io.github.douira.glsl_transformer.ast.node.abstract_node.*;
import io.github.douira.glsl_transformer.ast.node.expression.LiteralExpression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class PrintAST extends ASTListenerVisitor<Void> {
  StringBuilder builder = new StringBuilder();

  @Override
  public Void visit(ASTNode node) {
    if (!(node instanceof InnerASTNode)) {
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

  private static String escape(String s) {
    return s.replace("\t", "\\t")
        .replace("\b", "\\b")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\f", "\\f");
  }

  @Override
  public Void visitLiteralExpression(LiteralExpression node) {
    if (node.isString()) {
      visitData(node.getType());
      visitData(escape(node.getString()));
    } else {
      super.visitLiteralExpression(node);
    }
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
