package io.github.douira.glsl_transformer.cst.token_filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Consumer;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.cst.transform.CSTTransformer;
import io.github.douira.glsl_transformer.job_parameter.NonFixedJobParameters;
import io.github.douira.glsl_transformer.test_util.TestWithResource;

public class TokenFilterTest extends TestWithResource {
  private int nextIndex;

  @BeforeEach
  void setupManager() {
    manager = new CSTTransformer<>();
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
    var a = new StringFilter<NonFixedJobParameters>("a");
    var b = new StringFilter<NonFixedJobParameters>("b");
    var c = new StringFilter<NonFixedJobParameters>("c");
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

    var d = new StringFilter<NonFixedJobParameters>("d");
    var e = new StringFilter<NonFixedJobParameters>("e");
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
    var transformer = new CSTTransformer<NonFixedJobParameters>();
    testResetStateOnce(transformer, transformer::setParseTokenFilter);
    transformer = new CSTTransformer<NonFixedJobParameters>();
    testResetStateOnce(transformer, transformer::setPrintTokenFilter);
  }

  void testResetStateOnce(
      CSTTransformer<NonFixedJobParameters> man,
      Consumer<TokenFilter<NonFixedJobParameters>> setMethod) {
    setMethod.accept(new TokenFilter<>() {
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
    var transformer = new CSTTransformer<NonFixedJobParameters>();
    testJobParametersOnce(transformer, transformer::setParseTokenFilter);
    transformer = new CSTTransformer<NonFixedJobParameters>();
    testJobParametersOnce(transformer, transformer::setPrintTokenFilter);
  }

  void testJobParametersOnce(
      CSTTransformer<NonFixedJobParameters> man,
      Consumer<TokenFilter<NonFixedJobParameters>> setMethod) {
    var parameters = new NonFixedJobParameters();
    setMethod.accept(new TokenFilter<>() {
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
