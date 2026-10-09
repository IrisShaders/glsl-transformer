package io.github.douira.glsl_transformer.ast.typing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.ASTParser;
import io.github.douira.glsl_transformer.test_util.TestResourceManager;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.DirectoryLocation;

/**
 * Runs the type analysis on the test files of glslang.
 * <p>
 * The outcome of the analysis of every file is pinned in the
 * {@code TypeCorpusTest.outcomes} file: the diagnostic codes with their counts,
 * {@code ok} if there are none or {@code unparseable} if the file cannot be
 * parsed. To update the file after a deliberate change of the analysis, run
 * this test with the environment variable {@code UPDATE_TYPE_CORPUS} set.
 * <p>
 * The {@code TypeCorpusTest.glslang} file records for every file if glslang
 * accepts it without errors. It is derived from the reference results in
 * {@code Test/baseResults} of glslang for the files that are identical to the
 * ones there. Files that glslang accepts and that do not depend on the
 * preprocessor may not have diagnostics, unless they are on the allowlist in
 * {@code TypeCorpusTest.allowlist}, which gives a reason for each file.
 */
public class TypeCorpusTest {
  private static final Path DIRECTORY = Paths.get("src", "test", "java",
      TypeCorpusTest.class.getPackageName().replace('.', '/'), "__snapshots__");
  private static final Path OUTCOMES = DIRECTORY.resolve("TypeCorpusTest.outcomes");
  private static final Path GLSLANG = DIRECTORY.resolve("TypeCorpusTest.glslang");
  private static final Path ALLOWLIST = DIRECTORY.resolve("TypeCorpusTest.allowlist");

  // macros and conditional compilation are not evaluated by the parser
  private static final Pattern PREPROCESSOR = Pattern.compile(
      "(?m)^[ \\t]*#[ \\t]*(define|undef|if|ifdef|ifndef|else|elif|endif)\\b");

  private static Map<String, String> readTable(Path path) throws IOException {
    var table = new TreeMap<String, String>();
    for (var line : Files.readAllLines(path)) {
      if (!line.isBlank() && !line.startsWith("//")) {
        var parts = line.split(" ", 2);
        table.put(parts[0], parts[1]);
      }
    }
    return table;
  }

  private static String analyze(ASTParser parser, String name, String content) {
    TranslationUnit tree;
    try {
      tree = parser.parseTranslationUnit(RootSupplier.DEFAULT, content);
    } catch (RuntimeException e) {
      return "unparseable";
    }

    // the stage is given by the file extension
    var stage = ShaderStage.fromExtension(name.substring(name.lastIndexOf('.') + 1));
    var analysis = new TypeAnalyzer()
        .setEnvironment(TypeEnvironment.of(tree).withStage(stage))
        .analyze(tree);
    // every expression in the tree has a type
    assertEquals(List.of(), TypingTestUtil.untypedExpressions(tree, analysis), name);

    var counts = new TreeMap<DiagnosticCode, Integer>();
    for (var diagnostic : analysis.diagnostics()) {
      counts.merge(diagnostic.code(), 1, Integer::sum);
    }
    return counts.isEmpty() ? "ok"
        : counts.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining(","));
  }

  @Test
  void testGlslangCorpus() throws IOException {
    var parser = new ASTParser();
    parser.getLexer().enableAllFlags();
    parser.setSLLOnly();

    // the analysis never throws
    var outcomes = new TreeMap<String, String>();
    var preprocessed = new HashSet<String>();
    TestResourceManager.getDirectoryResources(DirectoryLocation.GLSLANG_TESTS).forEach(resource -> {
      var name = resource.getScenarioName();
      outcomes.put(name, assertDoesNotThrow(() -> analyze(parser, name, resource.content()), name));
      if (PREPROCESSOR.matcher(resource.content()).find()) {
        preprocessed.add(name);
      }
    });
    assertTrue(outcomes.size() > 600, "The corpus should have been found");

    if (System.getenv("UPDATE_TYPE_CORPUS") != null) {
      Files.write(OUTCOMES, outcomes.entrySet().stream()
          .map(entry -> entry.getKey() + " " + entry.getValue()).toList());
    }

    // files that glslang accepts have no diagnostics
    var glslang = readTable(GLSLANG);
    var allowlist = readTable(ALLOWLIST);
    var unexpected = new ArrayList<String>();
    var obsolete = new ArrayList<String>();
    var clean = 0;
    for (var entry : outcomes.entrySet()) {
      var name = entry.getKey();
      var checked = "accepted".equals(glslang.get(name)) && !preprocessed.contains(name);
      var isClean = entry.getValue().equals("ok");
      if (checked && isClean) {
        clean++;
      }
      if (checked && !isClean && !allowlist.containsKey(name)) {
        unexpected.add(name + " " + entry.getValue());
      }
      if (allowlist.containsKey(name) && (!checked || isClean)) {
        obsolete.add(name);
      }
    }
    assertEquals(List.of(), unexpected,
        "Files that glslang accepts should not have diagnostics unless they are on the allowlist");
    assertEquals(List.of(), obsolete, "The allowlist should only contain files that need to be on it");
    assertTrue(clean > 250, "Most accepted files should be analyzed without diagnostics but only "
        + clean + " are");

    // the outcome of each file is pinned
    assertEquals(
        Files.readAllLines(OUTCOMES).stream().collect(Collectors.joining("\n")),
        outcomes.entrySet().stream()
            .map(entry -> entry.getKey() + " " + entry.getValue()).collect(Collectors.joining("\n")));
  }
}
