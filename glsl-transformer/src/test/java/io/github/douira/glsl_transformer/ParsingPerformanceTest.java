package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.basic.EnhancedParser;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

public class ParsingPerformanceTest extends TestWithBareCSTTransformer {
  private void assertPerformance(Duration expected, Collection<String> inputs) {
    var parser = new EnhancedParser(false);

    // warmup the JVM and the parser's DFA cache
    inputs.forEach(parser::parse);

    assertTimeout(expected, () -> inputs.forEach(parser::parse),
        "It should parse fast enough using SLL mode.");
  }

  private void assertPerformance(Duration expected, Stream<Resource> resources) {
    assertPerformance(expected,
        resources.map(Resource::content).collect(Collectors.toList()));
  }

  private void assertFilePerformance(Duration expected, Stream<FileLocation> files) {
    assertPerformance(expected, files.map(TestResourceManager::getResource));
  }

  private void assertFilePerformance(Duration expected, FileLocation... files) {
    assertFilePerformance(expected, Stream.of(files));
  }

  private void assertDirectoryPerformance(Duration expected, DirectoryLocation dir) {
    assertPerformance(expected, TestResourceManager.getDirectoryResources(dir));
  }

  @Test
  void testParsingPerformanceGLSLang() {
    assertDirectoryPerformance(Duration.ofMillis(1000), DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testParsingPerformanceSingle() {
    assertFilePerformance(Duration.ofMillis(600), FileLocation.DEEP_STATEMENT_TEST);
    assertFilePerformance(Duration.ofMillis(1200), FileLocation.DEEP_PAREN_EXPRESSION_TEST);
    assertFilePerformance(Duration.ofMillis(300), FileLocation.DEEP_EXPRESSION_TEST);
    assertFilePerformance(Duration.ofMillis(300), FileLocation.LONG_EXPRESSION_TEST);
    assertFilePerformance(Duration.ofMillis(300), FileLocation.COMMENT_TEST);
  }
}
