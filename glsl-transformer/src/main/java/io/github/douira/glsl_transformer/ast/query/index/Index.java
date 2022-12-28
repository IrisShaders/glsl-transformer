package io.github.douira.glsl_transformer.ast.query.index;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

public interface Index<N extends ASTNode> {
  void add(N node);

  void remove(N node);

  public static <V> Consumer<Set<V>> iterate(Consumer<V> consumer) {
    return set -> set.stream().forEach(consumer);
  }

  public static <V, R> Function<Set<V>, Stream<R>> iterate(Function<V, R> mapper) {
    return set -> set.stream().map(mapper);
  }
}
