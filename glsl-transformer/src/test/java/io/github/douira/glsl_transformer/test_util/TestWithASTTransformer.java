package io.github.douira.glsl_transformer.test_util;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.transform.ASTTransformer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public abstract class TestWithASTTransformer {
  public ASTTransformer<JobParameters> transformer;

  @BeforeEach
  public void setUp() {
    transformer = new ASTTransformer<>();
  }
}
