package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.Transformation.PhaseEntry;

public class PhaseCollectorTest {
  private TransformationManager manager;
  private int nextIndex;

  void addOrderedPhase(int order, int group) {
    final var assertIndex = nextIndex++;
    manager.addPhaseAt(new PhaseEntry(new RunPhase() {
      @Override
      protected void run(TranslationUnitContext ctx) {
        assertEquals(assertIndex, nextIndex++,
            "It should execute the phase with order  " + order + " and group " + group + " at the right time.");
      }
    }, order, group));
  }

  void assertPhaseExecutionOrder(Runnable runnable) {
    nextIndex = 0;
    manager = new TransformationManager();
    runnable.run();
    var expectedNextIndex = nextIndex;
    nextIndex = 0;
    manager.transform("");
    assertEquals(expectedNextIndex, nextIndex, "It should execute all added phases");
  }

  @Test
  void testAddPhaseAt() {
    assertPhaseExecutionOrder(() -> {
      addOrderedPhase(-5, 0);
      addOrderedPhase(0, 0);
      addOrderedPhase(1, 0);
      addOrderedPhase(5, 0);
      addOrderedPhase(6, 0);
    });
    assertPhaseExecutionOrder(() -> {
      addOrderedPhase(6, -5);
      addOrderedPhase(5, 0);
      addOrderedPhase(1, 1);
      addOrderedPhase(0, 5);
      addOrderedPhase(-5, 6);
    });
    assertPhaseExecutionOrder(() -> {
      addOrderedPhase(6, -5);
      addOrderedPhase(0, 1);
      addOrderedPhase(1, 1);
      addOrderedPhase(5, 1);
      addOrderedPhase(-5, 2);
    });
    assertPhaseExecutionOrder(() -> {
      addOrderedPhase(0, 1);
      addOrderedPhase(0, 1);
      addOrderedPhase(0, 2);
    });
  }

  @Test
  void testGetRootNode() {
    manager = new TransformationManager();
    nextIndex = 0;
    manager.registerTransformation(new Transformation(new RunPhase() {
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
    manager = new TransformationManager();
    nextIndex = 0;
    var transformation = new Transformation() {
      @Override
      void addPhasesTo(PhaseCollector collector) {
        nextIndex++;
      }

      @Override
      protected void resetState() {
        nextIndex++;
      }
    };

    manager.registerTransformation(transformation);
    manager.registerTransformation(transformation);
    assertEquals(2, nextIndex, "It should request the transformation to add phases to it");

    nextIndex = 0;
    manager.transform("");
    assertEquals(2, nextIndex, "It should reset the state on each stored transformation");
  }

  @Test
  void testRegisterTransformationMultiple() {
    manager = new TransformationManager();
    nextIndex = 0;
    manager.registerTransformationMultiple(collector -> {
      assertEquals(manager, collector, "It should pass itself as the collector to register transformations to");
      nextIndex++;
    });
    assertEquals(1, nextIndex, "It should call the function for registering transformations on it");
  }
}