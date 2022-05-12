package io.github.douira.glsl_transformer.core;

import java.util.function.Supplier;

/**
 * Wraps a supplier and caches its result according to a given cache policy.
 */
public class CachingSupplier<V> implements Supplier<V> {
  private V cachedValue;
  private final CachePolicy cachePolicy;
  private final Supplier<V> generator;

  /**
   * Creates a new caching supplier with a given cache policy and supplier.
   * 
   * @param cachePolicy The cache policy
   * @param generator   The supplier
   */
  protected CachingSupplier(CachePolicy cachePolicy, Supplier<V> generator) {
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

  /**
   * Invalidates the cached value if the cache policy of this caching supplier
   * requires it based on the strength of the given cache invalidation event.
   * 
   * @param fulfilledPolicy The strength of the cache invalidation event
   */
  public void invalidate(CachePolicy fulfilledPolicy) {
    if (fulfilledPolicy.ordinal() <= cachePolicy.ordinal()) {
      cachedValue = null;
    }
  }

  /**
   * Returns a supplier with the given cache policy and the same internal supplier
   * but uses the current caching supplier if it has the same cache policy.
   * 
   * @param cachePolicy The cache policy to use on the returned supplier
   * @return A supplier with the given cache policy
   */
  Supplier<V> getSupplierWithPolicy(CachePolicy cachePolicy) {
    if (this.cachePolicy == cachePolicy) {
      return this;
    } else {
      return of(cachePolicy, generator);
    }
  }

  /**
   * Returns any supplier that supplies the same values as a caching supplier with
   * the given cache policy and value generating supplier. This means that if the
   * {@code CachePolicy#ALWAYS} is used, the returned supplier will be the same as
   * the given supplier.
   * 
   * @param <V>         The value type
   * @param cachePolicy The cache policy to use on the returned supplier
   * @param generator   The supplier to use on the returned supplier
   * @return A supplier with the given cache policy
   */
  public static <V> Supplier<V> of(CachePolicy cachePolicy, Supplier<V> generator) {
    return cachePolicy == CachePolicy.ALWAYS ? generator : new CachingSupplier<V>(cachePolicy, generator);
  }
}
