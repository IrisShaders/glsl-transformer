package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

public class ParsingPerformanceTest extends TestWithBareCSTTransformer {
  static final boolean benchmark = false;
  static Duration unitTime;
  String displayName;

  @BeforeAll
  static void sample() {
    var start = System.nanoTime();
    var map = new HashMap<Integer, Object>();
    for (int i = 0; i < 1e6; i++) {
      map.put(i, new Object());
      if (map.size() > 100) {
        map.clear();
      }
    }
    unitTime = Duration.ofNanos(System.nanoTime() - start).dividedBy(50);
    System.out.println("unit time: " + unitTime);
  }

  @BeforeEach
  void setUp(TestInfo testInfo) {
    displayName = testInfo.getDisplayName();
  }

  private void assertPerformance(boolean throwParseErrors, int expectedMillis, Collection<String> inputs) {
    var parser = new EnhancedParser(throwParseErrors);
    parser.setSLLOnly();

    // warmup the JVM and the parser's DFA cache
    inputs.forEach(parser::parse);
    var n = benchmark ? 30 : 3;
    assertTimeout(unitTime.multipliedBy(expectedMillis * n), () -> {
      var start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        inputs.forEach(parser::parse);
      }
      var duration = Duration.ofNanos(System.nanoTime() - start);
      System.out.println(displayName + ": " + duration.dividedBy(n) + " (" + n + " times)");
    },
        "It should parse fast enough using SLL mode.");
  }

  private void assertPerformance(boolean throwParseErrors, int expectedMillis, Stream<Resource> resources) {
    assertPerformance(throwParseErrors,
        expectedMillis,
        resources.map(Resource::content).collect(Collectors.toList()));
  }

  private void assertFilePerformance(boolean throwParseErrors, int expectedMillis, Stream<FileLocation> files) {
    assertPerformance(throwParseErrors, expectedMillis, files.map(TestResourceManager::getResource));
  }

  private void assertFilePerformance(boolean throwParseErrors, int expectedMillis, FileLocation... files) {
    assertFilePerformance(throwParseErrors, expectedMillis, Stream.of(files));
  }

  private void assertDirectoryPerformance(boolean throwParseErrors, int expectedMillis, DirectoryLocation dir) {
    assertPerformance(throwParseErrors, expectedMillis, TestResourceManager.getDirectoryResources(dir));
  }

  @Test
  void testParsingPerformanceGLSLang() {
    assertDirectoryPerformance(false,
        1000, DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testDeepStatementParsing() {
    assertFilePerformance(true,
        300, FileLocation.DEEP_STATEMENT_TEST);
  }

  @Test
  void testDeepParenExpressionParsing() {
    assertFilePerformance(true,
        1000, FileLocation.DEEP_PAREN_EXPRESSION_TEST);
  }

  @Test
  void testDeepExpressionParsing() {
    assertFilePerformance(true,
        300, FileLocation.DEEP_EXPRESSION_TEST);
  }

  @Test
  void testLongExpressionParsing() {
    assertFilePerformance(true,
        300, FileLocation.LONG_EXPRESSION_TEST);
  }

  @Test
  void testCommentParsing() {
    assertFilePerformance(true,
        300, FileLocation.COMMENT_TEST);
  }
}
