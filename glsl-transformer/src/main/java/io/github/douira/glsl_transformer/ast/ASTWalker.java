package io.github.douira.glsl_transformer.ast;

public class ASTWalker extends ASTBaseVisitor<Void> {
  private ASTListener listener;

  public ASTWalker(ASTListener listener) {
    this.listener = listener;
  }

  public static void walkAST(ASTListener listener, ASTNode node) {
    new ASTWalker(listener).visit(node);
  }

  @Override
  public Void visit(ASTNode node) {
    if (node instanceof InnerASTNode innerNode) {
      enterNode(listener, innerNode);
      super.visit(node);
      exitNode(listener, innerNode);
    } else {
      super.visit(node);
    }
    return null;
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
