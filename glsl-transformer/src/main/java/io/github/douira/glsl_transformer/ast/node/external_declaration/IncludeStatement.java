package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class IncludeStatement extends ExternalDeclaration {
  public String content;
  public boolean isAngleBrackets;

  public IncludeStatement(String content, boolean isAngleBrackets) {
    this.content = content;
    this.isAngleBrackets = isAngleBrackets;
  }

  public IncludeStatement(String content) {
    this(content, false);
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.INCLUDE_STATEMENT;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitIncludeStatement(this);
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
  public IncludeStatement clone() {
    return new IncludeStatement(content, isAngleBrackets);
  }

  @Override
  public IncludeStatement cloneInto(Root root) {
    return (IncludeStatement) super.cloneInto(root);
  }

  @Override
  public IncludeStatement cloneSeparate() {
    return (IncludeStatement) super.cloneSeparate();
  }
}
