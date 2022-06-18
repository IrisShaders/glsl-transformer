package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.*;

public interface LeafASTNodeListener {
  default void visitVersionStatement(VersionStatement node) {
  }

  default void visitEmptyDeclaration(EmptyDeclaration node) {
  }

  default void visitIdentifier(Identifier node) {
  }
}
