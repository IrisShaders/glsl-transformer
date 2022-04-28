package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.*;

/**
 * Handles the setup of a transformation manager and an index for tracking the
 * execution of phases.
 */
public abstract class TestForExecutionOrder {
  protected TransformationManager<NonFixedJobParameters> manager;
  protected Transformation<NonFixedJobParameters> transformation;
  protected int nextIndex;
  protected boolean useWalk;

  protected void useWalkPhases() {
    useWalk = true;
  }

  protected void useRunPhases() {
    useWalk = false;
  }

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
    return useWalk ? new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        assertEquals(index, nextIndex++, message);
      }
    } : new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertEquals(index, nextIndex++, message);
      }
    };
  }

  protected TransformationPhase<NonFixedJobParameters> assertOrderPhase(
      int minimum, int maximum, String message) {
    return useWalk ? new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        assertRange(minimum, maximum, nextIndex++, message);
      }
    } : new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertRange(minimum, maximum, nextIndex++, message);
      }
    };
  }

  protected TransformationPhase<NonFixedJobParameters> assertResetPhase(int index, String message) {
    return useWalk ? new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        nextIndex++;
      }

      @Override
      public void resetState() {
        assertEquals(index, nextIndex, message);
      }
    } : new RunPhase<>() {
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

  protected TransformationPhase<NonFixedJobParameters> assertResetPhase(
      int minimum, int maximum, String message) {
    return useWalk ? new RunWalkPhase<>() {
      @Override
      protected void beforeWalk(TranslationUnitContext ctx) {
        nextIndex++;
      }

      @Override
      public void resetState() {
        assertRange(minimum, maximum, nextIndex, message);
      }
    } : new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
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
