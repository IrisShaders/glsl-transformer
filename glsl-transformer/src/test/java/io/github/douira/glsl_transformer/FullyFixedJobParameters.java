package io.github.douira.glsl_transformer;

import io.github.douira.glsl_transformer.transform.JobParameters;

public class FullyFixedJobParameters extends JobParameters {
  private Object tag = new Object();

  @Override
  public boolean equals(JobParameters obj) {
    return this == obj;
  }

  @Override
  public int hashCode() {
    return tag.hashCode();
  }
}
