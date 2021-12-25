package io.github.douira.glsl_transformer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Knows about the various code resource files and can load them.
 */
public class TestResources {
  private static final Map<Path, Resource> resourceCache = new HashMap<>();

  public static enum FileLocation {
    TINY("/tiny.glsl"),
    DIRECTIVE_TEST("/directiveTest.glsl"),
    SHADER("/shader.glsl"),
    EXTERNAL_DECLARATIONS("/externalDeclarations.glsl"),
    KAPPA("/unlicensed/composite3.glsl"),
    BENCHMARK1("/unlicensed/benchmark1.glsl"),
    BENCHMARK2("/unlicensed/benchmark2.glsl"),
    TEST("/unlicensed/test.glsl"),
    TYPE_TEST("/typeTest.glsl");

    Path path;

    private FileLocation(String path) {
      this.path = Paths.get(path);
    }
  }

  public static enum DirectoryLocation {
    GLSLANG_TESTS("/glslang-test", Set.of("ray", "preprocessor"));

    Path path;
    Set<String> excludeInFiles;

    private DirectoryLocation(String path, Set<String> excludeInFiles) {
      this.path = Paths.get(path);
      this.excludeInFiles = excludeInFiles;
    }
  }

  public record Resource(Path path, String content) {
  }

  private TestResources() {
  }

  public static Resource getResource(FileLocation location) {
    return getPathResource(location.path);
  }

  public static Stream<Resource> getDirectoryResources(DirectoryLocation location) {
    return getDirectoryContents(location.path, location.excludeInFiles);
  }

  private static Stream<Resource> getDirectoryContents(Path directory, Set<String> excludeInFiles) {
    var resourcePath = getResourcePath(directory);
    return assertDoesNotThrow(
        () -> Files.walk(resourcePath),
        "The resource directory at " + resourcePath + " could not be enumerated.")
            .filter(path -> {
              var fileName = path.getFileName().toString().toLowerCase();
              for (var excluded : excludeInFiles) {
                if (fileName.contains(excluded)) {
                  return false;
                }
              }

              var file = path.toFile();
              if (file.isDirectory()) {
                return false;
              }

              return true;
            })
            .map(TestResources::getPathResource);
  }

  private static Resource getPathResource(Path path) {
    var result = resourceCache.get(path);
    if (result == null) {
      result = new Resource(path, readResourceFile(path));
    }
    return result;
  }

  private static String readResourceFile(Path path) {
    var resourcePath = getResourcePath(path);
    return assertDoesNotThrow(() -> Files.readString(path),
        "The file at " + resourcePath.toString() + " could not be read.");
  }

  private static Path getResourcePath(Path resource) {
    return getResourcePath(resource.toString());
  }

  private static Path getResourcePath(String resource) {
    return assertDoesNotThrow(
        () -> Paths.get(TestResources.class.getResource(resource).toURI()),
        "The resource at " + resource + " could not be found.");
  }
}
