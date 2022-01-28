package io.github.douira.glsl_transformer.core.target;

/**
 * Implements a handler target in a simple way by simply being constructed with
 * a fixed needle. If more flexibility is desired, extend
 * {@link io.github.douira.glsl_transformer.core.target.HandlerTarget}.
 */
public abstract class HandlerTargetImpl<T> extends HandlerTarget<T> {
  private final String needle;

  /**
   * Creates a new handler target with the given search string
   * 
   * @param needle The search string
   */
  public HandlerTargetImpl(String needle) {
    this.needle = needle;
  }

  @Override
  public String getNeedle() {
    return needle;
  }
}
