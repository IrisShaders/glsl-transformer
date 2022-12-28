package io.github.douira.glsl_transformer;

import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.annotations.SnapshotName;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import io.github.douira.glsl_transformer.parser.EnhancedParser;
import io.github.douira.glsl_transformer.test_util.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.*;

@ExtendWith({ SnapshotExtension.class })
public class CSTTreeTest {
  private Expect expect;

  @Test
  @SnapshotName("testParseTree")
  void testParseTree() {
    var parser = new EnhancedParser();
    parser.getLexer().enableIncludeDirective = true;
    parser.setThrowParseErrors(false);
    var walker = new ParseTreeWalker();

    Stream.concat(Stream.of(
        TestResourceManager.getResource(FileLocation.UNIFORM_TEST),
        TestResourceManager.getResource(FileLocation.MATRIX_PARSE_TEST)),
        TestResourceManager
            .getDirectoryResources(DirectoryLocation.GLSLANG_TESTS))
        .forEach(resource -> {
          var content = resource.content();
          var listener = new PrintTreeSnapshot();
          walker.walk(listener, parser.parse(content));
          expect
              .scenario(resource.getScenarioName())
              .toMatchSnapshot(SnapshotUtil.inputOutputSnapshot(content, listener.builder.toString()));
        });
  }
}
