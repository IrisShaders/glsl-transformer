package io.github.douira.glsl_transformer.ast.query.index;

import java.util.Set;
import java.util.stream.Stream;

public interface PrefixQueryable<S extends Set<E>, E> {
  Stream<S> prefixQuery(String prefix);

  default Stream<E> prefixQueryFlat(String prefix) {
    return prefixQuery(prefix).flatMap(Set::stream);
  }
}
