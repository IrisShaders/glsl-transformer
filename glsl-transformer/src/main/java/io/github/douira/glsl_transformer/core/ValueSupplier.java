package io.github.douira.glsl_transformer.core;

import java.util.function.Supplier;

public class ValueSupplier<V> implements Supplier<V> {
  private V value;

  public ValueSupplier(V value) {
    this.value = value;
  }

  @Override
  public V get() {
    return value;
  }
}
