package io.github.douira.glsl_transformer.ast;

public class ASTWalker {
  private static final ASTWalker DEFAULT = new ASTWalker();

  public static void walkAST(GeneralASTListener listener, ASTNode node) {
    DEFAULT.walk(listener, node);
  }

  protected void walk(GeneralASTListener listener, ASTNode node) {
    if (node instanceof InnerASTNode innerNode) {
      enterNode(listener, innerNode);
      innerNode.visitDefault(this, listener);
      exitNode(listener, innerNode);
    }
  }

  protected void enterNode(GeneralASTListener listener, InnerASTNode node) {
    listener.enterEveryNode(node);
    node.enterNode(listener);
  }

  protected void exitNode(GeneralASTListener listener, InnerASTNode node) {
    listener.exitEveryNode(node);
    node.exitNode(listener);
  }
}
