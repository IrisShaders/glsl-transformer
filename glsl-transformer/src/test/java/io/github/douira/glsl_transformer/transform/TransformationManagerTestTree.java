package io.github.douira.glsl_transformer.transform;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.TestResourceManager.*;

@ExtendWith({ SnapshotExtension.class })
public class TransformationManagerTestTree {
  private Expect expect;

  @Test
  @SnapshotName("testParseTree")
  void testParseTree() {
    var man = new TransformationManager<WrappedParameters<StringBuilder>>(false);
    man.addConcurrent(new PrintTreeSnapshot());

    Stream.concat(Stream.of(
        TestResourceManager.getResource(FileLocation.UNIFORM_TEST),
        TestResourceManager.getResource(FileLocation.MATRIX_PARSE_TEST)),
        TestResourceManager
            .getDirectoryResources(DirectoryLocation.GLSLANG_TESTS))
        .forEach(resource -> {
          var content = resource.content();
          var builder = new StringBuilder();
          man.transform(content, new WrappedParameters<>(builder));
          expect.scenario(resource.getScenarioName())
              .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(content, builder.toString()));
        });
  }
}
