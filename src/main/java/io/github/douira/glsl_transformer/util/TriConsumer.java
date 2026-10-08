package io.github.douira.glsl_transformer.util;

/**
 * A triple consumer takes three values and does something with them.
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {
  void accept(A a, B b, C c);
}
