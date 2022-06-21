package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class EmptyDeclaration extends ExternalDeclaration {
  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.EMPTY_DECLARATION;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitEmptyDeclaration(this);
  }
}
