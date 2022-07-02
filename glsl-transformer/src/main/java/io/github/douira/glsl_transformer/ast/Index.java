package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public interface Index<T extends ASTNode> {
  void add(T node);

  void remove(T node);
}
