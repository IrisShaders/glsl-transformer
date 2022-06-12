package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.stream.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestResourceManager.DirectoryLocation;
import io.github.douira.glsl_transformer.transform.*;

public class PerformanceTest extends TestWithBareTransformationManager {
  @Test
  void testParsingPerformance() {
    var man = new TransformationManager<NonFixedJobParameters>(false);
    man.setSLLOnly();

    // warmup the JVM and the parser's DFA cache
    var resources = TestResourceManager
        .getDirectoryResources(DirectoryLocation.GLSLANG_TESTS)
        .collect(Collectors.toList());
    resources.forEach(resource -> man.parse(resource.content()));

    assertTimeout(Duration.ofMillis(1000),
        () -> resources.forEach(
            resource -> man.parse(resource.content())));
  }
}
