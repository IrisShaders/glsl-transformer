package io.github.douira.glsl_transformer.generic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EmptyTerminalNodeTest {
  EmptyTerminalNode emptyNode = new EmptyTerminalNode();

  @Test
  void hasNoChildren() {
    assertEquals(emptyNode.getChildCount(), 0, "It should not have any children");
  }

  @Test
  void testGetParent() {
    assertNull(emptyNode.getParent(), "It should not have a parent");
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
