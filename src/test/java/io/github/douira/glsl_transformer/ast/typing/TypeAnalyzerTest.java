package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.TypingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;

import io.github.douira.glsl_transformer.test_util.TestCaseSource;

/**
 * Table-driven tests of the type analysis. The cases are in the
 * TypeAnalyzerTest.cases file.
 */
public class TypeAnalyzerTest {
  /**
   * Each case consists of global declarations and a list of lines of the form
   * {@code expression => type} or {@code expression => type ! CODES}, where the
   * expression is typed inside of a function after the declarations.
   */
  @ParameterizedTest
  @TestCaseSource(caseSet = "testExpressions")
  void testExpressions(String scenario, String declarations, String expectations) {
    var checks = new ArrayList<Executable>();
    for (var line : expectations.split("\n")) {
      if (line.isBlank() || line.startsWith("//")) {
        continue;
      }
      var parts = line.split(" => ", 2);
      assertEquals(2, parts.length, "Malformed expectation: " + line);
      var actual = describeExpression(declarations, parts[0]);
      checks.add(() -> assertEquals(parts[1].trim(), actual, parts[0]));
    }
    assertFalse(checks.isEmpty());
    assertAll(scenario, checks);
  }

  /**
   * Each case consists of a translation unit and the expected diagnostic codes
   * in order, or {@code ok} if there are none.
   */
  @ParameterizedTest
  @TestCaseSource(caseSet = "testPrograms")
  void testPrograms(String scenario, String source, String expected) {
    assertEquals(expected.trim(), codes(analyze(source)), scenario + ":\n" + source);
  }
}
