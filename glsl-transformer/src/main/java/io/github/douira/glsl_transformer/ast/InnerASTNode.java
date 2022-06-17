package io.github.douira.glsl_transformer.ast;

import java.util.List;

public abstract class InnerASTNode extends ASTNode {
  public abstract void enterNode(GeneralASTListener listener);

  public abstract void exitNode(GeneralASTListener listener);

  public abstract void visitDefault(ASTWalker walker, GeneralASTListener listener);
  
  public abstract List<InnerASTNode> getChildren();

  public abstract InnerASTNode getChild(int index);

  public abstract int getChildCount();

  @Override
  public <R> R accept(GeneralASTVisitor<? extends R> visitor) {
    return visitor.visitChildren(this);
  }
}
