package io.github.douira.glsl_transformer.cst.transform;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.job_parameter.WrappedParameters;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

@ExtendWith({ SnapshotExtension.class })
public class CSTTransformerTestTree {
  private Expect expect;

  @Test
  @SnapshotName("testParseTree")
  void testParseTree() {
    var transformer = new CSTTransformer<WrappedParameters<StringBuilder>>(false);
    transformer.setSLLOnly();
    transformer.addConcurrent(new PrintTreeSnapshot());

    Stream.concat(Stream.of(
        TestResourceManager.getResource(FileLocation.UNIFORM_TEST),
        TestResourceManager.getResource(FileLocation.MATRIX_PARSE_TEST)),
        TestResourceManager
            .getDirectoryResources(DirectoryLocation.GLSLANG_TESTS))
        .forEach(resource -> {
          var content = resource.content();
          var builder = new StringBuilder();
          transformer.transform(content, new WrappedParameters<>(builder));
          expect.scenario(resource.getScenarioName())
              .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(content, builder.toString()));
        });
  }
}
