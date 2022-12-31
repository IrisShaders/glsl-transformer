package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class CustomDirective extends ExternalDeclaration {
  public String content;

  public CustomDirective(String content) {
    this.content = content;
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.CUSTOM_DIRECTIVE_STATEMENT;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitCustomDirective(this);
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
  public CustomDirective clone() {
    return new CustomDirective(content);
  }

  @Override
  public CustomDirective cloneInto(Root root) {
    return (CustomDirective) super.cloneInto(root);
  }

  @Override
  public CustomDirective cloneSeparate() {
    return (CustomDirective) super.cloneSeparate();
  }
}
