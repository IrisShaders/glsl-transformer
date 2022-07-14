package io.github.douira.glsl_transformer.util;

import java.util.function.*;

@FunctionalInterface
public interface Passthrough<V> extends Function<V, V> {
  V apply(V v);

  public static <V> Passthrough<V> of(Function<V, V> f) {
    return f::apply;
  }

  public static <V> Passthrough<V> of(Consumer<V> f) {
    return v -> {
      f.accept(v);
      return v;
    };
  }
}
