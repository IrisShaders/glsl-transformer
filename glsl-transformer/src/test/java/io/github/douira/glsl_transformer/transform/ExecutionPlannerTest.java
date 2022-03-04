package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class ExecutionPlannerTest {
  private TransformationManager<Void> manager;
  private int nextIndex;

  @BeforeEach
  void setup() {
    manager = new TransformationManager<>();
    nextIndex = 0;
  }

  @Test
  void testGetRootNode() {
    manager.addConcurrent(new Transformation<>(new RunPhase<>() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertSame(ctx, manager.getRootNode());
        nextIndex++;
      }
    }));

    assertNull(
        manager.getRootNode(), "It should only have a root node during transformation.");
    manager.transform("");
    assertEquals(1, nextIndex, "It should execute the added phase");
    assertNull(
        manager.getRootNode(), "It should only have a root node during transformation.");
  }

  @Test
  void testAddConcurrent() {
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));

    manager.transform("");
    assertEquals(1, nextIndex, "It should run the added phase");
  }

  @Test
  void testAddConcurrentMultiple() {
    for (int i = 0; i < 3; i++) {
      manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    }

    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testAddConcurrentMultipleSingleLevel() {
    for (int i = 0; i < 3; i++) {
      manager.addConcurrent(new RunPhase<Void>() {
        @Override
        protected void run(TranslationUnitContext ctx) {
          nextIndex++;
        }

        @Override
        public void resetState() {
          assertEquals(0, nextIndex);
        }
      });
    }

    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }

  @Test
  void testGetRootTransformation() {
    manager
        .getRootTransformation()
        .addRootDependency(RunPhase.withRun(() -> nextIndex++));

    manager.transform("");
    assertEquals(1, nextIndex, "It should run the root transformation");
  }

  @Test
  void testThrowMultipleFinalization() {
    manager.planExecution();
    assertThrows(IllegalStateException.class, () -> manager.planExecution(),
        "It should throw if execution planning is initiated manually multiple times.");
  }

  @Test
  void testAllowManualFinalization() {
    manager.planExecution();
    assertDoesNotThrow(() -> manager.transform(""),
        "It should not throw after regular manual planning.");
  }
}
