package io.github.douira.glsl_transformer;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.support.AnnotationConsumer;

import au.com.origin.snapshots.annotations.SnapshotName;

public class TestCaseProvider implements ArgumentsProvider, AnnotationConsumer<TestCaseSource> {
  private static record TestCase(String testCaseSet, String scenario, String content) {
  };

  private static final Map<Path, List<TestCase>> TEST_CASE_CACHE = new HashMap<>();

  private String testCaseSet;

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    // TODO: read snapshot files config from snapshot properties
    var fileUnderTest = Paths.get(context.getRequiredTestClass().getName()
        .replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
    var testCaseFile = Paths.get("src", "test", "java").resolve(
        fileUnderTest.getParent()
            .resolve("__snapshots__")
            .resolve(fileUnderTest.getFileName() + ".cases"));

    var testCases = getTestCases(testCaseFile);

    // get the name of the snapshot from the method or the annotation
    var useSetName = Optional
        .ofNullable(testCaseSet)
        .or(() -> Optional
            .ofNullable(context
                .getRequiredTestMethod()
                .getAnnotation(SnapshotName.class))
            .map(SnapshotName::value))
        .orElseGet(
            () -> context.getRequiredTestMethod().getName());

    // filter and map the test cases into an argument stream
    return testCases
        .stream()
        .filter(testCase -> testCase.testCaseSet().equals(useSetName))
        .map(testCase -> Arguments.of(testCase.scenario(), testCase.content()));
  }

  @Override
  public void accept(TestCaseSource t) {
    if (!t.value().isBlank()) {
      testCaseSet = t.value();
    }
  }

  private List<TestCase> getTestCases(Path file) throws IOException {
    var testCases = TEST_CASE_CACHE.get(file);
    if (testCases == null) {
      var content = Files.readString(file);

      // parse the testcases
      var rawCases = content.trim().split("§");
      testCases = new ArrayList<>(rawCases.length - 1);
      for (var i = 1; i < rawCases.length; i++) {
        var rawCase = rawCases[i];
        var segments = rawCase.split(" |:", 3);

        // ensure there is a trailing newline or parsing will break
        testCases.add(new TestCase(
            segments[0], segments[1], segments[2].trim() + "\n"));
      }

      TEST_CASE_CACHE.put(file, testCases);
    }

    return testCases;
  }
}
