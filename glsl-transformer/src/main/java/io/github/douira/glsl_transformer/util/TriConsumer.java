package io.github.douira.glsl_transformer.util;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
  void accept(A a, B b, C c);
}
