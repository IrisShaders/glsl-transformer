package io.github.douira.glsl_transformer.ast.node.type.qualifier;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class TypeQualifierPart extends InnerASTNode {
  public enum QualifierType {
    STORAGE,
    LAYOUT,
    PRECISION,
    INTERPOLATION,
    INVARIANT,
    PRECISE
  }

  public abstract QualifierType getQualifierType();

  public abstract <R> R typeQualifierPartAccept(ASTVisitor<R> visitor);

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        visitor.visitTypeQualifierPart(this),
        typeQualifierPartAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTypeQualifierPart(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTypeQualifierPart(this);
  }
}
