package io.github.douira.glsl_transformer;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.transform.*;

/**
 * Handles setup of all the things required to run a transformation.
 */
public abstract class TestWithTransformationManager<T extends JobParameters> {
  private static String testResourceInput;

  private static String testCode;
  protected TransformationManager<T> manager;

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

  public String runTransformation(String code, LifecycleUser<T> activity) {
    manager = new TransformationManager<T>();
    manager.addConcurrent(activity);
    return manager.transform(code);
  }

  public String runTransformation(LifecycleUser<T> activity) {
    return runTransformation(testCode, activity);
  }
}
