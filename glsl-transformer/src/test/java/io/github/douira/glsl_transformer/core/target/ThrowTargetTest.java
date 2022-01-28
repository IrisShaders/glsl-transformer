package io.github.douira.glsl_transformer.core.target;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.core.SearchTerminals;
import io.github.douira.glsl_transformer.transform.SemanticException;
import io.github.douira.glsl_transformer.tree.TreeMember;

public class ThrowTargetTest extends TestWithTransformationManager<Void> {
  private int nextIndex;

  @Test
  void testFromMessage() {
    var target = new ThrowTargetImpl<>("needle", "message");
    assertEquals("needle", target.getNeedle());
    assertEquals("message", target.getMessage(null, null));
  }

  @Test
  void testThrow() {
    var message = "message";
    nextIndex = 0;
    try {
      runTransformation(
          "int f = foo + oofevilinside;",
          new SearchTerminals<>(
              new ThrowTarget<Void>("evil") {
                @Override
                public String getMessage(TreeMember node, String match) {
                  assertEquals(match, "oofevilinside", "It should match the inexact match");
                  nextIndex++;
                  return message;
                }
              }) {
            {
              allowInexactMatches();
            }
          });
    } catch (SemanticException e) {
      assertSame(message, e.getMessage(), "It should throw an exception containins the right message");
      nextIndex += 100;
    }
    assertEquals(101, nextIndex,
        "It should visit the right points in the control flow");
  }
  
  @Test
  void testThrowInexactMatch() {
    var message = "message";
    nextIndex = 0;
    try {
      runTransformation(
          "int f = foo + oofevilinside + outside + evil;",
          new SearchTerminals<>(
              new ThrowTarget<Void>("evil") {
                @Override
                public String getMessage(TreeMember node, String match) {
                  assertEquals(match, "evil", "It should match the exact match");
                  nextIndex++;
                  return message;
                }
              }));
    } catch (SemanticException e) {
      assertSame(message, e.getMessage(), "It should throw an exception containins the right message");
      nextIndex += 100;
    }
    assertEquals(101, nextIndex,
        "It should visit the right points in the control flow");
  }

  @Test
  void testNoThrow() {
    assertDoesNotThrow(
        () -> runTransformation(
            "int f = foo + EvilInCaps + EVILCAPS + e_v_i_l_spaced;",
            new SearchTerminals<>(new ThrowTargetImpl<>("evil", "message"))));
  }
}
