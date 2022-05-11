package io.github.douira.glsl_transformer.core;

import java.util.function.Supplier;

public class CachingSupplier<V> implements Supplier<V> {
  private V cachedValue;
  private final CachePolicy cachePolicy;
  private final Supplier<V> generator;

  public CachingSupplier(CachePolicy cachePolicy, Supplier<V> generator) {
    this.cachePolicy = cachePolicy;
    this.generator = generator;
  }

  @Override
  public V get() {
    if (cachedValue == null || cachePolicy == CachePolicy.ALWAYS) {
      cachedValue = generator.get();
    }
    return cachedValue;
  }

  public void invalidate(CachePolicy fulfilledPolicy) {
    if (fulfilledPolicy.ordinal() <= cachePolicy.ordinal()) {
      cachedValue = null;
    }
  }

  Supplier<V> getSupplierWithPolicy(CachePolicy cachePolicy) {
    if (this.cachePolicy == cachePolicy) {
      return this;
    } else {
      return of(cachePolicy, generator);
    }
  }

  public static <V> Supplier<V> of(CachePolicy cachePolicy, Supplier<V> generator) {
    return cachePolicy == CachePolicy.ALWAYS ? generator : new CachingSupplier<V>(cachePolicy, generator);
  }
}
