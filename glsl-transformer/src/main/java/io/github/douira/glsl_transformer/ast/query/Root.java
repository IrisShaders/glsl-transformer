package io.github.douira.glsl_transformer.ast.query;

import java.util.function.Supplier;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public class Root {
  public ASTNode rootNode;
  public final NodeIndex nodeIndex;
  public final IdentifierIndex identifierIndex;

  private static Root activeBuildRoot;

  public static Root getActiveBuildRoot() {
    return activeBuildRoot;
  }

  protected static synchronized ASTNode indexNodesInternal(
      Root instance, Supplier<ASTNode> builder) {
    if (activeBuildRoot != null) {
      throw new IllegalStateException("There is already a build in progress");
    }

    activeBuildRoot = instance;
    try {
      return builder.get();
    } finally {
      activeBuildRoot = null;
    }
  }

  public static ASTNode indexNodes(Root instance, Supplier<ASTNode> builder) {
    var root = indexNodesInternal(instance, builder);
    instance.rootNode = root;
    return root;
  }

  public static ASTNode indexNodes(Supplier<ASTNode> builder) {
    return indexNodes(new Root(), builder);
  }

  public static ASTNode indexNodes(
      ASTNode parentTreeMember, Supplier<ASTNode> builder) {
    return indexNodesInternal(parentTreeMember.getRoot(), builder);
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
