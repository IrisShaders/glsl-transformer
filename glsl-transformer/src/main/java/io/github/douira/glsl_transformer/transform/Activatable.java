package io.github.douira.glsl_transformer.transform;

import java.util.function.Supplier;

public interface Activatable {
  boolean isActive();

  public Activatable activation(Supplier<Boolean> activation);
}
