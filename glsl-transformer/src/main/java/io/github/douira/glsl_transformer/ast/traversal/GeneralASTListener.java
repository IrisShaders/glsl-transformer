package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;

public interface GeneralASTListener {
  default void enterEveryNode(InnerASTNode node) {
  }

  default void exitEveryNode(InnerASTNode node) {
  }

  default void afterEnterEveryNode(InnerASTNode node) {
  }

  default void beforeExitEveryNode(InnerASTNode node) {
  }
}
