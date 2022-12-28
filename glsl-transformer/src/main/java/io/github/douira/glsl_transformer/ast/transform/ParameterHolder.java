package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.Supplier;

public interface ParameterHolder<J> {
  J getJobParameters();

  void setJobParameters(J parameters);

  /**
   * Runs a function with job parameters available set in the context.
   * 
   * @param <R>        The return type of the function
   * @param parameters The job parameters to set
   * @param run        The function to run while the transformation manager has
   *                   job parameters
   * @return The value returned by the supplier function
   */
  default <R> R withJobParameters(J parameters, Supplier<R> run) {
    setJobParameters(parameters);
    var value = run.get();
    setJobParameters(null);
    return value;
  }
}
