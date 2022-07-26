package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.*;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

public class ParsingPerformanceTest extends TestWithBareCSTTransformer {
  String displayName;

  @BeforeEach
  void setUp(TestInfo testInfo) {
    displayName = testInfo.getDisplayName();
  }

  private void assertPerformance(boolean throwParseErrors, Duration expected, Collection<String> inputs) {
    var parser = new EnhancedParser(throwParseErrors);
    parser.setSLLOnly();

    // warmup the JVM and the parser's DFA cache
    inputs.forEach(parser::parse);
    var n = System.getProperty("GRADLE") != null ? 5 : 30;
    assertTimeout(expected.multipliedBy(n), () -> {
      var start = System.nanoTime();
      for (int i = 0; i < n; i++) {
        inputs.forEach(parser::parse);
      }
      var duration = Duration.ofNanos(System.nanoTime() - start);
      System.out.println(displayName + ": " + duration.dividedBy(n) + " (" + n + " times)");
    },
        "It should parse fast enough using SLL mode.");
  }

  private void assertPerformance(boolean throwParseErrors, Duration expected, Stream<Resource> resources) {
    assertPerformance(throwParseErrors, expected,
        resources.map(Resource::content).collect(Collectors.toList()));
  }

  private void assertFilePerformance(boolean throwParseErrors, Duration expected, Stream<FileLocation> files) {
    assertPerformance(throwParseErrors, expected, files.map(TestResourceManager::getResource));
  }

  private void assertFilePerformance(boolean throwParseErrors, Duration expected, FileLocation... files) {
    assertFilePerformance(throwParseErrors, expected, Stream.of(files));
  }

  private void assertDirectoryPerformance(boolean throwParseErrors, Duration expected, DirectoryLocation dir) {
    assertPerformance(throwParseErrors, expected, TestResourceManager.getDirectoryResources(dir));
  }

  @Test
  void testParsingPerformanceGLSLang() {
    assertDirectoryPerformance(false,
        Duration.ofMillis(700), DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testDeepStatementParsing() {
    assertFilePerformance(true,
        Duration.ofMillis(100), FileLocation.DEEP_STATEMENT_TEST);
  }

  @Test
  void testDeepExpressionParenParsing() {
    assertFilePerformance(true,
        Duration.ofMillis(700), FileLocation.DEEP_PAREN_EXPRESSION_TEST);
  }

  @Test
  void testDeepExpressionParsing() {
    assertFilePerformance(true,
        Duration.ofMillis(100), FileLocation.DEEP_EXPRESSION_TEST);
  }

  @Test
  void testLongExpressionParsing() {
    assertFilePerformance(true,
        Duration.ofMillis(100), FileLocation.LONG_EXPRESSION_TEST);
  }

  @Test
  void testCommentParsing() {
    assertFilePerformance(true,
        Duration.ofMillis(100), FileLocation.COMMENT_TEST);
  }
}
