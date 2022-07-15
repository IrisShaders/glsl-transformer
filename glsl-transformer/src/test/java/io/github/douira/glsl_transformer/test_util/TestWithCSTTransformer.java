package io.github.douira.glsl_transformer.test_util;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.cst.transform.*;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public class TestWithCSTTransformer<T extends JobParameters> {
  protected CSTTransformer<T> manager;
  protected Transformation<T> transformation;

  @BeforeEach
  void setupManagerOnly() {
    manager = new CSTTransformer<T>();
    transformation = manager.getRootTransformation();
  }
}
