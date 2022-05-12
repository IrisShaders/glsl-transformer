package io.github.douira.glsl_transformer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestForExecutionOrder;
import io.github.douira.glsl_transformer.transform.*;

public class WrapIdentifierTest extends TestForExecutionOrder {
  @Test
  void testThrowOnUnconfigured() {
    manager.addConcurrent(new WrapIdentifier<>());
    assertThrows(
        IllegalStateException.class,
        () -> manager.transform(""),
        "It should throw if basic configuration values are missing");
  }

  @Test
  void testBasicWrap() {
    manager.addConcurrent(new WrapIdentifier<NonFixedJobParameters>()
        .wrapTarget("foo")
        .detectionResult("bar")
        .injectionExternalDeclaration("int snap = 0;")
        .injectionLocation(InjectionPoint.BEFORE_EOF));
    assertEquals(
        "int bar = 4;int snap = 0;",
        manager.transform("int foo = 4;"),
        "It should wrap the identifier foo, replace it with bar and inject a declaration");
  }

  @Test
  void testParsedWrap() {
    manager.addConcurrent(new WrapIdentifier<NonFixedJobParameters>()
        .wrapTarget("foo")
        .parsedReplacement("bar + 3")
        .injectionExternalDeclaration("int snap = 0;")
        .injectionLocation(InjectionPoint.BEFORE_DECLARATIONS));
    assertEquals(
        "int snap = 0;int a = bar + 3;",
        manager.transform("int a = foo;"),
        "It should wrap the identifier foo, replace it with bar + 3 and inject a declaration");
  }

  @Test
  void testResultDetection() {
    manager.addConcurrent(new WrapIdentifier<NonFixedJobParameters>()
        .wrapTarget("foo")
        .detectionResult("bar")
        .parsedReplacement("bar + 3")
        .injectionExternalDeclaration("int snap = 0;")
        .injectionLocation(InjectionPoint.BEFORE_DECLARATIONS));
    assertThrows(
        SemanticException.class,
        () -> manager.transform("int a = bar;"),
        "It should throw if the detection result is already present");
  }

  @Test
  void testResultDetectionNotInInjection() {
    manager.addConcurrent(new WrapIdentifier<NonFixedJobParameters>()
        .wrapTarget("foo")
        .detectionResult("bar")
        .parsedReplacement("bar + 3")
        .injectionExternalDeclaration("int snap = bar;")
        .injectionLocation(InjectionPoint.BEFORE_DECLARATIONS));
    assertEquals(
        "int snap = bar;int a = bar + 3;",
        manager.transform("int a = foo;"),
        "It should normally wrap even if the detection result appears in the injection.");
  }
}
