package io.github.douira.glsl_transformer.core.target;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestForExecutionOrder;
import io.github.douira.glsl_transformer.core.SearchTerminals;
import io.github.douira.glsl_transformer.transform.NonFixedJobParameters;

public class TerminalReplaceTargetTest extends TestForExecutionOrder {
  @Test
  void testReplacement() {
    manager.addConcurrent(
        new SearchTerminals<NonFixedJobParameters>()
            .singleTarget(new TerminalReplaceTargetImpl<>("foo", "bar")));
    // this test only breaks with the new opening detection
    assertEquals(
        "int bar = bar;",
        manager.transform("int foo = foo;"),
        "It should handle multiple replacement in spaced contexts");
    assertEquals(
        "int bar = bar(a);",
        manager.transform("int foo = foo(a);"),
        "It should handle multiple replacement in spaced contexts");
    assertEquals(
        "int a = bar(bar);",
        manager.transform("int a = foo(foo);"),
        "It should handle multiple replacement in spaced contexts");
    assertEquals(
        "int bar = bar(bar);",
        manager.transform("int foo = foo(foo);"),
        "It should handle multiple replacement in spaced contexts");
    assertEquals(
        "int bar;",
        manager.transform("int foo;"),
        "It should wrap the identifier foo, replace it with bar and inject a declaration");
    assertEquals(
        "int bar ;",
        manager.transform("int foo ;"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "void bar () {}",
        manager.transform("void foo () {}"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "void main(int bar) {}",
        manager.transform("void main(int foo) {}"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "void main( int bar) {}",
        manager.transform("void main( int foo) {}"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "int a = 1+ bar+ 1;",
        manager.transform("int a = 1+ foo+ 1;"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "int a = 1+ bar + 1;",
        manager.transform("int a = 1+ foo + 1;"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "int a = 1+bar + 1;",
        manager.transform("int a = 1+foo + 1;"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "int a = bar( a);",
        manager.transform("int a = foo( a);"),
        "It should handle replacement in spaced contexts");
    assertEquals(
        "int a = a( bar);",
        manager.transform("int a = a( foo);"),
        "It should handle replacement in spaced contexts");
  }
}
