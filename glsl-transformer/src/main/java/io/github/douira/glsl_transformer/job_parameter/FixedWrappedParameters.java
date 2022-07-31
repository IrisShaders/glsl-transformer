package io.github.douira.glsl_transformer.job_parameter;

/**
 * The default {@link WrappedParameters} implementation uses the
 * {@link NonFixedJobParameters} which means that it never produces cached
 * execution plans. This class on the other hand causes execution plans to be
 * cached if the contained parameter object is the same.
 */
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
  public boolean equals(Object other) {
    return FixedWrappedParameters.class.isInstance(other) &&
        parameters.equals(((FixedWrappedParameters<Object>) other).getContents());
  }

  @Override
  public int hashCode() {
    return parameters.hashCode();
  }
}
