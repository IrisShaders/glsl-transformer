package io.github.douira.glsl_transformer.ast;

public interface GeneralASTListener {
  default void enterEveryNode(InnerASTNode node) {
  }

  default void exitEveryNode(InnerASTNode node) {
  }
}
