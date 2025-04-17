package io.github.douira.glsl_transformer.test_util;

import java.nio.file.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Knows about the various code resource files and can load them.
 */
public class TestResourceManager extends TestResourceManagerBase {
  public enum FileLocation {
    TINY("/tiny.glsl"),
    DIRECTIVE_TEST("/directiveTest.glsl"),
    SHADER("/shader.glsl"),
    EXTERNAL_DECLARATIONS("/externalDeclarations.glsl"),
    TYPE_TEST("/typeTest.glsl"),
    UNIFORM_TEST("/uniformTest.glsl"),
    MATRIX_PARSE_TEST("/glslang-test/matrix.frag"),
    GRAMMAR_DEBUG("/grammarDebug.glsl"),
    LONG_EXPRESSION_TEST("/longExpressionTest.glsl"),
    DEEP_EXPRESSION_TEST("/deepExpressionTest.glsl"),
    DEEP_PAREN_EXPRESSION_TEST("/deepParenExpressionTest.glsl"),
    DEEP_STATEMENT_TEST("/deepStatementTest.glsl"),

    TEST_1("/unlicensed/test.glsl"),
    TEST_2("/unlicensed/test2.glsl"),
    COMMENT_TEST("/commentTest.glsl"),

    BENCHMARK_KAPPA_SHORT("/unlicensed/composite3.glsl"),
    BENCHMARK_RRE("/unlicensed/rre.glsl"),
    BENCHMARK_SILDURS("/unlicensed/sildurs.glsl"),
    BENCHMARK_APOLLO_RT("/unlicensed/ApolloRT.glsl"),
    BENCHMARK_COMPLEMENTARY("/unlicensed/complementary.glsl"),
    BENCHMARK_SOFTVOXELS("/unlicensed/SoftVoxels.glsl"),
    BENCHMARK_UNKNOWN("/unlicensed/unknown.glsl"),
    BENCHMARK_MOLLYVX("/unlicensed/MollyVX.glsl"),
    BENCHMARK_SEUS_PTGI_HRR("/unlicensed/SEUS_PTGI_HRR.glsl"),
    BENCHMARK_SEUS_RENEWED("/unlicensed/SEUS_Renewed.glsl"),
    BENCHMARK_NOSTALGIAVX("/unlicensed/NostalgiaVX.glsl");

    public final String path;

    FileLocation(String path) {
      this.path = path;
    }
  }

  public enum DirectoryLocation {
    GLSLANG_TESTS("/glslang-test", Set.of("ray", "preprocessor", "badChars"));

    public final String path;
    public final Set<String> excludeInFiles;

    DirectoryLocation(String path, Set<String> excludeInFiles) {
      this.path = path;
      
      // make sure the excludes are lower case and not empty
      this.excludeInFiles = excludeInFiles.stream()
          .map(String::toLowerCase)
          .filter(exclude -> !exclude.isEmpty())
          .collect(Collectors.toSet());
    }
  }

  public record Resource(Path path, String content) {
    public String getScenarioName() {
      return path().getFileName().toString();
    }
  }

  private TestResourceManager() {
    super();
  }

  public static Resource getResource(FileLocation location) {
    return TestResourceManagerBase.getRelativePathResource(location.path);
  }

  public static Stream<Resource> getDirectoryResources(DirectoryLocation location) {
    return getDirectoryResources(location.path, location.excludeInFiles);
  }
}
