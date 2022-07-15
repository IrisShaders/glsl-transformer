package io.github.douira.glsl_transformer.job_parameter;

/**
 * This class simply wraps a parameter object that extends the job parameter
 * type. This is required when using an object that doesn't extend the job
 * parameter type as a job parameter.
 */
public class WrappedParameters<T> extends NonFixedJobParameters {
  private final T parameters;

  /**
   * Creates a new job parameter wrapper.
   * 
   * @param parameters The job parameters to wrap
   */
  public WrappedParameters(T parameters) {
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
}
