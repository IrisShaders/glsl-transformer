package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class ExecutionPlannerTest {
  private TransformationManager<Void> manager;
  private int nextIndex;

  @Test
  void testGetRootNode() {
    manager = new TransformationManager<Void>();
    nextIndex = 0;
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
    manager = new TransformationManager<Void>();
    nextIndex = 0;

    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));

    manager.transform("");
    assertEquals(1, nextIndex, "It should run the added phase");
  }

  @Test
  void testAddConcurrentMultiple() {
    manager = new TransformationManager<Void>();
    nextIndex = 0;

    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));
    manager.addConcurrent(RunPhase.withRun(() -> nextIndex++));

    manager.transform("");
    assertEquals(3, nextIndex, "It should run all of the added phases");
  }
}
