package io.github.douira.glsl_transformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import au.com.origin.snapshots.annotations.SnapshotName;

public class TestCaseProvider implements ArgumentsProvider {
  private record TestCase(String snapshotName, String scenario, String content) {
  };

  static final Map<Path, List<TestCase>> testCaseCache = new HashMap<>();

  private List<TestCase> getTestCases(Path file) throws IOException {
    var testCases = testCaseCache.get(file);
    if (testCases == null) {
      var contents = Files.readString(file);

      // parse the testcases
      var rawCases = contents.trim().split("§");
      testCases = new ArrayList<>(rawCases.length - 1);
      for (var i = 1; i < rawCases.length; i++) {
        var rawCase = rawCases[i];
        var segments = rawCase.split(" |:", 3);
        testCases.add(new TestCase(
            segments[0], segments[1], segments[2].trim()));
      }

      testCaseCache.put(file, testCases);
    }

    return testCases;
  }

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
    var snapshotName = Optional.ofNullable(
        context
            .getRequiredTestMethod().getAnnotation(SnapshotName.class))
        .map(SnapshotName::value)
        .orElseGet(
            () -> context.getRequiredTestMethod().getName());

    // filter and map the test cases into an argument stream
    return testCases
        .stream()
        .filter(testCase -> testCase.snapshotName().equals(snapshotName))
        .map(testCase -> Arguments.of(testCase.scenario(), testCase.content()));
  }
}
