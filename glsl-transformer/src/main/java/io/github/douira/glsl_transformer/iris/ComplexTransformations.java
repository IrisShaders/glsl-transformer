package io.github.douira.glsl_transformer.iris;

import io.github.douira.glsl_transformer.transform.PhaseCollector;

/**
 * This is a group of transformations used in Iris.
 */
public class ComplexTransformations {
  /**
   * Creates transformation instances and adds them to the given phase collector.
   * This method is meant to be passed to
   * {@link io.github.douira.glsl_transformer.transform.PhaseCollector#registerTransformationMultiple(java.util.function.Consumer)}
   * as function.
   * 
   * @param collector The phase collector to register the transformations with
   */
  public static void registerWith(PhaseCollector collector) {
    collector.registerTransformation(new DeclarationReplacement());

    // collector.registerTransformation(new DebugTransformation());

    // ... etc, also maybe define them in here?
    // long ones could be in their own classes and short ones could be inline or
    // inner classes
  }
}
