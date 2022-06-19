package io.github.douira.glsl_transformer.ast;

import java.util.Collection;
import java.util.function.Consumer;

public class ChildNodeList<Child extends ASTNode> extends ProxyArrayList<Child> {
  private Consumer<Child> childConsumer;

  public ChildNodeList(InnerASTNode parent) {
    this(consumerFromParent(parent));
  }

  public ChildNodeList(int initialCapacity, InnerASTNode parent) {
    this(initialCapacity, consumerFromParent(parent));
  }

  public ChildNodeList(Collection<? extends Child> c, InnerASTNode parent) {
    this(c, consumerFromParent(parent));
  }

  public ChildNodeList(Collection<? extends Child> c, Consumer<Child> childConsumer) {
    super(c);
    this.childConsumer = childConsumer;
  }

  public ChildNodeList(Consumer<Child> childConsumer) {
    this.childConsumer = childConsumer;
  }

  public ChildNodeList(int initialCapacity, Consumer<Child> childConsumer) {
    super(initialCapacity);
    this.childConsumer = childConsumer;
  }

  private static <T extends ASTNode> Consumer<T> consumerFromParent(InnerASTNode parent) {
    return added -> added.setParent(parent);
  }

  @Override
  protected void notifyAddition(Child added) {
    childConsumer.accept(added);
  }
}
