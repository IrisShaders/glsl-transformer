package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public interface ContextTracker {
  default void enterContext(ASTNode node) {
  }
}
