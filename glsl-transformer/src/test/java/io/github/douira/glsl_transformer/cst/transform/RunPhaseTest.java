package io.github.douira.glsl_transformer.cst.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.cst.node.StringNode;
import io.github.douira.glsl_transformer.test_util.TestForExecutionOrder;

public class RunPhaseTest extends TestForExecutionOrder {
  @Test
  void testCanWalk() {
    assertFalse(RunPhase.withRun(null).canWalk(), "It should not signal being able to a tree walk.");
  }

  @Test
  void testWithInjectNodes() {
    manager.addConcurrent(RunPhase.withInjectNodes(CSTInjectionPoint.END, new StringNode("b")));
    assertEquals("a;\nb", manager.transform("a;"),
        "It should generate a run phase that does an injection of multiple nodes.");
  }

  @Test
  void testWithInjectExternalDeclarations() {
    manager.addConcurrent(RunPhase.withInjectExternalDeclarations(CSTInjectionPoint.END, "foo;"));
    assertEquals("foo;", manager.transform(""),
        "It should generate a run phase that does an injection.");
  }

  @Test
  void testWithRun() {
    manager.addConcurrent(incrementRunPhase());
    manager.transform("");
    assertEquals(1, nextIndex, "It should generate a run phase that does a run.");
  }

  @Test
  void testWithRunNull() {
    manager.addConcurrent(RunPhase.withRun(null));
    manager.transform("");
    assertEquals(0, nextIndex, "It should generate a run phase that does nothing.");
  }
}
