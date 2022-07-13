package io.github.douira.glsl_transformer.cst.node;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * String node injection tests are performed through directive injection tests
 * in TransformationPhaseTest as it uses StringNode internally.
 */
public class StringNodeTest {
  @Test
  void testConstructor() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new StringNode(null),
        "It should throw on a null content");
  }

  @Test
  void testGetContent() {
    assertEquals("foo", new StringNode("foo").getContent(),
        "It should return the content it was constructed with");
    assertEquals("", new StringNode("").getContent(),
        "It should return the empty content it was constructed with");
  }

  @Test
  void testGetPrinted() {
    assertEquals("foo", new StringNode("foo").getPrinted(),
        "It should print the content it was constructed with");
    assertEquals("", new StringNode("").getPrinted(),
        "It should print the empty content it was constructed with");
  }
}
