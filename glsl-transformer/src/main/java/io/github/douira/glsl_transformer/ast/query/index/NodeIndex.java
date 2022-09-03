package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
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
public class NodeIndex implements Index<ASTNode> {
  public final Map<Class<ASTNode>, Set<ASTNode>> index = new HashMap<>();
  public final Supplier<Set<ASTNode>> bucketConstructor;

  public NodeIndex(Supplier<Set<ASTNode>> bucketConstructor) {
    this.bucketConstructor = bucketConstructor;
  }

  public NodeIndex() {
    this(HashSet::new);
  }

  public static NodeIndex withHashSetBuckets() {
    return new NodeIndex(HashSet::new);
  }

  public static NodeIndex withLinkedHashSetBuckets() {
    return new NodeIndex(LinkedHashSet::new);
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
      set = bucketConstructor.get();
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
   * @param <T>   the type of the class
   * @param type the class of nodes to return
   * @return a set of nodes with the given type
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> Set<T> get(Class<T> type) {
    var result = (Set<T>) index.get(type);
    return result == null ? Collections.emptySet() : result;
  }

  /**
   * Returns a stream of all nodes with the given type.
   * 
   * @param <T>   the type of the class
   * @param type the class of nodes to return
   * @return a stream of nodes with the given type
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> Stream<T> getStream(Class<T> type) {
    var result = (Set<T>) index.get(type);
    return result == null ? Stream.empty() : result.stream();
  }

  /**
   * Returns an arbitrary node with the given type.
   * 
   * @param <T>   the type of the class
   * @param type the class of the node to return
   * @return an arbitrary node with the given type
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> T getOne(Class<T> type) {
    var result = (Set<T>) index.get(type);
    if (result == null) {
      return null;
    }
    var iterator = result.iterator();
    return iterator.hasNext() ? iterator.next() : null;
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
   * @param <T>  The type of the nodes
   * @param node The node to get the set of
   * @return The set of nodes that have the same class as the given node
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> Set<T> get(T node) {
    return (Set<T>) get(node.getClass());
  }

  /**
   * Returns an arbitrary node that has the same class as the given node.
   * 
   * @param <T>  The type of the node
   * @param node The node to get a node from the index for
   * @return An arbitrary node that has the same class as the given node
   */
  @SuppressWarnings("unchecked")
  public <T extends ASTNode> T getOne(T node) {
    return (T) getOne(node.getClass());
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
