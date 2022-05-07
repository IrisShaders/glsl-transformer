package io.github.douira.glsl_transformer.transform;

import java.util.function.Supplier;

/**
 * Combines activatable and lifecycle user functionality.
 */
public interface ActivatableLifecycleUser<T extends JobParameters> extends LifecycleUser<T>, Activatable {
  /** Override to make type more specific */
  @Override
  ActivatableLifecycleUser<T> activation(Supplier<Boolean> activation);
}
