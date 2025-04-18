package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.parser.*;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

public class ParsingPerformanceTest {
  static final boolean benchmark = false;
  static Duration unitTime;
  String displayName;
  EnhancedParser parser;

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

  private void assertPerformance(int expectedMillis, Collection<String> inputs) {
    // warmup the JVM and the parser's DFA cache
    inputs.forEach(parser::parse);
    var n = benchmark ? 30 : 1;
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

  private void assertPerformance(int expectedMillis, Stream<Resource> resources) {
    assertPerformance(expectedMillis,
        resources.map(Resource::content).collect(Collectors.toList()));
  }

  private void assertFilePerformance(int expectedMillis, Stream<FileLocation> files) {
    assertPerformance(expectedMillis, files.map(TestResourceManager::getResource));
  }

  private void assertFilePerformance(int expectedMillis, FileLocation... files) {
    assertFilePerformance(expectedMillis, Stream.of(files));
  }

  private void assertDirectoryPerformance(int expectedMillis, DirectoryLocation dir) {
    assertPerformance(expectedMillis, TestResourceManager.getDirectoryResources(dir));
  }

  @Test
  void testParsingPerformanceGLSLang() {
    parser = new EnhancedParser(false);
    parser.setSLLOnly();
    assertDirectoryPerformance(1000, DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testParsingPerformanceGLSLangCaching() {
    parser = new CachingParser(false, 1000);
    parser.setSLLOnly();
    assertDirectoryPerformance(20, DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testDeepStatementParsing() {
    parser = new EnhancedParser(true);
    parser.setSLLOnly();
    assertFilePerformance(300, FileLocation.DEEP_STATEMENT_TEST);
  }

  @Test
  void testDeepParenExpressionParsing() {
    parser = new EnhancedParser(true);
    parser.setSLLOnly(); // appears to make no difference in new version of the grammar
    assertFilePerformance(3000, FileLocation.DEEP_PAREN_EXPRESSION_TEST);
  }

  @Test
  void testDeepParenExpressionParsingCaching() {
    // with warmup, the caching parser needs basically no time
    parser = new CachingParser();
    parser.setSLLOnly();
    assertFilePerformance(10, FileLocation.DEEP_PAREN_EXPRESSION_TEST);
  }

  @Test
  void testDeepExpressionParsing() {
    // NOTE: a previous version of the deep expression test (as in 151bcc45) caused
    // a stack overflow but only in gradle using java 16. It's unclear why this was
    // happening but in the following commit the benchmark file has been changed to
    // avoid this.
    parser = new EnhancedParser(true);
    parser.setSLLOnly();
    assertFilePerformance(300, FileLocation.DEEP_EXPRESSION_TEST);
  }

  @Test
  void testLongExpressionParsing() {
    parser = new EnhancedParser(true);
    parser.setSLLOnly();
    assertFilePerformance(300, FileLocation.LONG_EXPRESSION_TEST);
  }

  @Test
  void testCommentParsing() {
    parser = new EnhancedParser(true);
    parser.setSLLOnly();
    assertFilePerformance(300, FileLocation.COMMENT_TEST);
  }
}
