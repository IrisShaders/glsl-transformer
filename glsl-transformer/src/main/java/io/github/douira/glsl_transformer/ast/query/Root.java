package io.github.douira.glsl_transformer.ast.query;

import java.util.function.*;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.util.Passthrough;

public class Root {
  public final NodeIndex nodeIndex;
  public final IdentifierIndex identifierIndex;

  private static Root activeBuildRoot;

  public static Root getActiveBuildRoot() {
    return activeBuildRoot;
  }

  protected static synchronized <R> R withActiveBuildRoot(
      Root instance,
      Function<Root, R> rootConsumer) {
    if (activeBuildRoot != null) {
      throw new IllegalStateException("There is already a build in progress");
    }

    activeBuildRoot = instance;
    try {
      return rootConsumer.apply(instance);
    } finally {
      activeBuildRoot = null;
    }
  }

  public static synchronized <NodeType extends ASTNode> NodeType indexNodes(
      Root instance, Supplier<NodeType> builder) {
    return withActiveBuildRoot(instance, root -> {
      var result = builder.get();
      root.registerChild(result);
      return result;
    });
  }

  protected static synchronized <NodeType extends ASTNode> void indexNodes(
      Root instance, Consumer<Passthrough<NodeType>> registerer) {
    withActiveBuildRoot(instance, root -> {
      registerer.accept(Passthrough.of(root::registerChild));
      return null;
    });
  }

  public static <NodeType extends ASTNode> NodeType indexNodes(
      Supplier<NodeType> builder) {
    return indexNodes(new Root(), builder);
  }

  public static <NodeType extends ASTNode> NodeType indexNodes(
      ASTNode parentTreeMember, Supplier<NodeType> builder) {
    return indexNodes(parentTreeMember.getRoot(), builder);
  }

  public static <NodeType extends ASTNode> void indexNodes(
      Consumer<Passthrough<NodeType>> registerer) {
    indexNodes(new Root(), registerer);
  }

  public static <NodeType extends ASTNode> void indexNodes(
      ASTNode parentTreeMember, Consumer<Passthrough<NodeType>> registerer) {
    indexNodes(parentTreeMember.getRoot(), registerer);
  }

  public Root(NodeIndex nodeIndex, IdentifierIndex identifierIndex) {
    this.nodeIndex = nodeIndex;
    this.identifierIndex = identifierIndex;
  }

  public Root() {
    this(new NodeIndex(), new IdentifierIndex());
  }

  public void registerChild(ASTNode child) {
    nodeIndex.add(child);
    if (child instanceof Identifier) {
      identifierIndex.add((Identifier) child);
    }
  }

  public void unregisterChild(ASTNode child) {
    nodeIndex.remove(child);
    if (child instanceof Identifier) {
      identifierIndex.remove((Identifier) child);
    }
  }

  public void merge(Root other) {
    nodeIndex.merge(other.nodeIndex);
    identifierIndex.merge(other.identifierIndex);
  }
}
