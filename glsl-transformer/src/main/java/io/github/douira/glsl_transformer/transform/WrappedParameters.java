package io.github.douira.glsl_transformer.transform;

public class WrappedParameters<T> extends NonFixedJobParameters {
  private final T parameters;

  public WrappedParameters(T parameters) {
    this.parameters = parameters;
  }

  public T getContents() {
    return parameters;
  }
}
