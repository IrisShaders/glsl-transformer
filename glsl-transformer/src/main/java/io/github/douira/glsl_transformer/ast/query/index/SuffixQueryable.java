package io.github.douira.glsl_transformer.ast.query.index;

import java.util.Set;
import java.util.stream.Stream;

public interface SuffixQueryable<S extends Set<E>, E> {
  Stream<S> suffixQuery(String suffix);

  default Stream<E> suffixQueryFlat(String suffix) {
    return suffixQuery(suffix).flatMap(Set::stream);
  }
}
