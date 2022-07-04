package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.*;

public class ASTWalker<R> extends ASTBaseVisitor<R> {
  protected ASTListener listener;

  protected ASTWalker() {
  }

  private ASTWalker(ASTListener listener) {
    this.listener = listener;
  }

  public static <T> T walk(ASTListener listener, ASTNode node) {
    return new ASTWalker<T>(listener).visit(node);
  }

  @Override
  public R visit(ASTNode node) {
    if (node instanceof InnerASTNode innerNode) {
      enterNode(listener, innerNode);
      var result = super.visit(node);
      exitNode(listener, innerNode);
      return result;
    } else {
      return super.visit(node);
    }
  }

  protected void enterNode(ASTListener listener, InnerASTNode node) {
    listener.enterEveryNode(node);
    node.enterNode(listener);
    listener.afterEnterEveryNode(node);
  }

  protected void exitNode(ASTListener listener, InnerASTNode node) {
    listener.beforeExitEveryNode(node);
    node.exitNode(listener);
    listener.exitEveryNode(node);
  }
}
