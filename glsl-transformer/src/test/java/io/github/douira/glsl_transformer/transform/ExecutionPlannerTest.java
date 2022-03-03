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
  void testRegisterTransformation() {
    manager = new TransformationManager<Void>();
    nextIndex = 0;
    var transformation = new Transformation<Void>() {
      @Override
      public void init() {
        nextIndex++;
      }

      @Override
      public void resetState() {
        nextIndex++;
      }
    };

    manager.addConcurrent(transformation);
    assertEquals(2, nextIndex, "It should request the transformation to add phases to it");

    nextIndex = 0;
    manager.transform("");
    assertEquals(2, nextIndex, "It should reset the state on each stored transformation");
  }
}
