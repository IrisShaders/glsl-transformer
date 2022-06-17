package io.github.douira.glsl_transformer.ast.node;

import java.util.List;

import io.github.douira.glsl_transformer.ast.GeneralASTVisitor;

public abstract class InnerASTNode extends ASTNode {
  public abstract List<InnerASTNode> getChildren();

  public abstract InnerASTNode getChild(int index);

  public abstract int getChildCount();

  @Override
  public <R> R accept(GeneralASTVisitor<? extends R> visitor) {
    return visitor.visitChildren(this);
  }
}
