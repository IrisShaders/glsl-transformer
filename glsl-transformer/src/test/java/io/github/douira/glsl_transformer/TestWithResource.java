package io.github.douira.glsl_transformer;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.transform.*;

/**
 * Handles setup of all the things required to run a transformation.
 */
public abstract class TestWithResource extends TestWithBareTransformationManager {
  private static String testResourceInput;

  private static String testCode;

  public static void loadResource(FileLocation location) {
    testResourceInput = TestResourceManager.getResource(location).content();
  }

  public static void setTestCode(String code) {
    testCode = code;
  }

  @BeforeEach
  public void setupParsing() {
    setTestCode(testResourceInput);
  }

  public String run(String code, LifecycleUser<NonFixedJobParameters> activity) {
    manager = new TransformationManager<NonFixedJobParameters>();
    manager.addConcurrent(activity);
    return manager.transform(code);
  }

  public String run(LifecycleUser<NonFixedJobParameters> activity) {
    return run(testCode, activity);
  }
}
