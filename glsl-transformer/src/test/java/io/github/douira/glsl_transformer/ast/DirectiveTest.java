package io.github.douira.glsl_transformer.ast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.Directive.Type;

/**
 * Directive injection and printing is already handled in the
 * TransformationPhaseTest.
 */
public class DirectiveTest {
  @Test
  void testConstructor() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Directive(Type.DEFINE, null),
        "It should throw on a null content");
    assertThrows(
        IllegalArgumentException.class,
        () -> new Directive(null, ""),
        "It should throw on a null type");
    assertThrows(
        IllegalArgumentException.class,
        () -> new Directive(Type.EMPTY, "fds"),
        "It should refuse to construct an empty directive with content");
  }

  @Test
  void testGetPrinted() {
    assertEquals(
        "#define foo\n",
        new Directive(Type.DEFINE, "foo").getPrinted(),
        "It should print a define with content");
    assertEquals(
        "#define\n",
        new Directive(Type.DEFINE, "").getPrinted(),
        "It should print a define with no content");
    assertEquals(
        "#define\n",
        new Directive(Type.DEFINE, " ").getPrinted(),
        "It should print a define with blank content");
    assertEquals(
        "#define\n",
        new Directive(Type.DEFINE, " \n").getPrinted(),
        "It should print a define with blank and newline content");
    assertEquals(
        "#define foo\\\nbar\n",
        new Directive(Type.DEFINE, " foo\nbar").getPrinted(),
        "It should print a define with content using newline escapes if there are non-blank newlines");
    assertEquals(
        "#define foo\n",
        new Directive(Type.DEFINE, " foo\n").getPrinted(),
        "It should print a define with content that has trailing newlines");
    assertEquals(
        "#define foo\n",
        new Directive(Type.DEFINE, " \n foo").getPrinted(),
        "It should print a define with content that has a blank prefix");

    assertEquals(
        "#ifdef foo\n",
        new Directive(Type.IFDEF, "foo").getPrinted(),
        "It should print a ifdef with content");

    assertEquals(
        "#\n",
        new Directive().getPrinted(),
        "It should print an empty directive");

    assertEquals("", new Directive().getContent(),
        "The content of an empty directive should be empty.");
  }
}
