package io.github.douira.glsl_transformer.ast.node.external_declaration;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ExtensionDirective extends ExternalDeclaration {
  public enum ExtensionBehavior implements TokenTyped {
    DEBUG(GLSLLexer.NR_REQUIRE),
    ENABLE(GLSLLexer.NR_ENABLE),
    WARN(GLSLLexer.NR_WARN),
    DISABLE(GLSLLexer.NR_DISABLE);

    public final int tokenType;

    private ExtensionBehavior(int tokenType) {
      this.tokenType = tokenType;
    }

    @Override
    public int getTokenType() {
      return tokenType;
    }

    public static ExtensionBehavior fromToken(Token token) {
      return TypeUtil.enumFromToken(ExtensionBehavior.values(), token);
    }
  }

  public String name;
  public ExtensionBehavior behavior; // TODO: nullable

  public ExtensionDirective(String name, ExtensionBehavior behavior) {
    this.name = name;
    this.behavior = behavior;
  }

  public ExtensionDirective(String name) {
    this.name = name;
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.EXTENSION_STATEMENT;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitExtensionDirective(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal nodes have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    // terminal nodes have no children
  }

  @Override
  public ExtensionDirective clone() {
    return new ExtensionDirective(name, behavior);
  }

  @Override
  public ExtensionDirective cloneInto(Root root) {
    return (ExtensionDirective) super.cloneInto(root);
  }

  @Override
  public ExtensionDirective cloneSeparate() {
    return (ExtensionDirective) super.cloneSeparate();
  }
}
