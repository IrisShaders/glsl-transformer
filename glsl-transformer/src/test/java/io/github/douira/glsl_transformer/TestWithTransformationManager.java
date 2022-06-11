package io.github.douira.glsl_transformer;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.transform.*;

public class TestWithTransformationManager<T extends JobParameters> {
  protected TransformationManager<T> manager;
  protected Transformation<T> transformation;

  @BeforeEach
  void setup() {
    manager = new TransformationManager<T>();
    transformation = manager.getRootTransformation();
  }
}
