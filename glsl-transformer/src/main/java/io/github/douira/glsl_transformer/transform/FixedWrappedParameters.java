package io.github.douira.glsl_transformer.transform;

public class FixedWrappedParameters<T> extends JobParameters {
  private final T parameters;

  /**
   * Creates a new job parameter wrapper.
   * 
   * @param parameters The job parameters to wrap
   */
  public FixedWrappedParameters(T parameters) {
    this.parameters = parameters;
  }

  /**
   * Returns the wrapped job parameters.
   * 
   * @return The wrapped job parameters
   */
  public T getContents() {
    return parameters;
  }

  /**
   * Suppresses warnings because the cast results in an object where we don't care
   * what the content of the type parameter T is since we're doing an object
   * comparison anyways.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(JobParameters obj) {
    return FixedWrappedParameters.class.isInstance(obj) &&
        parameters.equals(((FixedWrappedParameters<Object>) obj).getContents());
  }

  @Override
  public int hashCode() {
    return parameters.hashCode();
  }
}
