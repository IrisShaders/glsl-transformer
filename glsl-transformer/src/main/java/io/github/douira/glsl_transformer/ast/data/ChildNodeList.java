package io.github.douira.glsl_transformer.ast.data;

import java.util.*;
import java.util.stream.*;

import io.github.douira.glsl_transformer.ast.node.basic.*;

/**
 * A child list that notifies the given parent of child additions.
 * 
 * Implementation of the more complex RemovalProxyArray list isn't necessary
 * because each child notifies its previous parent of the parent change.
 */
public class ChildNodeList<Child extends ASTNode> extends ProxyArrayList<Child> {
  private InnerASTNode parent;

  public ChildNodeList(InnerASTNode parent) {
    this.parent = parent;
  }

  public ChildNodeList(int initialCapacity, InnerASTNode parent) {
    super(initialCapacity);
    this.parent = parent;
  }

  protected ChildNodeList(Collection<? extends Child> c, InnerASTNode parent) {
    super(c, false);
    this.parent = parent;
    notifyAdditionInternal(c);
  }

  @Override
  protected void notifyAddition(Child added) {
    added.setParent(parent);
  }

  public static <Child extends ASTNode> Collector<Child, ?, ChildNodeList<Child>> getCollector(InnerASTNode parent) {
    return Collectors.toCollection(() -> new ChildNodeList<Child>(parent));
  }

  public static <Child extends ASTNode> ChildNodeList<Child> collect(
      Stream<Child> stream, InnerASTNode parent) {
    return stream.collect(getCollector(parent));
  }
}
