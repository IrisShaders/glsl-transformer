package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.WalkPhase;
import io.github.douira.glsl_transformer.transform.JobParameters;
import io.github.douira.glsl_transformer.transform.NonFixedJobParameters;
import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

/**
 * Handles the setup of a transformation manager and an index for tracking the
 * execution of phases.
 */
public abstract class TestForExecutionOrder {
  protected TransformationManager<NonFixedJobParameters> manager;
  protected Transformation<NonFixedJobParameters> transformation;
  protected int nextIndex;

  public static void assertRange(int minimum, int maximum, int value, String message) {
    assertTrue(value >= minimum, message);
    assertTrue(value <= maximum, message);
  }

  private abstract class RunWalkPhase<R extends JobParameters> extends WalkPhase<R> {
    @Override
    protected boolean isActiveAtWalk() {
      return false;
    }
  }

  protected TransformationPhase<NonFixedJobParameters> assertOrderPhase(int index, String message) {
    return new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        assertEquals(index, nextIndex++, message);
      }
    };
  }

  protected TransformationPhase<NonFixedJobParameters> assertOrderPhase(
      int minimum, int maximum, String message) {
    return new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        assertRange(minimum, maximum, nextIndex++, message);
      }
    };
  }

  protected TransformationPhase<NonFixedJobParameters> assertResetPhase(int index, String message) {
    return new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        nextIndex++;
      }

      @Override
      public void resetState() {
        assertEquals(index, nextIndex, message);
      }
    };
  }

  protected TransformationPhase<NonFixedJobParameters> assertResetPhase(
      int minimum, int maximum, String message) {
    return new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        nextIndex++;
      }

      @Override
      public void resetState() {
        assertRange(minimum, maximum, nextIndex, message);
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
