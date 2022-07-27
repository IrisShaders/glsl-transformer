package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.ASTTransformer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public abstract class TestWithASTTransformer {
  public ASTTransformer<JobParameters> t;

  @BeforeEach
  public void setUp() {
    t = new ASTTransformer<>();
    t.setSLLOnly();
  }

  public void assertTransform(String expected, String input) {
    assertEquals(expected, t.transform(PrintType.COMPACT, input));
  }
}
