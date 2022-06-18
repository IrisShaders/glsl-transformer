package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.*;

public abstract class ExternalDeclaration extends ASTNode {
  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitExternalDeclaration(this);
  }
}
