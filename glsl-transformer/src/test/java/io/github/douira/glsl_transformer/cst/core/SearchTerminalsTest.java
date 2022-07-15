package io.github.douira.glsl_transformer.cst.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestForExecutionOrder;

public class SearchTerminalsTest extends TestForExecutionOrder {
  @Test
  void testNestedActivationInactive() {
    manager.addConcurrent(new SearchTerminals<NonFixedJobParameters>()
        .targets((() -> {
          throw new IllegalStateException("Should not be called");
        }))
        .activation(() -> false));
    assertDoesNotThrow(() -> manager.transform("int a;"));
  }

  @Test
  void testNestedActivationActive() {
    manager.addConcurrent(new SearchTerminals<NonFixedJobParameters>()
        .targets((() -> {
          throw new IllegalStateException("Should not be called");
        }))
        .activation(() -> true));
    assertThrows(IllegalStateException.class, () -> manager.transform("int a;"));
  }
}
