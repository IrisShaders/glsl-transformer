package io.github.douira.glsl_transformer.ast.query;

import java.util.Set;
import java.util.stream.Stream;

public interface SuffixQueryable<E> {
  Stream<Set<E>> suffixQuery(String suffix);

  default Stream<E> suffixQueryFlat(String suffix) {
    return suffixQuery(suffix).flatMap(Set::stream);
  }
}
