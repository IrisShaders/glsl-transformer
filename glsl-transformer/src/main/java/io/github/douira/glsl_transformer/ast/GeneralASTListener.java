package io.github.douira.glsl_transformer.ast;

public interface GeneralASTListener {
  void visitDefault(ASTNode node);

  void enterEveryNode(InnerASTNode node);

  void exitEveryNode(InnerASTNode node);
}
