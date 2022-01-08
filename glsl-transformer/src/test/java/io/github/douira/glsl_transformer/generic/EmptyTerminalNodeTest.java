package io.github.douira.glsl_transformer.generic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the empty terminal node. MoveCheckable is tested in its own file.
 */
public class EmptyTerminalNodeTest {
  ExtendedContext parent = new ExtendedContext(null, 0) {
  };
  TreeMember replacedNode = new EmptyTerminalNode(parent);
  EmptyTerminalNode emptyNode = new EmptyTerminalNode(replacedNode);

  @Test
  void hasNoChildren() {
    assertEquals(emptyNode.getChildCount(), 0, "It should not have any children");
  }

  @Test
  void testGetParent() {
    assertEquals(replacedNode.getParent(), emptyNode.getParent(), "It should have the correct parent");
  }

  @Test
  void hasNoPayload() {
    assertNull(emptyNode.getPayload(), "It should have no payload");
    assertNull(emptyNode.getSymbol(), "It should have no symbol");
  }

  @Test
  void sourceIntervalIsEmpty() {
    assertEquals(
        emptyNode.getSourceInterval().length(), 0,
        "It should have an empty source token interval");
  }
}
