package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ExternalDeclaration extends InnerASTNode {
  public enum ExternalDeclarationType {
    FUNCTION_DEFINITION,
    DECLARATION,
    PRAGMA_STATEMENT,
    EXTENSION_STATEMENT,
    LAYOUT_DEFAULTS,
    EMPTY_DECLARATION
  }

  public abstract ExternalDeclarationType getExternalDeclarationType();

  public abstract <R> R externalDeclarationAccept(ASTVisitor<R> visitor);

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        visitor.visitExternalDeclaration(this),
        externalDeclarationAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterExternalDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitExternalDeclaration(this);
  }

  @Override
  public ExternalDeclaration clone() {
    return (ExternalDeclaration) super.clone();
  }

  @Override
  public ExternalDeclaration cloneInto(Root root) {
    return (ExternalDeclaration) super.cloneInto(root);
  }

  @Override
  public ExternalDeclaration cloneSeparate() {
    return (ExternalDeclaration) super.cloneSeparate();
  }
}
