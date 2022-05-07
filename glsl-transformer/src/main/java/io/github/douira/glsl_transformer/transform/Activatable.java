package io.github.douira.glsl_transformer.transform;

import java.util.function.Supplier;

/**
 * Models things on which an activation supplier can be set.
 */
public interface Activatable {
  /**
   * Gets the current activation state.
   * 
   * @return {@code true} if the object is active
   */
  boolean isActive();

  /**
   * Sets the activation supplier.
   * 
   * @param activation The activation supplier
   * @return This object
   */
  public Activatable activation(Supplier<Boolean> activation);
}
