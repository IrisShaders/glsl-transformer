package io.github.douira.glsl_transformer.ast;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public class CollectingRoot implements NodeTracker {
  private static CollectingRoot activeCollector;
  private List<ASTNode> nodes = new ArrayList<>();

  public static synchronized List<ASTNode> collectNodes(
      NodeTracker nodeTracker, Runnable runnable) {
    if (activeCollector != null) {
      throw new IllegalStateException("There is already a collector active");
    }
    activeCollector = new CollectingRoot();
    runnable.run();
    var nodes = activeCollector.nodes;
    activeCollector = null;
    return nodes;
  }

  @Override
  public void registerChild(ASTNode child) {
    activeCollector.nodes.add(child);
  }

  @Override
  public void unregisterChild(ASTNode child) {
    throw new UnsupportedOperationException("Nodes should only be added during AST building");
  }
}
