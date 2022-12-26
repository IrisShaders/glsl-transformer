package io.github.douira.glsl_transformer.basic;

import java.util.function.Supplier;

public interface ParameterHolder<T> {
  T getJobParameters();

  void setJobParameters(T parameters);

  /**
   * Runs a function with job parameters available set in the context.
   * 
   * @param <R>        The return type of the function
   * @param parameters The job parameters to set
   * @param run        The function to run while the transformation manager has
   *                   job parameters
   * @return The value returned by the supplier function
   */
  default <R> R withJobParameters(T parameters, Supplier<R> run) {
    setJobParameters(parameters);
    var value = run.get();
    setJobParameters(null);
    return value;
  }
}
