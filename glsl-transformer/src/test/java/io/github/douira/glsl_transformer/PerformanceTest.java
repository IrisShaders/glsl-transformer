package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.antlr.v4.runtime.atn.PredictionMode;
import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;

public class PerformanceTest extends TestWithBareTransformationManager {
  @Disabled
  @Test
  void testParsingPerformance() {
    // manager.getParser().getInterpreter().setPredictionMode(PredictionMode.SLL);
    var input = TestResourceManager.getResource(FileLocation.EXTERNAL_DECLARATIONS).content();

    // warmup
    manager.parse(input);
    // assertTimeout(Duration.ofSeconds(0), () -> manager.parse(input));
  }
}
