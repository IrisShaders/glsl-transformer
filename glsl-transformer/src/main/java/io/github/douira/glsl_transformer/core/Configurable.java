package io.github.douira.glsl_transformer.core;

import java.util.Set;
import java.util.function.Supplier;

public interface Configurable {
  Set<CachingSupplier<?>> getCachingSuppliers();

  default <V> Supplier<V> swapSupplier(Supplier<V> currentSupplier, Supplier<V> newSupplier) {
    removeSupplier(currentSupplier);
    return cachingSupplier(newSupplier);
  }

  default <V> Supplier<V> swapSupplier(Supplier<V> currentSupplier, V newValue) {
    return swapSupplier(currentSupplier, new ValueSupplier<V>(newValue));
  }

  default void removeSupplier(Supplier<?> currentSupplier) {
    if (currentSupplier == null) {
      throw new IllegalStateException("The current supplier is null!");
    }
    var cachingSuppliers = getCachingSuppliers();
    if (CachingSupplier.class.isInstance(currentSupplier)) {
      cachingSuppliers.remove((CachingSupplier<?>) currentSupplier);
    }
  }

  default <V> Supplier<V> cachingSupplier(Supplier<V> newSupplier) {
    if (newSupplier == null) {
      throw new IllegalStateException("The new supplier is null!");
    }
    var cachingSuppliers = getCachingSuppliers();
    if (CachingSupplier.class.isInstance(newSupplier)) {
      cachingSuppliers.remove((CachingSupplier<V>) newSupplier);
    }
    return newSupplier;
  }

  default <V> Supplier<V> value(V newValue) {
    return new ValueSupplier<V>(newValue);
  }

  default <V> Supplier<V> missing() {
    return new MissingSupplier<V>();
  }
}
