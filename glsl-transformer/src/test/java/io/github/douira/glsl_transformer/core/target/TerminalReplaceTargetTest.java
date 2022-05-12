package io.github.douira.glsl_transformer.core.target;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestForExecutionOrder;
import io.github.douira.glsl_transformer.core.SearchTerminals;
import io.github.douira.glsl_transformer.transform.NonFixedJobParameters;

public class TerminalReplaceTargetTest extends TestForExecutionOrder {
  @Test
  void testBasicReplacement() {
    manager.addConcurrent(
        new SearchTerminals<NonFixedJobParameters>()
            .singleTarget(new TerminalReplaceTargetImpl<>("foo", "bar")));
    assertEquals(
        "int bar;",
        manager.transform("int foo;"),
        "It should wrap the identifier foo, replace it with bar and inject a declaration");
  }
}
