package io.github.douira.glsl_transformer.ast.transform;

/**
 * All job parameters have to extend this job parameter base class.
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
