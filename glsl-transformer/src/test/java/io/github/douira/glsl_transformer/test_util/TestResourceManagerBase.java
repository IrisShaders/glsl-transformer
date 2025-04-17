package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.test_util.TestResourceManager.Resource;

/**
 * Knows about the various code resource files and can load them.
 */
public class TestResourceManagerBase {
  private static final Map<Path, Resource> RESOURCE_CACHE = new HashMap<>();
  private static final Set<String> GLOBAL_EXCLUDE = Set.of(".ds_store", "disable_");

  TestResourceManagerBase() {
  }

  private static boolean checkFileNameWith(String fileName, Set<String> excludes) {
    for (var excluded : excludes) {
      if (fileName.contains(excluded)) {
        return false;
      }
    }
    return true;
  }

  private static boolean allowPath(Path path, Set<String> excludeInFiles) {
    var fileName = path.getFileName().toString().toLowerCase();
    return checkFileNameWith(fileName, GLOBAL_EXCLUDE)
        && checkFileNameWith(fileName, excludeInFiles);
  }

  public static Stream<Resource> getDirectoryResources(Path directoryPath, Set<String> excludeInFiles) {
    var resourcePath = getResourcePath(directoryPath);
    return assertDoesNotThrow(
        () -> Files.walk(resourcePath),
        "The resource directory at " + resourcePath + " could not be enumerated.")
        .filter(path -> {
          if (!allowPath(path, excludeInFiles)) {
            return false;
          }

          var file = path.toFile();
          if (file.isDirectory()) {
            return false;
          }

          return true;
        })
        .map(TestResourceManagerBase::getPathResource)
        .filter(resource -> resource.content() != null);
  }

  static Resource getPathResource(Path path) {
    return Optional
        .ofNullable(RESOURCE_CACHE.get(path))
        .orElseGet(() -> new Resource(path, getFileContent(path)));
  }

  private static String getFileContent(Path path) {
    var resourcePath = getResourcePath(path);
    return assertDoesNotThrow(
        () -> {
          try {
            return Files.readString(resourcePath, StandardCharsets.UTF_8);
          } catch (MalformedInputException e) {
            return null;
          }
        },
        "The file at " + resourcePath.toString() + " could not be read.");
  }

  private static Path getResourcePath(Path resource) {
    return getResourcePath(resource.toString().replace('\\', '/'));
  }

  private static Path getResourcePath(String resource) {
    try {
      return Paths.get(TestResourceManager.class.getResource(resource).toURI());
    } catch (Exception e) {
      return Paths.get(resource);
    }
  }
}
