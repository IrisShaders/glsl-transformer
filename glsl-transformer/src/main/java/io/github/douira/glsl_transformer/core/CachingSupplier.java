package io.github.douira.glsl_transformer.core;

import java.util.function.Supplier;

public class CachingSupplier<V> implements Supplier<V> {
  private V cachedValue;
  private Supplier<V> generator;

  public enum Policy {
    /**
     * Generate a value only once and then cache it forever.
     */
    ONCE,
    /**
     * Generate the value every time a new execution plan it built.
     */
    ON_BUILD,

    /**
     * Generate the value every time a transformation job happens. (beforeRun)
     */
    ON_JOB,

    /**
     * Generate the value every time a value is requested.
     */
    ALWAYS
  }

  @Override
  public V get() {
    // TODO Auto-generated method stub
    return null;
  }
}
