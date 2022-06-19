package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.InnerASTNode;

public interface GeneralASTListener {
  default void enterEveryNode(InnerASTNode node) {
  }

  default void exitEveryNode(InnerASTNode node) {
  }
}
