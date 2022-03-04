package io.github.douira.glsl_transformer.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

public class TransformationTest {
  private TransformationManager<Void> manager;
  private Transformation<Void> transformation;
  private int nextIndex;

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
        RunPhase.withRun(() -> assertEquals(1, nextIndex++,
            "The dependent should run second.")),
        RunPhase.withRun(() -> assertEquals(0, nextIndex++,
            "The dependency should run first.")));
    manager.transform("");
    assertEquals(2, nextIndex, "Both run phases should run");
  }

  @Test
  void testAddDependent() {
    transformation.addDependent(
        RunPhase.withRun(() -> assertEquals(0, nextIndex++,
            "The dependency should run first.")),
        RunPhase.withRun(() -> assertEquals(1, nextIndex++,
            "The dependent should run second.")));
    manager.transform("");
    assertEquals(2, nextIndex, "Both run phases should run");
  }

  @Test
  void testChainDependencyOnRoot() {
    transformation.addRootDependency(
        RunPhase.withRun(() -> assertEquals(2, nextIndex++,
            "The root dependency should run third.")));
    transformation.chainDependency(
        RunPhase.withRun(() -> assertEquals(1, nextIndex++,
            "The first chained dependency should run second.")));
    transformation.chainDependency(
        RunPhase.withRun(() -> assertEquals(0, nextIndex++,
            "The second chained dependency should run first.")));
    manager.transform("");
    assertEquals(3, nextIndex, "All three phases should run");
  }

  @Test
  void testChainDependencyDefaultMany() {
    for (int i = 0; i < 10; i++) {
      var localIndex = i;
      transformation.chainDependency(
          RunPhase.withRun(() -> assertEquals(
              10 - localIndex - 1, nextIndex++,
              "The dependency added with i=" + localIndex + "should run at index " + (10 - localIndex - 1) + ".")));
    }
    manager.transform("");
    assertEquals(10, nextIndex, "All phases should run");
  }

  @Test
  void testChainDependentOnEnd() {
    transformation.addEndDependent(
        RunPhase.withRun(() -> assertEquals(0, nextIndex++,
            "The end dependent should run first.")));
    transformation.chainDependent(
        RunPhase.withRun(() -> assertEquals(1, nextIndex++,
            "The first chained dependent should run second.")));
    transformation.chainDependent(
        RunPhase.withRun(() -> assertEquals(2, nextIndex++,
            "The second chained dependent should run third.")));
    manager.planExecution();
    manager.transform("");
    assertEquals(3, nextIndex, "All three run phases should run");
  }

  @Test
  void testAddRootDependency() {

  }

  @Test
  void testAddEndDependent() {

  }

  @Test
  void testAppend() {

  }

  @Test
  void testPrepend() {

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
}
