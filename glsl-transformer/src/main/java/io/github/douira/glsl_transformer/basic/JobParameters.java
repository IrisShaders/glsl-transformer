package io.github.douira.glsl_transformer.basic;

/**
 * All job parameters have to extend this job parameter base class. It enforces
 * an equals and hashCode method in order to allow the "fixed" part job
 * parameters to be used in a map.
 * 
 * If no part of the job parameters is fixed, the class
 * {@link NonFixedJobParameters} can be used instead.
 */
public interface JobParameters {
  public static final JobParameters EMPTY = new JobParameters() {
    @Override
    public boolean equals(Object other) {
      return other == this;
    }

    @Override
    public int hashCode() {
      return 0;
    }
  };

  /**
   * Requires the implementation of the equals method.
   */
  boolean equals(Object other);

  /**
   * Requires the implementation of the hashCode method.
   */
  int hashCode();
}
