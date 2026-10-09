package io.github.douira.glsl_transformer.ast.typing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.DeclarationMember;
import io.github.douira.glsl_transformer.ast.node.expression.binary.AssignmentExpression;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.ASTParser;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.test_util.TestResourceManager;
import io.github.douira.glsl_transformer.test_util.TestResourceManager.DirectoryLocation;

/**
 * Compares the types that the type analysis infers with the types that glslang
 * infers for the same files of its test suite.
 * <p>
 * The {@code TypeDifferentialTest.glslang} file contains facts that were
 * extracted from the typed AST dumps in {@code Test/baseResults} of glslang for
 * the files that glslang accepts, that do not use the preprocessor and for
 * which the type analysis has no diagnostics:
 * <ul>
 * <li>{@code var name type}: every use of a variable with this name has this
 * type. Variables with struct or block types and names that are used for
 * variables of different types are left out.</li>
 * <li>{@code fn name type}: the functions with this name return this type.</li>
 * <li>{@code assign type count}: the number of assignments and initializations
 * of this type.</li>
 * </ul>
 * The comparison is experimental: it does not match individual expressions but
 * only checks these facts, and only where the type analysis knows the complete
 * type. The files in {@code TypeDifferentialTest.exclusions} are known to
 * differ and are not compared.
 */
public class TypeDifferentialTest {
  private static final Path DIRECTORY = Paths.get("src", "test", "java",
      TypeDifferentialTest.class.getPackageName().replace('.', '/'), "__snapshots__");

  private static void add(Map<String, Set<String>> facts, String key, Type type) {
    facts.computeIfAbsent(key, k -> new TreeSet<>()).add(type.getTypeName().replace("[?]", "[]"));
  }

  /**
   * Computes the facts of a file in the same form as the glslang facts. Facts
   * that cannot be compared map to more than one value.
   */
  private static Map<String, Set<String>> factsOf(ASTParser parser, String name, String content) {
    var tree = parser.parseTranslationUnit(RootSupplier.DEFAULT, content);
    var stage = ShaderStage.fromExtension(name.substring(name.lastIndexOf('.') + 1));
    var analysis = new TypeAnalyzer()
        .setEnvironment(TypeEnvironment.of(tree).withStage(stage))
        .analyze(tree);
    assertTrue(analysis.diagnostics().isEmpty(), name);

    var facts = new TreeMap<String, Set<String>>();
    for (var symbol : analysis.symbols.values()) {
      if (symbol instanceof VariableSymbol variable) {
        add(facts, "var " + variable.name(), variable.type());
      }
    }
    for (var signature : analysis.signatures.values()) {
      if (signature.declaration() != null) {
        add(facts, "fn " + signature.name(), signature.returnType());
      }
    }

    // glslang prints an assignment for every initialization that is not folded
    // into a constant
    var assignments = new TreeMap<String, Integer>();
    ASTWalker.walk(new ASTListener() {
      private void count(ASTNode node) {
        assignments.merge(analysis.typeOf(node).getTypeName(), 1, Integer::sum);
      }

      @Override
      public void enterAssignmentExpression(AssignmentExpression node) {
        count(node);
      }

      @Override
      public void enterDeclarationMember(DeclarationMember node) {
        if (node.getInitializer() != null && !((VariableSymbol) analysis.symbolOf(node)).isConstant()) {
          count(node);
        }
      }

      @Override
      public void enterIterationConditionInitializer(IterationConditionInitializer node) {
        count(node);
      }
    }, tree);
    assignments.forEach((type, count) -> facts.put("assign " + type, Set.of(count.toString())));
    return facts;
  }

  @Test
  void testAgainstGlslang() throws IOException {
    var parser = new ASTParser();
    parser.getLexer().enableAllFlags();
    parser.setSLLOnly();
    var contents = new HashMap<String, String>();
    TestResourceManager.getDirectoryResources(DirectoryLocation.GLSLANG_TESTS)
        .forEach(resource -> contents.put(resource.getScenarioName(), resource.content()));

    var exclusions = new HashSet<String>();
    for (var line : Files.readAllLines(DIRECTORY.resolve("TypeDifferentialTest.exclusions"))) {
      if (!line.isBlank() && !line.startsWith("//")) {
        exclusions.add(line.split(" ", 2)[0]);
      }
    }

    var mismatches = new ArrayList<String>();
    var differing = new TreeSet<String>();
    var compared = 0;
    var files = 0;
    String file = null;
    Map<String, Set<String>> facts = null;
    for (var line : Files.readAllLines(DIRECTORY.resolve("TypeDifferentialTest.glslang"))) {
      if (line.startsWith("# ")) {
        file = line.substring(2);
        facts = exclusions.contains(file) ? null : factsOf(parser, file, contents.get(file));
        files += facts == null ? 0 : 1;
      } else if (facts != null) {
        var separator = line.lastIndexOf(' ');
        var key = line.substring(0, separator);
        var expected = line.substring(separator + 1);
        var actual = facts.get(key);

        // assignments of a type that are missing entirely are a difference too
        if (actual == null && key.startsWith("assign ")) {
          actual = Set.of("0");
        }
        if (actual != null && actual.size() == 1) {
          compared++;
          if (!actual.contains(expected)) {
            mismatches.add(file + ": " + key + " is " + actual.iterator().next()
                + " but glslang has " + expected);
            differing.add(file);
          }
        }
      }
    }
    assertEquals(List.of(), mismatches, "Files with differences: " + differing);
    assertTrue(files > 60, "Only " + files + " files were compared");
    assertTrue(compared > 700, "Only " + compared + " facts were compared");
  }
}
