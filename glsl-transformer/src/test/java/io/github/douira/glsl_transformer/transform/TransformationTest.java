package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class TransformationTest {
  private TransformationManager<Void> manager;
  private Transformation<Void> transformation;
  private int nextIndex;

  RunPhase<Void> getAssertPhase(int index, String message) {
    return RunPhase.withRun(() -> assertEquals(index, nextIndex++, message));
  }

  @BeforeEach
  void setup() {
    manager = new TransformationManager<>();
    transformation = manager.getRootTransformation();
    nextIndex = 0;
  }

  @Test
  void testRootAndEndNode() {
    var rootNode = transformation.getRootDepNode();
    var endNode = transformation.getEndDepNode();
    assertTrue(rootNode.getDependencies().contains(endNode),
        "The root node should depend on the end node.");
    assertTrue(endNode.getDependents().contains(rootNode),
        "The end node should have the root node as a dependent.");
  }

  @Test
  void testAddDependency() {
    transformation.addDependency(
        getAssertPhase(1, "The dependent should run second."),
        getAssertPhase(0, "The dependency should run first."));
    manager.transform("");
    assertEquals(2, nextIndex, "Both run phases should run.");
  }

  @Test
  void testAddDependent() {
    transformation.addDependent(
        getAssertPhase(0, "The dependency should run first."),
        getAssertPhase(1, "The dependent should run second."));
    manager.transform("");
    assertEquals(2, nextIndex, "Both run phases should run");
  }

  @Test
  void testChainDependencyDefault() {
    transformation.chainDependency(
        getAssertPhase(1, "The first chained dependency should run second."));
    transformation.chainDependency(
        getAssertPhase(0, "The second chained dependency should run first."));
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testChainDependencyOnRoot() {
    transformation.addRootDependency(
        getAssertPhase(2, "The root dependency should run third."));
    transformation.chainDependency(
        getAssertPhase(1, "The first chained dependency should run second."));
    transformation.chainDependency(
        getAssertPhase(0, "The second chained dependency should run first."));
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run.");
  }

  @Test
  void testChainDependencyDefaultMany() {
    for (int i = 0; i < 10; i++) {
      var localIndex = i;
      transformation.chainDependency(
          getAssertPhase(10 - localIndex - 1,
              "The dependency added with i=" + localIndex + "should run at index " + (10 - localIndex - 1) + "."));
    }
    manager.transform("");
    assertEquals(10, nextIndex, "All phases should run.");
  }

  @Test
  void testChainDependentDefault() {
    transformation.chainDependent(
        getAssertPhase(0, "The first chained dependent should run first."));
    transformation.chainDependent(
        getAssertPhase(1, "The second chained dependent should run second."));
    manager.transform("");
    assertEquals(2, nextIndex, "Both run phases should run.");
  }

  @Test
  void testChainDependentOnEnd() {
    transformation.addEndDependent(
        getAssertPhase(0, "The end dependent should run first."));
    transformation.chainDependent(
        getAssertPhase(1, "The first chained dependent should run second."));
    transformation.chainDependent(
        getAssertPhase(2, "The second chained dependent should run third."));
    manager.planExecution();
    manager.transform("");
    assertEquals(3, nextIndex, "All three run phases should run.");
  }

  @Test
  void testChainDependentDefaultMany() {
    for (int i = 0; i < 10; i++) {
      var localIndex = i;
      transformation.chainDependent(
          getAssertPhase(localIndex,
              "The dependent added with i=" + localIndex + "should run at index " + localIndex + "."));
    }
    manager.transform("");
    assertEquals(10, nextIndex, "All phases should run.");
  }

  @Test
  void testAddRootAndEndDependency() {
    transformation.addRootDependency(
        new RunPhase<Void>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            nextIndex++;
          }

          @Override
          public void resetState() {
            assertEquals(0, nextIndex, "The root dependency should run in the first level.");
          }
        });
    transformation.addEndDependent(
        new RunPhase<Void>() {
          @Override
          protected void run(TranslationUnitContext ctx) {
            nextIndex++;
          }

          @Override
          public void resetState() {
            assertEquals(0, nextIndex, "The ene dependent should run in the first level.");
          }
        });
    manager.transform("");
    assertEquals(2, nextIndex, "Both phases should run.");
  }

  @Test
  void testAppendDependency() {
    transformation.addEndDependent(RunPhase.withRun(() -> nextIndex++));
    transformation.addRootDependency(RunPhase.withRun(() -> nextIndex++));
    transformation.chainDependency(RunPhase.withRun(() -> nextIndex++));
    transformation.appendDependency(
        getAssertPhase(0, "The appended phase should run before all other phases."));
    manager.transform("");
    assertEquals(4, nextIndex, "All phases should run.");
  }

  @Test
  void testPrependDependent() {
    transformation.addRootDependency(RunPhase.withRun(() -> nextIndex++));
    transformation.addEndDependent(RunPhase.withRun(() -> nextIndex++));
    transformation.chainDependent(RunPhase.withRun(() -> nextIndex++));
    transformation.prependDependent(
        getAssertPhase(3, "The prepended phase should run after all other phases."));
    manager.transform("");
    assertEquals(4, nextIndex, "All phases should run.");
  }

  @Test
  void testChainConcurrentDependency() {

  }

  @Test
  void testChainConcurrentDependent() {

  }

  @Test
  void testChainConcurrentBoth() {

  }

  @Test
  void testBadDependency() {
    assertThrows(Error.class, () -> {
      transformation.addEndDependent(RunPhase.withRun(null));
      transformation.chainDependency(RunPhase.withRun(null));
    }, "It should not allow making the end node a dependent");
    assertThrows(Error.class, () -> {
      transformation.addRootDependency(RunPhase.withRun(null));
      transformation.chainDependent(RunPhase.withRun(null));
    }, "It should not allow making the root node a dependency");
  }
}
