package io.github.douira.glsl_transformer.ast.node.external_declaration;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class LayoutDefaults extends ExternalDeclaration {
  protected InnerASTNode qualifier; // TODO: LayoutQualifier
  public LayoutMode mode;

  public enum LayoutMode implements TokenAssociatedEnum {
    UNIFORM(GLSLLexer.UNIFORM),
    IN(GLSLLexer.IN),
    OUT(GLSLLexer.OUT),
    BUFFER(GLSLLexer.BUFFER);

    public int tokenType;

    private LayoutMode(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static LayoutMode fromToken(Token token) {
      return TypeUtil.enumFromToken(LayoutMode.values(), token);
    }
  }

  public LayoutDefaults(InnerASTNode qualifier, LayoutMode mode) {
    this.qualifier = setup(qualifier);
    this.mode = mode;
  }

  public InnerASTNode getQualifier() {
    return qualifier;
  }

  public void setQualifier(InnerASTNode qualifier) {
    updateParents(this.qualifier, qualifier);
    this.qualifier = qualifier;
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.LAYOUT_DEFAULTS;
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterLayoutDefaults(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitLayoutDefaults(this);
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitLayoutDefaults(this);
  }
}
