package io.github.douira.glsl_transformer.print.filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Consumer;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationManager;

public class TokenFilterTest extends TestWithTransformationManager<Void> {
  private int nextIndex;

  @BeforeEach
  void setup() {
    manager = new TransformationManager<>();
  }

  @Test
  void testPrintTokenFilter() {
    manager.setPrintTokenFilter(new StringFilter<>("b", ";"));
    assertEquals(
        " a ", manager.transform(" a;b; "),
        "It should filter tokens before printing.");
  }

  @Test
  void testParseTokenFilter() {
    manager.setParseTokenFilter(new StringFilter<>("a"));
    assertEquals(
        " ; ", manager.transform(" ;a "),
        "It should filter tokens before parsing.");
  }

  @Test
  void testJoin() {
    var a = new StringFilter<Void>("a");
    var b = new StringFilter<Void>("b");
    var c = new StringFilter<Void>("c");
    var ab = TokenFilter.join(a, b);
    manager.setParseTokenFilter(ab);
    assertEquals(
        " c;   c; d; e; ",
        manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(ab instanceof MultiFilter, "It should produce a multi filter");

    var abc = TokenFilter.join(ab, c);
    manager.setParseTokenFilter(abc);
    assertEquals(
        " ;   ; d; e; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(abc instanceof MultiFilter, "It should still be a multi filter");
    assertNotSame(ab, abc, "It should create a new filter");

    var cab = TokenFilter.join(c, ab);
    manager.setParseTokenFilter(cab);
    assertEquals(
        " ;   ; d; e; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(cab instanceof MultiFilter, "It should still be a multi filter");
    assertNotSame(ab, cab, "It should create a new filter");

    var d = new StringFilter<Void>("d");
    var e = new StringFilter<Void>("e");
    var abde = TokenFilter.join(ab, TokenFilter.join(d, e));
    manager.setParseTokenFilter(abde);
    assertEquals(
        " c;   c; ; ; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(abde instanceof MultiFilter, "It should still be a multi filter");

    assertSame(a, TokenFilter.join(a, null), "It should pass through token filters if one of them is null");
    assertSame(a, TokenFilter.join(null, a), "It should pass through token filters if one of them is null");
  }

  @Test
  void testResetState() {
    var manager = new TransformationManager<Void>();
    testResetStateOnce(manager, manager::setParseTokenFilter);
    manager = new TransformationManager<Void>();
    testResetStateOnce(manager, manager::setPrintTokenFilter);
  }

  void testResetStateOnce(
      TransformationManager<Void> man,
      Consumer<TokenFilter<Void>> setMethod) {
    setMethod.accept(new TokenFilter<Void>() {
      @Override
      public boolean isTokenAllowed(Token token) {
        return true;
      }

      @Override
      public void resetState() {
        nextIndex++;
      }
    });

    nextIndex = 0;
    man.transform("");
    assertEquals(1, nextIndex, "It should reset the token filter state");
    man.transform("");
    assertEquals(2, nextIndex, "It should reset the token filter state again");
  }

  @Test
  void testJobParameters() {
    var manager = new TransformationManager<Object>();
    testJobParametersOnce(manager, manager::setParseTokenFilter);
    manager = new TransformationManager<Object>();
    testJobParametersOnce(manager, manager::setPrintTokenFilter);
  }

  void testJobParametersOnce(
      TransformationManager<Object> man,
      Consumer<TokenFilter<Object>> setMethod) {
    var parameters = new Object();
    setMethod.accept(new TokenFilter<Object>() {
      @Override
      public boolean isTokenAllowed(Token token) {
        nextIndex++;
        assertSame(parameters, getJobParameters());
        return true;
      }

      @Override
      public void resetState() {
        nextIndex += 100;
        assertSame(parameters, getJobParameters());
      }
    });

    nextIndex = 0;
    man.transform(" ", parameters);
    assertEquals(101, nextIndex, "It should run the token filter");
  }
}
