package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

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

  public <V> EnumMap<Part, V> mapOf(V a, V b, V c) {
    EnumMap<Part, V> map = new EnumMap<>(Part.class);
    map.put(Part.A, a);
    map.put(Part.B, b);
    map.put(Part.C, c);
    return map;
  }

  public void assertTransform(Map<Part, String> expected, Map<Part, String> input) {
    assertEquals(expected, p.transform(PrintType.COMPACT, input));
  }

  public void assertTransform(String aExpected, String bExpected, String cExpected, String aInput, String bInput,
      String cInput) {
    assertEquals(mapOf(aExpected, bExpected, cExpected),
        p.transform(PrintType.COMPACT, mapOf(aInput, bInput, cInput)));
  }
}
