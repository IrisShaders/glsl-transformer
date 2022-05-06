package io.github.douira.glsl_transformer.core;

import static io.github.douira.glsl_transformer.util.ConfigUtil.*;

import java.util.function.Supplier;

import io.github.douira.glsl_transformer.transform.*;

public class ActivatableTransformation<T extends JobParameters> extends Transformation<T> implements Activatable {
  private Supplier<Boolean> activation;

  public ActivatableTransformation(LifecycleUser<T> content) {
    super(content);
  }

  public ActivatableTransformation() {
  }

  @Override
  public ActivatableTransformation<T> activation(Supplier<Boolean> activation) {
    this.activation = activation;
    return this;
  }

  @Override
  public boolean isActive() {
    return withDefault(activation, true);
  }
}
