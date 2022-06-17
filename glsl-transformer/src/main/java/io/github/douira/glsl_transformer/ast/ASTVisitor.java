package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.Identifier;

public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  default R visitIdentifier(Identifier identifier) {
    return visitDefault(identifier);
  }
}
