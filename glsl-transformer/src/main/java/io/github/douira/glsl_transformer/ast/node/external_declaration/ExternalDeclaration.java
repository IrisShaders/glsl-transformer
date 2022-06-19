package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ExternalDeclaration extends InnerASTNode {
  public enum ExternalDeclarationType {
    FUNCTION_DEFINITION, // TODO
    DECLARATION, // TODO
    PRAGMA_STATEMENT,
    EXTENSION_STATEMENT,
    LAYOUT_DEFAULTS, // TODO (unfinished)
    EMPTY_DECLARATION
  }

  public abstract ExternalDeclarationType getExternalDeclarationType();

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
