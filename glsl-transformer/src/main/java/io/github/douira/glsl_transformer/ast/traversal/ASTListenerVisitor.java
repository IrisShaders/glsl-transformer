package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public class ASTListenerVisitor<R> extends ASTWalker<R> implements ASTListener {
  public ASTListenerVisitor() {
    this.listener = this;
  }

  public static <T> T walkAndListen(ASTNode node) {
    return new ASTListenerVisitor<T>().visit(node);
  }
}
