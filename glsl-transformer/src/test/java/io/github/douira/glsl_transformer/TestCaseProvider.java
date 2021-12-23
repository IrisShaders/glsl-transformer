package io.github.douira.glsl_transformer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import au.com.origin.snapshots.annotations.SnapshotName;

public class TestCaseProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    // TODO: read config from snapshot properties
    // find the test file and read the corresponding testcase file

    var fileUnderTest = Paths.get(context.getRequiredTestClass().getName()
        .replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
    var contents = Files.readString(
        Paths.get("src", "test", "java").resolve(
            fileUnderTest.getParent()
                .resolve("__snapshots__")
                .resolve(fileUnderTest.getFileName() + ".cases")));

    // parse the testcases
    var snapshotName = context.getRequiredTestMethod().getAnnotation(SnapshotName.class).value();
    var rawCases = contents.trim().split("§");
    var testCases = Stream.<Arguments>builder();
    for (var i = 1; i < rawCases.length; i++) {
      var rawCase = rawCases[i];
      var segments = rawCase.split(" |:", 3);
      var testCaseSnapshotName = segments[0];
      var scenario = segments[1];
      var testCase = segments[2].trim();

      // only collect test cases relevant to this snapshot
      // TODO: caching of the loaded file for other tests
      if (!testCaseSnapshotName.equals(snapshotName)) {
        continue;
      }

      testCases.accept(Arguments.of(scenario, testCase));
    }

    return testCases.build();
  }
}
