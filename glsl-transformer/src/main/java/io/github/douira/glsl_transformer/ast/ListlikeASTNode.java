package io.github.douira.glsl_transformer.ast;

import java.util.List;

public abstract class ListlikeASTNode<Child extends ASTNode> extends InnerASTNode implements List<Child> {

  @Override
  public void visitDefault(ASTWalker walker, GeneralASTListener listener) {
    for (Child child : this) {
      walker.walk(listener, child);
    }
  }
}
