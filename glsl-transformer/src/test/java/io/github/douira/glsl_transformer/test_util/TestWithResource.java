package io.github.douira.glsl_transformer.test_util;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.cst.transform.*;
import io.github.douira.glsl_transformer.cst.transform.lifecycle.LifecycleUser;
import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.FileLocation;

/**
 * Handles setup of all the things required to run a transformation.
 */
public abstract class TestWithResource extends TestWithBareCSTTransformer {
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
    manager = new CSTTransformer<NonFixedJobParameters>();
    manager.addConcurrent(activity);
    return manager.transform(code);
  }

  public String run(LifecycleUser<NonFixedJobParameters> activity) {
    return run(testCode, activity);
  }
}
