package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.*;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.util.Passthrough;

public class Root {
  public final NodeIndex nodeIndex;
  public final IdentifierIndex identifierIndex;

  private static Deque<Root> activeBuildRoots = new ArrayDeque<>();

  public static Root getActiveBuildRoot() {
    return activeBuildRoots.peekLast();
  }

  protected static synchronized <R> R withActiveBuildRoot(
      Root instance,
      Function<Root, R> rootConsumer) {
    activeBuildRoots.addLast(instance);
    try {
      return rootConsumer.apply(instance);
    } finally {
      activeBuildRoots.removeLast();
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

  public static <NodeType extends ASTNode> NodeType indexNodes(
    Supplier<NodeType> builder) {
    return indexNodes(new Root(), builder);
  }

  public static <NodeType extends ASTNode> NodeType indexNodes(
      ASTNode parentTreeMember, Supplier<NodeType> builder) {
    return indexNodes(parentTreeMember.getRoot(), builder);
  }

  public static synchronized void indexBuildSession(Root instance, Runnable session) {
    withActiveBuildRoot(instance, root -> {
      session.run();
      return null;
    });
  }

  public static void indexBuildSession(Runnable session) {
    indexBuildSession(new Root(), session);
  }

  public static void indexBuildSession(ASTNode parentTreeMember, Runnable session) {
    indexBuildSession(parentTreeMember.getRoot(), session);
  }

  protected static synchronized <NodeType extends ASTNode> void indexSeparateTrees(
      Root instance, Consumer<Passthrough<NodeType>> registererConsumer) {
    withActiveBuildRoot(instance, root -> {
      registererConsumer.accept(Passthrough.of(root::registerChild));
      return null;
    });
  }

  public static <NodeType extends ASTNode> void indexSeparateTrees(
      Consumer<Passthrough<NodeType>> registerer) {
    indexSeparateTrees(new Root(), registerer);
  }

  public static <NodeType extends ASTNode> void indexSeparateTrees(
      ASTNode parentTreeMember, Consumer<Passthrough<NodeType>> registerer) {
    indexSeparateTrees(parentTreeMember.getRoot(), registerer);
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
