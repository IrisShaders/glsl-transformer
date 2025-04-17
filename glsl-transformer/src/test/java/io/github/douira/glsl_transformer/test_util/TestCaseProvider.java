package io.github.douira.glsl_transformer.test_util;

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
  public static enum Spacing {
    TRIMMED_TRAILING_NEWLINE {
      @Override
      public String process(String input) {
        return input.trim() + '\n';
      }
    },
    TRIM_SINGLE_BOTH {
      @Override
      public String process(String input) {
        return input.substring(1, input.length() - 1);
      }
    },
    NONE {
      @Override
      public String process(String input) {
        return input;
      }
    };

    public abstract String process(String input);
  }

  private record TestCase(String testCaseSet, String scenario, String content, String output) {
  };

  private static final Map<Path, List<TestCase>> TEST_CASE_CACHE = new HashMap<>();

  private String testCaseSet;
  private Spacing spacing;

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
        .map(testCase -> Arguments.of(
            testCase.scenario(), testCase.content(), testCase.output()));
  }

  @Override
  public void accept(TestCaseSource source) {
    if (!source.caseSet().isBlank()) {
      testCaseSet = source.caseSet();
    }
    spacing = source.spacing();
  }

  private List<TestCase> getTestCases(Path file) throws IOException {
    var testCases = TEST_CASE_CACHE.get(file);
    if (testCases == null) {
      var content = Files.readString(file);

      // parse the testcases
      var rawCases = content.split("§");
      testCases = new ArrayList<>(rawCases.length - 1);
      for (var i = 1; i < rawCases.length; i++) {
        var rawCase = rawCases[i];
        var segments = rawCase.split(" |:", 3);
        if (segments.length < 3) {
          continue;
        }
        var input = segments[2];
        var inputSegments = input.split("===", 2);
        testCases.add(new TestCase(
            segments[0],
            segments[1],
            spacing.process(inputSegments[0]),
            spacing.process(
                inputSegments.length > 1 ? inputSegments[1] : inputSegments[0])));
      }

      TEST_CASE_CACHE.put(file, testCases);
    }

    return testCases;
  }
}
