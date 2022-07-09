package io.github.douira.glsl_transformer.ast.declaration;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class Declaration extends InnerASTNode {
  public enum DeclarationType {
    FUNCTION,
    TYPE_AND_INIT,
    PRECISION,
    INTERFACE_BLOCK,
    VARIABLE
  }

  public abstract DeclarationType getDeclarationType();

  public abstract <R> R declarationAccept(ASTVisitor<R> visitor);

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        visitor.visitDeclaration(this),
        declarationAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitDeclaration(this);
  }
}
