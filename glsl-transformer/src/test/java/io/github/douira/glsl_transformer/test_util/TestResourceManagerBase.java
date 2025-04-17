package io.github.douira.glsl_transformer.test_util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
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

  static Stream<Resource> getDirectoryResources(String directoryPath, Set<String> excludeInFiles) {
    var resourcePath = makePathAbsoluteInResourceDir(directoryPath);
    try {
      return Files.walk(resourcePath).filter(path -> {
            if (!allowPath(path, excludeInFiles)) {
              return false;
            }

            return !path.toFile().isDirectory();
          })
          .map(TestResourceManagerBase::getPathResource)
          .filter(resource -> resource.content() != null);
    } catch (IOException e) {
      throw new RuntimeException("The resource directory at " + resourcePath + " could not be enumerated.", e);
    }
  }

  static Resource getRelativePathResource(String relativeResourcePath) {
    return getPathResource(makePathAbsoluteInResourceDir(relativeResourcePath));
  }

  private static Resource getPathResource(Path path) {
    if (RESOURCE_CACHE.containsKey(path)) {
      return RESOURCE_CACHE.get(path);
    }
    try {
      var resource = new Resource(path, Files.readString(path, StandardCharsets.UTF_8));
      RESOURCE_CACHE.put(path, resource);
      return resource;
    } catch (IOException e) {
      throw new RuntimeException("The file at " + path + " could not be read.");
    }
  }

  private static Path makePathAbsoluteInResourceDir(String relativeResourcePath) {
    try {
      return Paths.get(TestResourceManager.class.getResource(relativeResourcePath).toURI());
    } catch (URISyntaxException | NullPointerException e) {
      throw new RuntimeException("The resource " + relativeResourcePath + " could not be found.", e);
    } catch (Exception e) {
      return Paths.get(relativeResourcePath);
    }
  }
}
