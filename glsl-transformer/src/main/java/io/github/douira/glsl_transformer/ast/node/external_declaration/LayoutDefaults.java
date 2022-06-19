package io.github.douira.glsl_transformer.ast.node.external_declaration;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser.LayoutDefaultsContext;
import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class LayoutDefaults extends InnerASTNode {
  public InnerASTNode qualifier; // TODO: LayoutQualifier
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
    this.qualifier = qualifier;
    this.mode = mode;
  }

  public static LayoutDefaults from(InnerASTNode qualifier, LayoutDefaultsContext ctx) {
    return new LayoutDefaults(qualifier, LayoutMode.fromToken(ctx.layoutMode));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterLayoutDefaults(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitLayoutDefaults(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitLayoutDefaults(this);
  }
}
