package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public class TestWithGroupedASTTransformer {
  public enum Part {
    A, B, C
  }

  public TriASTTransformer<JobParameters, Part> p;

  @BeforeEach
  public void setUp() {
    p = new TriASTTransformer<>(Part.class, Part.A, Part.B, Part.C) {
      @Override
      public Map<Part, String> transform(Map<Part, String> str) throws RecognitionException {
        return super.transform(PrintType.INDENTED, str);
      }
    };
    p.setSLLOnly();
  }

  public void assertTransform(Map<Part, String> expected, Map<Part, String> input) {
    assertEquals(expected, p.transform(PrintType.COMPACT, input));
  }
}
