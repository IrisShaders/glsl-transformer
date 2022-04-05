package io.github.douira.glsl_transformer.transform;

public class NonFixedJobParameters extends JobParameters {
  public static final NonFixedJobParameters INSTANCE = new NonFixedJobParameters();

  @Override
  public boolean equals(Object obj) {
    return obj instanceof NonFixedJobParameters;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
