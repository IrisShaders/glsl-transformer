package io.github.douira.glsl_transformer.core.target;

import io.github.douira.glsl_transformer.transform.JobParameters;

/**
 * Implements a handler target in a simple way by simply being constructed with
 * a fixed needle. If more flexibility is desired, extend
 * {@link io.github.douira.glsl_transformer.core.target.HandlerTarget}.
 */
public abstract class HandlerTargetImpl<T extends JobParameters> extends HandlerTarget<T> {
  private final String needle;

  /**
   * Creates a new handler target with the given search string
   * 
   * @param needle The search string
   */
  public HandlerTargetImpl(String needle) {
    this.needle = needle;
  }

  /**
   * Creates a new empty handler target that doesn't have a needle. The
   * {@link #getNeedle()} method has to be overwritten if this constructor is
   * used. This is also true for all subclasses using this constructor.
   */
  protected HandlerTargetImpl() {
    this(null);
  }

  @Override
  public String getNeedle() {
    return needle;
  }
}
