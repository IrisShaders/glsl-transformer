package io.github.douira.glsl_transformer.ast.query.index;

import java.util.Set;
import java.util.stream.Stream;

public interface InfixQueryable<S extends Set<E>, E> {
  Stream<S> infixQuery(String infix);

  default Stream<E> infixQueryFlat(String infix) {
    return infixQuery(infix).flatMap(Set::stream);
  }
}
