package me.douira.glsl_transformer.iris;

import me.douira.glsl_transformer.transform.PhaseCollector;

public class ComplexTransformations {
  public static void registerWith(PhaseCollector collector) {
    collector.registerTransformation(new DeclarationReplacement());

    // ... etc, also maybe define them in here?
    // long ones could be in their own classes and short ones could be inline or
    // inner classes
  }
}
