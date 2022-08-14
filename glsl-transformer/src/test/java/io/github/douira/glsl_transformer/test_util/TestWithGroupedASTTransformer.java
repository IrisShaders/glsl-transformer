package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.TriASTTransformer;
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
    var result = p.transform(PrintType.COMPACT, input);
    assertEquals(expected.get(Part.A), result.get(Part.A));
    assertEquals(expected.get(Part.B), result.get(Part.B));
    assertEquals(expected.get(Part.C), result.get(Part.C));
    assertEquals(expected, result);
  }

  public void assertTransform(String aExpected, String bExpected, String cExpected, String aInput, String bInput,
      String cInput) {
    var result = p.transform(PrintType.COMPACT, Map.of(Part.A, aInput, Part.B, bInput, Part.C, cInput));
    assertEquals(aExpected, result.get(Part.A));
    assertEquals(bExpected, result.get(Part.B));
    assertEquals(cExpected, result.get(Part.C));
    assertEquals(Map.of(Part.A, aExpected, Part.B, bExpected, Part.C, cExpected), result);
  }
}
