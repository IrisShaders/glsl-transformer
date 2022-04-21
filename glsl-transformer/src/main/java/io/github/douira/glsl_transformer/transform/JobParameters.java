package io.github.douira.glsl_transformer.transform;

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
   */
  public abstract boolean equals(Object obj);

  /**
   * Requires the implementation of the hashCode method.
   */
  public abstract int hashCode();
}
