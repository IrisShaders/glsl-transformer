package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.ReferenceExpression;

/**
 * Indexes nodes based on their content and enabled fast string queries.
 */
public abstract class StringKeyedIndex<V, N extends ASTNode, S extends Set<V>, I extends Map<String, S>>
    implements Index<N> {
  public final I index;
  public final Supplier<S> setFactory;

  public StringKeyedIndex(I index, Supplier<S> setFactory) {
    this.index = index;
    this.setFactory = setFactory;
  }

  protected abstract N getNode(V entry);

  public Set<V> get(String key) {
    var result = index.get(key);
    return result == null ? Collections.emptySet() : result;
  }

  public Stream<V> getStream(String key) {
    var result = index.get(key);
    return result == null ? Stream.empty() : result.stream();
  }

  public <M extends ASTNode> Stream<M> getAncestors(String key, Class<M> ancestorType) {
    return getStream(key)
        .map(value -> getNode(value).getAncestor(ancestorType))
        .filter(Objects::nonNull);
  }

  public Stream<ReferenceExpression> getReferenceExpressions(String key) {
    return getStream(key)
        .map(value -> getNode(value).getAncestor(ReferenceExpression.class))
        .filter(Objects::nonNull);
  }

  public ReferenceExpression getOneReferenceExpression(String key) {
    return getReferenceExpressions(key).findFirst().orElse(null);
  }

  public V getOne(String key) {
    var iterator = index.get(key).iterator();
    return iterator.hasNext() ? iterator.next() : null;
  }

  public V getUnique(String key) {
    var set = index.get(key);
    var resultSize = set == null ? 0 : set.size();
    if (resultSize != 1) {
      throw new IllegalStateException("Expected exactly one result for key " + key + ", but got " + resultSize);
    }
    return set.iterator().next();
  }

  public boolean has(String key) {
    var result = index.get(key);
    return result != null && !result.isEmpty();
  }
}
