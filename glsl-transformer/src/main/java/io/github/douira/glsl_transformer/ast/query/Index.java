package io.github.douira.glsl_transformer.ast.query;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public interface Index<T extends ASTNode> {
  void add(T node);

  void remove(T node);

  public static <V> Consumer<Set<V>> iterate(Consumer<V> consumer) {
    return set -> set.stream().forEach(consumer);
  }

  public static <V, R> Function<Set<V>, Stream<R>> iterate(Function<V, R> mapper) {
    return set -> set.stream().map(mapper);
  }
}
