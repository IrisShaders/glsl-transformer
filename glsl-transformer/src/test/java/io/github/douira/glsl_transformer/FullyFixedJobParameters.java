package io.github.douira.glsl_transformer;

import io.github.douira.glsl_transformer.transform.JobParameters;

/**
 * Used to test execution plan caching with job parameters that are always
 * cached. This class is not helpful otherwise since it has no useful content
 * and proper job parameters have to use their content in the equals and
 * hashCode methods.
 */
public class FullyFixedJobParameters extends JobParameters {
  private Object tag = new Object();

  @Override
  public boolean equals(JobParameters other) {
    return this == other;
  }

  @Override
  public int hashCode() {
    return tag.hashCode();
  }
}
