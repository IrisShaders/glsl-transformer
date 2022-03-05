package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.RunPhase;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationManager;

/**
 * Handles the setup of a transformation manager and an index for tracking the
 * execution of phases.
 */
public abstract class TestForExecutionOrder {
  protected TransformationManager<Void> manager;
  protected Transformation<Void> transformation;
  protected int nextIndex;

  protected RunPhase<Void> assertOrderPhase(int index, String message) {
    return RunPhase.withRun(() -> assertEquals(index, nextIndex++, message));
  }

  protected RunPhase<Void> assertResetPhase(int index, String message) {
    return new RunPhase<Void>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        nextIndex++;
      }

      @Override
      public void resetState() {
        assertEquals(index, nextIndex, message);
      }
    };
  }

  @BeforeEach
  void setup() {
    manager = new TransformationManager<>();
    transformation = manager.getRootTransformation();
    nextIndex = 0;
  }
}
