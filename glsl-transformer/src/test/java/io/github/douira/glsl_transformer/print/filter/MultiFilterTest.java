package io.github.douira.glsl_transformer.print.filter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.TestWithTransformationManager;
import io.github.douira.glsl_transformer.transform.TransformationManager;
import io.github.douira.glsl_transformer.util.CompatUtil;

public class MultiFilterTest extends TestWithTransformationManager<Void> {
  static <T> TransformationManager<T> assertPrintFilterResult(
      String expected, String input, TokenFilter<T> filter, String message) {
    var man = new TransformationManager<T>();
    man.setPrintTokenFilter(filter);
    assertEquals(expected, man.transform(input), message);
    return man;
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
    // test that the state is reset on the subfilters
  }

  @Test
  void testSetCollector() {
    // test that the collector is set on the subfilters and that all of them can
    // access job parameters
  }

  @Test
  void testSetConjunction() {
    // test AND/OR behavior
  }

  @Test
  void testSetShortCircuit() {
    // test shortcircuiting enabled/disabled
  }
}
