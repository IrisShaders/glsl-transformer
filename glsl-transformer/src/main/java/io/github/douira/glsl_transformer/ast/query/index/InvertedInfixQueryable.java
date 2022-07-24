package io.github.douira.glsl_transformer.ast.query.index;

import java.util.Set;
import java.util.stream.Stream;

public interface InvertedInfixQueryable<E> {
  Stream<Set<E>> invertedInfixQuery(String prefix, String suffix);

  default Stream<E> invertedInfixQueryFlat(String prefix, String suffix) {
    return invertedInfixQuery(prefix, suffix).flatMap(Set::stream);
  }
}
