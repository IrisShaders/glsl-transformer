package io.github.douira.glsl_transformer.core;

/**
 * Used to specify how often a {@link CachingSupplier} should be evaluated.
 */
public enum CachePolicy {
  /**
   * Generate a value only once and then cache it forever.
   */
  ONCE,

  /**
   * Generate the value every time a new execution plan it built.
   */
  ON_FIXED_PARAMETER_CHANGE,

  /**
   * Generate the value every time a transformation job happens. (beforeRun)
   */
  ON_JOB,

  /**
   * Generate the value every time a value is requested.
   */
  ALWAYS
}
