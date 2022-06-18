package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.ASTVisitor;

public class EmptyDeclaration extends ExternalDeclaration {
  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitEmptyDeclaration(this);
  }
}