package io.github.douira.glsl_transformer;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.transform.*;

public class TestWithCSTTransformer<T extends JobParameters> {
  protected CSTTransformer<T> manager;
  protected Transformation<T> transformation;

  @BeforeEach
  void setupManagerOnly() {
    manager = new CSTTransformer<T>();
    transformation = manager.getRootTransformation();
  }
}
