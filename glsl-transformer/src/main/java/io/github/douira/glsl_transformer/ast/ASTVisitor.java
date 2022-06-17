package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.Identifier;

public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  R visitIdentifier(Identifier identifier);  
}
