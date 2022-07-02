package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public interface NodeTracker {
  void registerChild(ASTNode child);

  void unregisterChild(ASTNode child);
}
