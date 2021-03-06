package io.github.douira.glsl_transformer.ast.node.basic;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;

public abstract class ListASTNode<Child extends ASTNode>
    extends InnerASTNode
    implements ListNode<Child> {
  public final List<Child> children;

  public ListASTNode(Stream<Child> children) {
    this.children = ChildNodeList.collect(children, this);
  }

  @Override
  public List<Child> getChildren() {
    return children;
  }
}
