package io.github.douira.glsl_transformer.ast;

public class ChildNodeList<Child extends ASTNode> extends ProxyArrayList<Child> {
  private InnerASTNode parent;

  public ChildNodeList(InnerASTNode parent) {
    this.parent = parent;
  }

  @Override
  protected void notifyAddition(Child added) {
    added.setParent(parent);
  }
}
