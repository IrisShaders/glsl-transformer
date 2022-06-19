package io.github.douira.glsl_transformer.ast;

import java.util.List;
import java.util.stream.*;

public abstract class ListASTNode<Child extends ASTNode>
    extends InnerASTNode
    implements ListNode<Child> {
  public List<Child> children;

  public ListASTNode(List<Child> children) {
    this.children = new ChildNodeList<>(children, this);
  }

  public ListASTNode(Stream<Child> children) {
    this.children = ChildNodeList.collect(children, this);
  }

  @Override
  public List<Child> getChildren() {
    return children;
  }
}
