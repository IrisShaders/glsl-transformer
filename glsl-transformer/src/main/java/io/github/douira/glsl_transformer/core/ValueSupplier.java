package io.github.douira.glsl_transformer.core;

import java.util.function.Supplier;

/**
 * A value supplier is a supplier that just returns a predetermined value.
 */
public class ValueSupplier<V> implements Supplier<V> {
  private V value;

  /**
   * Creates a new value supplier with a given value.
   * 
   * @param value the value to return
   */
  public ValueSupplier(V value) {
    this.value = value;
  }

  @Override
  public V get() {
    return value;
  }
}
