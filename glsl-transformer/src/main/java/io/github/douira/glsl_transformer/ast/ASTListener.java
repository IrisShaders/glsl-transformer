package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.Identifier;

public interface ASTListener extends GeneralASTListener {
  default void visitIdentifier(Identifier identifier) {
  }
}
