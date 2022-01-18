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
    var target = ThrowTarget.fromMessage("needle", "message");
    assertEquals("needle", target.getNeedle());
    assertEquals("message", target.getException(null, null).getMessage());
  }

  @Test
  void testThrow() {
    var exception = new SemanticException("message");
    nextIndex = 0;
    try {
      runTransformation(
          "int f = foo + oofevilinside;",
          new SearchTerminals<>(
              new ThrowTarget<Void>("evil") {
                @Override
                public SemanticException getException(TreeMember node, String match) {
                  assertEquals(match, "oofevilinside", "It should match the inexact match");
                  nextIndex++;
                  return exception;
                }
              }) {
            {
              allowInexactMatches();
            }
          });

    } catch (SemanticException e) {
      assertEquals(exception, e, "It should throw the right exception");
      nextIndex += 100;
    }
    assertEquals(101, nextIndex,
        "It should visit the right points in the control flow");
  }
  
  @Test
  void testThrowInexactMatch() {
    var exception = new SemanticException("message");
    nextIndex = 0;
    try {
      runTransformation(
          "int f = foo + oofevilinside + outside + evil;",
          new SearchTerminals<>(
              new ThrowTarget<Void>("evil") {
                @Override
                public SemanticException getException(TreeMember node, String match) {
                  assertEquals(match, "evil", "It should match the exact match");
                  nextIndex++;
                  return exception;
                }
              }));
    } catch (SemanticException e) {
      assertEquals(exception, e, "It should throw the right exception");
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
            new SearchTerminals<>(ThrowTarget.fromMessage("evil", "message"))));
  }
}
