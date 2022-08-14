package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.SingleASTTransformer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public abstract class TestWithSingleASTTransformer {
  public SingleASTTransformer<JobParameters> p;

  @BeforeEach
  public void setUp() {
    p = new SingleASTTransformer<>();
    p.setSLLOnly();
  }

  public void assertTransform(String expected, String input) {
    p.setPrintType(PrintType.COMPACT);
    assertEquals(expected, p.transform(input));
    p.setPrintType(PrintType.INDENTED);
  }
}
