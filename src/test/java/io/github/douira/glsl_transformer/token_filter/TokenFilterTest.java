package io.github.douira.glsl_transformer.token_filter;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.ast.transform.*;
import io.github.douira.glsl_transformer.ast.transform.ASTParser.ParsingCacheStrategy;

public class TokenFilterTest {
  private int nextIndex;
  private SingleASTTransformer<JobParameters> manager;

  @BeforeEach
  void setupManager() {
    manager = new SingleASTTransformer<>(SingleASTTransformer.IDENTITY_TRANSFORMATION);
  }

  @Test
  void testParseTokenFilter() {
    manager.setTokenFilter(new StringFilter<>("a"));
    assertEquals(
        "; ", manager.transform(" ;a "),
        "It should filter tokens before parsing.");
  }

  @Test
  void testJoin() {
    var a = new StringFilter<JobParameters>("a");
    var b = new StringFilter<JobParameters>("b");
    var c = new StringFilter<JobParameters>("c");
    var ab = TokenFilter.join(a, b);
    manager.setTokenFilter(ab);
    assertEquals(
        "c; c; d; e; ",
        manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(ab instanceof MultiFilter, "It should produce a multi filter");

    var abc = TokenFilter.join(ab, c);
    manager.setTokenFilter(abc);
    assertEquals(
        "; ; d; e; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(abc instanceof MultiFilter, "It should still be a multi filter");
    assertNotSame(ab, abc, "It should create a new filter");

    var cab = TokenFilter.join(c, ab);
    manager.setTokenFilter(cab);
    assertEquals(
        "; ; d; e; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(cab instanceof MultiFilter, "It should still be a multi filter");
    assertNotSame(ab, cab, "It should create a new filter");

    var d = new StringFilter<JobParameters>("d");
    var e = new StringFilter<JobParameters>("e");
    var abde = TokenFilter.join(ab, TokenFilter.join(d, e));
    manager.setTokenFilter(abde);
    assertEquals(
        "c; c; ; ; ", manager.transform(" c; a b c; d; e; "),
        "It should filter like all of the contained filters");
    assertTrue(abde instanceof MultiFilter, "It should still be a multi filter");

    assertSame(a, TokenFilter.join(a, null), "It should pass through token filters if one of them is null");
    assertSame(a, TokenFilter.join(null, a), "It should pass through token filters if one of them is null");
  }

  @Test
  void testResetState() {
    var t = new SingleASTTransformer<JobParameters>(SingleASTTransformer.IDENTITY_TRANSFORMATION);
    t.setParsingCacheStrategy(ParsingCacheStrategy.NONE);
    
    t.setTokenFilter(new TokenFilter<>() {
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
    t.transform("");
    assertEquals(1, nextIndex, "It should reset the token filter state");
    t.transform("");
    assertEquals(2, nextIndex, "It should reset the token filter state again");
  }

  @Test
  void testJobParameters() {
    var t = new SingleASTTransformer<JobParameters>(SingleASTTransformer.IDENTITY_TRANSFORMATION);
    t.setTokenFilter(new TokenFilter<>() {
      @Override
      public boolean isTokenAllowed(Token token) {
        nextIndex++;
        assertSame(JobParameters.EMPTY, getJobParameters());
        return true;
      }

      @Override
      public void resetState() {
        nextIndex += 100;
        assertSame(JobParameters.EMPTY, getJobParameters());
      }
    });

    nextIndex = 0;
    t.transform(" ", JobParameters.EMPTY);
    assertEquals(101, nextIndex, "It should run the token filter");
  }
}
