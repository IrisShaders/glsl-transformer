package io.github.douira.glsl_transformer.test_util;

import io.github.douira.glsl_transformer.basic.JobParameters;

/**
 * Used to test execution plan caching with job parameters that are always
 * cached. This class is not helpful otherwise since it has no useful content
 * and proper job parameters have to use their content in the equals and
 * hashCode methods.
 */
public class FullyFixedJobParameters implements JobParameters {
  private Object tag = new Object();

  @Override
  public boolean equals(Object other) {
    return this == other;
  }

  @Override
  public int hashCode() {
    return tag.hashCode();
  }
}
