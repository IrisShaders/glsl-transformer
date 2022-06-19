package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ExternalDeclaration extends InnerASTNode {
  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitExternalDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterExternalDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitExternalDeclaration(this);
  }
}
