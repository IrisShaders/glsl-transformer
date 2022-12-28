package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;

/**
 * Indexes nodes based on their ASTNode subclass and enables fast queries for
 * nodes by type. Only the exact class is added to the index which means
 * querying for the superclass Expression returns no results. Use
 * {@link SuperclassNodeIndex} to index nodes by the chain of their
 * superclasses.
 * 
 * Unchecked casts are used but they are safe because each set in the map only
 * has the right types of nodes.
 */
public class NodeIndex<S extends Set<ASTNode>> implements Index<ASTNode> {
  public final Map<Class<ASTNode>, S> index = new HashMap<>();
  public final Supplier<S> setFactory;

  public NodeIndex(Supplier<S> setFactory) {
    this.setFactory = setFactory;
  }

  public static NodeIndex<HashSet<ASTNode>> withUnordered() {
    return new NodeIndex<HashSet<ASTNode>>(HashSet::new);
  }

  public static NodeIndex<LinkedHashSet<ASTNode>> withOrdered() {
    return new NodeIndex<LinkedHashSet<ASTNode>>(LinkedHashSet::new);
  }

  /**
   * Method used internally to add a node to the index. This is only meant to be
   * called by {@link Root}.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void add(ASTNode node) {
    var nodeClass = (Class<ASTNode>) node.getClass();
    var set = index.get(nodeClass);
    if (set == null) {
      set = setFactory.get();
      index.put(nodeClass, set);
    }
    set.add(node);
  }

  /**
   * Method used internally to remove a node from the index. This is only meant to
   * be
   * called by {@link Root}.
   */
  @Override
  public void remove(ASTNode node) {
    var set = index.get(node.getClass());
    if (set == null) {
      return;
    }
    set.remove(node);
  }

  /**
   * Returns a set of all nodes with the given type.
   * 
   * @param <N>  the type of the class
   * @param type the class of nodes to return
   * @return a set of nodes with the given type
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> Set<N> get(Class<N> type) {
    var result = (Set<N>) index.get(type);
    return result == null ? Collections.emptySet() : result;
  }

  /**
   * Returns a stream of all nodes with the given type.
   * 
   * @param <N>  the type of the class
   * @param type the class of nodes to return
   * @return a stream of nodes with the given type
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> Stream<N> getStream(Class<N> type) {
    var result = (Set<N>) index.get(type);
    return result == null ? Stream.empty() : result.stream();
  }

  /**
   * Returns an arbitrary node with the given type.
   * 
   * @param <N>  the type of the class
   * @param type the class of the node to return
   * @return an arbitrary node with the given type
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> N getOne(Class<N> type) {
    var result = (Set<N>) index.get(type);
    if (result == null) {
      return null;
    }
    var iterator = result.iterator();
    return iterator.hasNext() ? iterator.next() : null;
  }

  /**
   * Returns the only node with the given type. Throws an exception if there is
   * not exactly one node with the given type.
   * 
   * @param <N>  the type of the class
   * @param type the class of the node to return
   * @return the only node with the given type
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> N getUnique(Class<N> type) {
    var result = (Set<N>) index.get(type);
    var resultCount = result == null ? 0 : result.size();
    if (resultCount != 1) {
      throw new IllegalStateException("Expected exactly one node of type " + type + " but found " + resultCount);
    }
    return result.iterator().next();
  }

  /**
   * Checks if the index contains any nodes of the given type.
   * 
   * @param type the class of the nodes to check for
   * @return true if the index contains any nodes of the given type
   */
  public boolean has(Class<? extends ASTNode> type) {
    var result = index.get(type);
    return result != null && !result.isEmpty();
  }

  /**
   * Returns the set of nodes that have the same class as the given node.
   * 
   * @param <N>  The type of the nodes
   * @param node The node to get the set of
   * @return The set of nodes that have the same class as the given node
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> Set<N> get(N node) {
    return (Set<N>) get(node.getClass());
  }

  /**
   * Returns an arbitrary node that has the same class as the given node.
   * 
   * @param <N>  The type of the node
   * @param node The node to get a node from the index for
   * @return An arbitrary node that has the same class as the given node
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> N getOne(N node) {
    return (N) getOne(node.getClass());
  }

  /**
   * Returns the only node that has the same class as the given node. Throws an
   * exception if there is not exactly one node with the same class as the given
   * node.
   * 
   * @param <N>  The type of the node
   * @param node The node to get a node from the index for
   * @return The only node that has the same class as the given node
   */
  @SuppressWarnings("unchecked")
  public <N extends ASTNode> N getUnique(N node) {
    return (N) getUnique(node.getClass());
  }

  /**
   * Checks if the index contains a node of the given type.
   * 
   * @param node the node to check for
   * @return true if the index contains the node
   */
  public boolean has(ASTNode node) {
    return has(node.getClass());
  }

  /**
   * Checks if the index contains the given node itself.
   * 
   * @param node the node to check for
   * @return true if the index contains the node
   */
  public boolean hasExact(ASTNode node) {
    var set = get(node);
    return set != null && set.contains(node);
  }
}
