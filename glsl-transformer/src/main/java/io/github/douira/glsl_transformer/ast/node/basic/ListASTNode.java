package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.query.Root;

public abstract class ListASTNode<Child extends ASTNode>
    extends InnerASTNode
    implements ListNode<Child> {
  private ChildNodeList<Child> children;

  public ListASTNode(Stream<Child> children) {
    this.children = ChildNodeList.collect(children, this);
  }

  @Override
  public List<Child> getChildren() {
    return children;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ListASTNode<Child> cloneInto(Root root) {
    return (ListASTNode<Child>) super.cloneInto(root);
  }

  @Override
  @SuppressWarnings("unchecked")
  public ListASTNode<Child> cloneSeparate() {
    return (ListASTNode<Child>) super.cloneSeparate();
  }

  public Stream<Child> getClonedChildren() {
    return children.getClonedStream();
  }
}
