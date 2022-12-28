package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.ast.transform.*;

public class TestWithGroupedASTTransformer {
  public enum Part {
    A, B, C
  }

  public TriASTTransformer<JobParameters, Part> p;

  @BeforeEach
  public void setUp() {
    p = new TriASTTransformer<>(Part.class, Part.A, Part.B, Part.C);
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
    p.setPrintType(PrintType.COMPACT);
    assertEquals(expected, p.transform(input));
    p.setPrintType(PrintType.INDENTED);
  }

  public void assertTransform(String aExpected, String bExpected, String cExpected, String aInput, String bInput,
      String cInput) {
    p.setPrintType(PrintType.COMPACT);
    assertEquals(mapOf(aExpected, bExpected, cExpected),
        p.transform(mapOf(aInput, bInput, cInput)));
    p.setPrintType(PrintType.INDENTED);
  }
}
