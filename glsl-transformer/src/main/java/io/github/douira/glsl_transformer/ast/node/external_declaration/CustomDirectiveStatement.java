package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class CustomDirectiveStatement extends ExternalDeclaration {
  public String content;

  public CustomDirectiveStatement(String content) {
    this.content = content;
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.CUSTOM_DIRECTIVE_STATEMENT;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitCustomDirectiveStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal nodes have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal nodes have no children
  }

  @Override
  public CustomDirectiveStatement clone() {
    return new CustomDirectiveStatement(content);
  }

  @Override
  public CustomDirectiveStatement cloneInto(Root root) {
    return (CustomDirectiveStatement) super.cloneInto(root);
  }

  @Override
  public CustomDirectiveStatement cloneSeparate() {
    return (CustomDirectiveStatement) super.cloneSeparate();
  }
}
