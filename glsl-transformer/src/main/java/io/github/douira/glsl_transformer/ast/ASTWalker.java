package io.github.douira.glsl_transformer.ast;

public class ASTWalker {
  private static final ASTWalker DEFAULT = new ASTWalker();

  public static void walkAST(ASTListener listener, ASTNode node) {
    DEFAULT.walk(listener, node);
  }

  protected void walk(ASTListener listener, ASTNode node) {
    if (node instanceof InnerASTNode innerNode) {
      enterNode(listener, innerNode);
      innerNode.visitDefault(this, listener);
      exitNode(listener, innerNode);
    }
  }

  protected void enterNode(ASTListener listener, InnerASTNode node) {
    listener.enterEveryNode(node);
    node.enterNode(listener);
  }

  protected void exitNode(ASTListener listener, InnerASTNode node) {
    listener.exitEveryNode(node);
    node.exitNode(listener);
  }
}
