package io.github.douira.glsl_transformer.print.filter;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.util.CompatUtil;

public class MultiFilterTest extends TestWithTransformationManager<Void> {
  int nextIndex;

  static <T> TransformationManager<T> assertPrintFilterResult(
      String expected, String input, T parameters, TokenFilter<T> filter, String message) {
    var man = new TransformationManager<T>();
    man.setPrintTokenFilter(filter);
    assertEquals(expected, man.transform(input, parameters), message);
    return man;
  }

  static <T> TransformationManager<T> assertPrintFilterResult(
      String expected, String input, TokenFilter<T> filter, String message) {
    return assertPrintFilterResult(expected, input, null, filter, message);
  }

  @Test
  void testAdd() {
    var multiFilter = new MultiFilter<>();
    assertPrintFilterResult(
        "b;a;", "b;a;", multiFilter, "It should not do anything when it's empty");
    multiFilter.add(new StringFilter<>("a"));
    assertPrintFilterResult(
        "b;;", "b;a;", multiFilter, "It should add the filter to the multi filter");
    multiFilter.add(new StringFilter<>("b"));
    assertPrintFilterResult(
        ";;", "b;a;", multiFilter, "It should filter both things");
  }

  @Test
  void testAddAll() {
    var multiFilter = new MultiFilter<>();
    assertPrintFilterResult(
        "b;a;", "b;a;", multiFilter, "It should not do anything when it's empty");
    multiFilter.addAll(
        CompatUtil.listOf(new StringFilter<>("a"), new StringFilter<>("b")));
    assertPrintFilterResult(
        ";;", "b;a;", multiFilter, "It should filter both things");
    var multiFilter2 = new MultiFilter<>();
    multiFilter2.add(new StringFilter<>("c"));
    multiFilter2.addAll(multiFilter);
    assertPrintFilterResult(
        ";;;", "b;a;c;", multiFilter2,
        "It should also filter the things added through another multi filter");
  }

  @Test
  void testClone() {
    var multiFilter = new MultiFilter<>();
    multiFilter.add(new StringFilter<>("a"));
    var cloned = multiFilter.clone();
    assertNotSame(multiFilter, cloned, "It should produce a new object");
    assertPrintFilterResult(
        "b;;c;", "b;a;c;", cloned, "It should filter after cloning");
  }

  @Test
  void testResetState() {
    nextIndex = 0;
    var multiFilter = new MultiFilter<Void>();
    multiFilter.add(new StringFilter<Void>("a") {
      boolean finished;

      @Override
      public boolean isTokenAllowed(Token token) {
        if (finished) {
          return true;
        }

        if (super.isTokenAllowed(token)) {
          finished = true;
          return true;
        } else {
          return false;
        }
      }

      @Override
      public void resetState() {
        finished = false;
        nextIndex++;
      }
    });
    multiFilter.add(new StringFilter<Void>("b") {
      @Override
      public void resetState() {
        nextIndex += 100;
      }
    });

    assertPrintFilterResult(
        ";;;a;c;", "a;b;b;a;c;", multiFilter, "It should filter correctly");
    assertEquals(101, nextIndex, "It should reset the state of the subfilters");
    assertPrintFilterResult(
        ";;;a;c;", "a;b;b;a;c;", multiFilter, "It should filter correctly again");
  }

  @Test
  void testsetPlanner() {
    nextIndex = 0;
    var parameters = new Object();
    var multiFilter = new MultiFilter<Object>();
    multiFilter.add(new StringFilter<Object>("a") {
      @Override
      public boolean isTokenAllowed(Token token) {
        nextIndex++;
        assertEquals(parameters, getJobParameters(), "It should have job parameters during filtering");
        return super.isTokenAllowed(token);
      }

      @Override
      public void resetState() {
        nextIndex += 100;
        assertEquals(parameters, getJobParameters(), "It should have job parameters during state reset");
      }
    });

    assertPrintFilterResult(";b;", "a;b;", parameters, multiFilter, "It should filter correctly");
    assertEquals(104, nextIndex, "It should run reset and the filtering method");
  }

  @Test
  void testConjunctionAndShortCircuit() {
    nextIndex = 0;
    var multiFilter = new MultiFilter<Void>();
    multiFilter.add(new TokenFilter<Void>() {
      @Override
      public boolean isTokenAllowed(Token token) {
        nextIndex++;
        return !token.getText().toLowerCase().equals("a");
      }
    });
    multiFilter.add(new TokenFilter<Void>() {
      @Override
      public boolean isTokenAllowed(Token token) {
        nextIndex += 100;
        return !token.getText().matches("[A-Z]");
      }
    });

    nextIndex = 0;
    assertPrintFilterResult(";b;;;", "a;b;A;B;", multiFilter, "It should filter with a conjunction by default");
    assertEquals(808, nextIndex, "It should call both filters each time");

    nextIndex = 0;
    multiFilter.setConjunction(false);
    assertPrintFilterResult("a;b;;B;", "a;b;A;B;", multiFilter, "It should filter correctly with a disjunction");
    assertEquals(808, nextIndex, "It should call both filters each time");

    nextIndex = 0;
    multiFilter.setShortCircuit(true);
    assertPrintFilterResult("a;b;;B;", "a;b;A;B;", multiFilter, "It should filter correctly with a disjunction");
    assertEquals(208, nextIndex, "It should call call the second filter only when the first one is false");

    nextIndex = 0;
    multiFilter.setConjunction(true);
    assertPrintFilterResult(";b;;;", "a;b;A;B;", multiFilter, "It should filter correctly with a conjunction");
    assertEquals(608, nextIndex, "It should call call the second filter only when the first one is true");
  }
}
