package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.SingleASTTransformer;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public abstract class TestWithSingleASTTransformer {
  public SingleASTTransformer<JobParameters> p;

  @BeforeEach
  public void setUp() {
    p = new SingleASTTransformer<>() {
      @Override
      public String transform(String str) throws RecognitionException {
        return transform(PrintType.INDENTED, str);
      }
    };
    p.setSLLOnly();
  }

  public void assertTransform(String expected, String input) {
    assertEquals(expected, p.transform(PrintType.COMPACT, input));
  }
}
