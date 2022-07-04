package io.github.douira.glsl_transformer.ast.query;

import java.util.function.Supplier;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public class Root {
  public ASTNode rootNode;
  public final NodeIndex nodeIndex = new NodeIndex();
  public final IdentifierIndex identifierIndex = new IdentifierIndex();

  private static Root activeBuildRoot;

  public static Root getActiveBuildRoot() {
    return activeBuildRoot;
  }

  public static synchronized ASTNode indexNodes(
    ASTNode parentTreeMember, Supplier<ASTNode> builder) {
    if (activeBuildRoot != null) {
      throw new IllegalStateException("There is already a build in progress");
    }

    activeBuildRoot = parentTreeMember.getRoot();
    var subRoot = builder.get();
    activeBuildRoot = null;
    return subRoot;
  }

  public static synchronized ASTNode indexNodes(Supplier<ASTNode> builder) {
    if (activeBuildRoot != null) {
      throw new IllegalStateException("There is already a build in progress");
    }

    activeBuildRoot = new Root();
    var rootNode = builder.get();
    activeBuildRoot.rootNode = rootNode;
    activeBuildRoot = null;
    return rootNode;
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
