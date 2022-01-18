package io.github.douira.glsl_transformer;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.TestResourceManager.FileLocation;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

/**
 * Handles setup of all the things required to run a transformation.
 */
public abstract class TestWithTransformationManager<P> {
  private static String testResourceInput;

  private static String testCode;
  protected TransformationManager<P> manager;

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

  public String runTransformation(String code, TransformationPhase<P> phase) {
    manager = new TransformationManager<P>();
    manager.registerTransformation(new Transformation<>(phase));
    return manager.transform(code);
  }

  public String runTransformation(TransformationPhase<P> phase) {
    return runTransformation(testCode, phase);
  }
}
