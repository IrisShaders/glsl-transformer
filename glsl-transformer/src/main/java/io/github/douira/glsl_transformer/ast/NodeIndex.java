package io.github.douira.glsl_transformer.ast;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

/**
 * Indexes nodes based on their ASTNode subclass and enables fast queries for
 * nodes by type.
 * 
 * Unchecked casts are used but they are safe because each set in the map only
 * has the right types of nodes.
 */
public class NodeIndex implements Index<ASTNode> {
  public final Map<Class<? extends ASTNode>, Set<? extends ASTNode>> index = new HashMap<>();

  @SuppressWarnings("unchecked")
  @Override
  public void add(ASTNode node) {
    var set = (Set<ASTNode>) index.get(node.getClass());
    if (set == null) {
      set = new HashSet<>();
      index.put(node.getClass(), set);
    }
    set.add(node);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void remove(ASTNode node) {
    var set = (Set<ASTNode>) index.get(node.getClass());
    if (set == null) {
      return;
    }
    set.remove(node);

  }

  @SuppressWarnings("unchecked")
  public <T extends ASTNode> Set<T> get(Class<T> clazz) {
    return (Set<T>) index.get(clazz);
  }

  @SuppressWarnings("unchecked")
  public <T extends ASTNode> Set<T> get(T node) {
    return (Set<T>) get(node.getClass());
  }
}
