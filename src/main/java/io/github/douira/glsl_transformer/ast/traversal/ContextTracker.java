package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

public interface ContextTracker {
  default void enterContext(ASTNode node) {
  }
}
