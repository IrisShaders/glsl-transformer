package io.github.douira.glsl_transformer.transform;

/**
 * This class is used when the job parameters have no fixed part and the
 * execution plan can't be statically optimized for certain job parameter
 * combinations.
 */
public class NonFixedJobParameters extends JobParameters {
  /**
   * An empty set non-fixed job parameters. This can be used if there are no job
   * parameters.
   */
  public static final NonFixedJobParameters INSTANCE = new NonFixedJobParameters();

  @Override
  public boolean equals(JobParameters obj) {
    return obj instanceof NonFixedJobParameters;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
