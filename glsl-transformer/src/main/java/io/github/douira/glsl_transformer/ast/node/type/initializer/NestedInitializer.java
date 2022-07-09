package io.github.douira.glsl_transformer.ast.node.type.initializer;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class NestedInitializer extends Initializer {
  public final List<Initializer> initializers;

  public NestedInitializer(List<Initializer> initializers) {
    this.initializers = new ChildNodeList<>(initializers, this);
  }

  public NestedInitializer(Stream<Initializer> initializers) {
    this.initializers = ChildNodeList.collect(initializers, this);
  }

  public NestedInitializer() {
    this.initializers = new ChildNodeList<>(this);
  }

  @Override
  public InitializerType getInitializerType() {
    return InitializerType.NESTED;
  }

  @Override
  public <R> R initializerAccept(ASTVisitor<R> visitor) {
    return visitor.visitNestedInitializer(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterNestedInitializer(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitNestedInitializer(this);
  }
}
