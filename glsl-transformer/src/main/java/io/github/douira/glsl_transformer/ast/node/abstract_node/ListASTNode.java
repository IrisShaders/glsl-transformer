package io.github.douira.glsl_transformer.ast.node.abstract_node;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.query.*;

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

  public Stream<Child> getClonedChildren() {
    return children.getClonedStream();
  }
}
