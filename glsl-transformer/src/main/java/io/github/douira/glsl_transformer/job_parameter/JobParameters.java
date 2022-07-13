package io.github.douira.glsl_transformer.job_parameter;

/**
 * All job parameters have to extend this job parameter base class. It enforces
 * an equals and hashCode method in order to allow the "fixed" part job
 * parameters to be used in a map.
 * 
 * If no part of the job parameters is fixed, the class
 * {@link NonFixedJobParameters} can be used instead.
 */
public abstract class JobParameters {
  /**
   * Requires the implementation of the equals method.
   * 
   * @param other The other job parameters to compare to
   * @return {@code true} if the fixed parts of the two job parameters are equal
   */
  public abstract boolean equals(JobParameters other);

  @Override
  public boolean equals(Object other) {
    return other instanceof JobParameters ? equals((JobParameters) other) : false;
  }

  /**
   * Requires the implementation of the hashCode method.
   */
  public abstract int hashCode();
}
