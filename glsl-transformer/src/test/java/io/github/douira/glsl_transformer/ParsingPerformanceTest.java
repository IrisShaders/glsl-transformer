package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestResourceManager.*;
import io.github.douira.glsl_transformer.transform.*;

public class ParsingPerformanceTest extends TestWithBareTransformationManager {
  private void assertPerformance(Duration expected, Collection<String> inputs) {
    var man = new TransformationManager<NonFixedJobParameters>(false);
    man.setSLLOnly();

    // warmup the JVM and the parser's DFA cache
    inputs.forEach(man::parse);

    assertTimeout(expected, () -> inputs.forEach(man::parse),
        "It should parse fast enough using SLL mode.");
  }

  private void assertPerformance(Duration expected, Stream<Resource> resources) {
    assertPerformance(expected,
        resources.map(Resource::content).collect(Collectors.toList()));
  }

  private void assertPerformance(Duration expected, Resource... resources) {
    assertPerformance(expected, Stream.of(resources));
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
  void testParsingPerformance() {
    assertDirectoryPerformance(Duration.ofMillis(1000), DirectoryLocation.GLSLANG_TESTS);
  }

  @Test
  void testParsingPerformanceSingle() {
    assertFilePerformance(Duration.ofMillis(300), FileLocation.LONG_EXPRESSION_TEST);
    assertFilePerformance(Duration.ofMillis(0), FileLocation.COMMENT_TEST);
  }
}
