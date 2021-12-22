package io.github.douira.glsl_transformer;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import au.com.origin.snapshots.annotations.SnapshotName;

public class TestCaseProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    var snapshotName = context.getRequiredTestMethod().getAnnotation(SnapshotName.class).value();
    return Stream.of(
        Arguments.of("empty", ""),
        Arguments.of("simple", "#version 5432\nbar;"),
        Arguments.of("2", "bar;"));
  }
}
