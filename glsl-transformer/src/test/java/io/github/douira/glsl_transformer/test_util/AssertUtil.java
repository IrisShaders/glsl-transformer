package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.print.*;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.parser.EnhancedParser;
import io.github.douira.glsl_transformer.util.TriConsumer;

public class AssertUtil {
  public static void assertRun(String message) {
    assertTrue(true, message);
  }

  public static void assertReprint(
      Function<GLSLParser, ? extends ParserRuleContext> parseMethod,
      String expected,
      String input) {
    assertReprint(PrintType.INDENTED, parseMethod, expected, input);
  }

  public static void assertReprint(
      PrintType printType,
      Function<GLSLParser, ? extends ParserRuleContext> parseMethod,
      String expected,
      String input) {
    var parser = new EnhancedParser();
    parser.setThrowParseErrors(true);
    parser.getLexer().enableAllFlags();
    var parseTree = parser.parse(input, parseMethod);
    ASTBuilder.setTokenStream(parser.getTokenStream());
    var ast = ASTBuilder.build(RootSupplier.supplyDefault(), parseTree);
    ASTBuilder.unsetTokenStream();
    var reprinted = ASTPrinter.print(printType, ast);
    assertEquals(expected, reprinted);
    ast = ast.cloneInto(RootSupplier.supplyDefault());
    reprinted = ASTPrinter.print(printType, ast);
    assertEquals(expected, reprinted);
  }

  public static ASTNode parseAST(
      Function<GLSLParser, ? extends ParserRuleContext> parseMethod,
      String input) {
    var parser = new EnhancedParser();
    parser.getLexer().enableAllFlags();
    var parseTree = parser.parse(input, parseMethod);
    try {
      ASTBuilder.setTokenStream(parser.getTokenStream());
      return ASTBuilder.build(RootSupplier.supplyDefault(), parseTree);
    } finally {
      ASTBuilder.unsetTokenStream();
    }
  }

  public static void assertQuery(Set<Object> expected, Stream<Object> result) {
    assertEquals(expected, result.collect(Collectors.toSet()));
  }

  public static class Counter implements Runnable {
    int count = 0;

    @Override
    public void run() {
      count++;
    }
  }

  public static void assertCall(int times, Consumer<Counter> callback) {
    var counter = new Counter();
    callback.accept(counter);
    assertEquals(times, counter.count, "It should call the callback " + times + " times");
  }

  public static void assertCall(int timesA, int timesB, BiConsumer<Counter, Counter> callback) {
    var a = new Counter();
    var b = new Counter();
    callback.accept(a, b);
    assertEquals(timesA, a.count, "It should call the callback a " + timesA + " times");
    assertEquals(timesB, b.count, "It should call the callback b " + timesB + " times");
  }

  public static void assertCall(int timesA, int timesB, int timesC, TriConsumer<Counter, Counter, Counter> callback) {
    var a = new Counter();
    var b = new Counter();
    var c = new Counter();
    callback.accept(a, b, c);
    assertEquals(timesA, a.count, "It should call the callback a " + timesA + " times");
    assertEquals(timesB, b.count, "It should call the callback b " + timesB + " times");
    assertEquals(timesC, c.count, "It should call the callback c " + timesC + " times");
  }

  public static class MultiCounter implements Consumer<Integer> {
    int[] counts;

    MultiCounter(int size) {
      counts = new int[size];
    }

    @Override
    public void accept(Integer i) {
      counts[i]++;
    }
  }

  public static void assertCall(Consumer<MultiCounter> callback, int... times) {
    var counter = new MultiCounter(times.length);
    callback.accept(counter);
    for (int i = 0; i < times.length; i++) {
      assertEquals(times[i], counter.counts[i], "It should call the callback " + i + " " + times[i] + " times");
    }
  }

  public static String getInputHash(String content) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-1");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    digest.update(content.getBytes());
    return Base64.getEncoder().encodeToString(digest.digest()).replace("=", "");
  }
}
