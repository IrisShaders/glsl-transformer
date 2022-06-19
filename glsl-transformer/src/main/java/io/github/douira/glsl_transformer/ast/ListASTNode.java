package io.github.douira.glsl_transformer.ast;

import java.util.List;

public abstract class ListASTNode<Child> extends InnerASTNode {
  public List<Child> children = new ProxyArrayList<>() {
    @Override
    protected void notifyAddition(Child added) {
      ListASTNode.this.notifyAddition(added);
    }
  };

  public ListASTNode(List<Child> children) {
    this.children = children;
  }

  protected void notifyAddition(Child added) {
  }
}
