package io.github.douira.glsl_transformer.ast.node.declaration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.print.ASTPrinter;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class VariableDeclarationTest extends TestWithSingleASTTransformer {
  private VariableDeclaration parse(String input) {
    var tu = p.parseSeparateTranslationUnit(input);
    assertEquals(1, tu.getChildren().size());
    return tu.getRoot().nodeIndex.getUnique(VariableDeclaration.class);
  }

  @Test
  void testLayoutDefaultsAreQualifierDefaults() {
    // every form of layout defaults is parsed as a variable declaration without names
    for (var input : List.of(
        "layout(std140) uniform;",
        "layout(row_major) buffer;",
        "layout(triangles) in;",
        "layout(triangle_strip, max_vertices = 4) out;",
        "layout(local_size_x = 8, local_size_y = 8) in;",
        "layout(early_fragment_tests) in;")) {
      var declaration = parse(input);
      assertTrue(declaration.isQualifierDefault(), input);
      assertTrue(declaration.getNames().isEmpty(), input);
      assertNotNull(declaration.getLayoutQualifier(), input);
      assertSame(declaration.getTypeQualifier(), declaration.getLayoutQualifier().getParent(), input);
    }
  }

  @Test
  void testLayoutQualifierAccess() {
    var declaration = parse("layout(triangle_strip, max_vertices = 4) out;");
    var parts = declaration.getLayoutQualifier().getParts();
    assertEquals(2, parts.size());
    assertEquals("triangle_strip", ((NamedLayoutQualifierPart) parts.get(0)).getName().getName());
    assertEquals("max_vertices", ((NamedLayoutQualifierPart) parts.get(1)).getName().getName());
    assertEquals("4", ASTPrinter.printSimple(((NamedLayoutQualifierPart) parts.get(1)).getExpression()));

    // the first layout qualifier is returned regardless of its position
    var later = parse("flat layout(a) layout(b) in;");
    assertEquals("a", ((NamedLayoutQualifierPart) later.getLayoutQualifier().getParts().get(0)).getName().getName());

    assertNull(parse("centroid out;").getLayoutQualifier());
    assertNull(parse("invariant gl_Position;").getLayoutQualifier());
  }

  @Test
  void testStorageType() {
    var input = parse("layout(triangles) in;");
    assertTrue(input.hasStorageType(StorageType.IN));
    assertFalse(input.hasStorageType(StorageType.OUT));
    assertFalse(input.hasStorageType(StorageType.UNIFORM));

    var multiple = parse("centroid out;");
    assertTrue(multiple.hasStorageType(StorageType.CENTROID));
    assertTrue(multiple.hasStorageType(StorageType.OUT));
    assertFalse(multiple.hasStorageType(StorageType.IN));

    assertFalse(parse("invariant gl_Position;").hasStorageType(StorageType.IN));
    assertTrue(parse("layout(std140) uniform;").hasStorageType(StorageType.UNIFORM));
    assertTrue(parse("layout(std430) buffer;").hasStorageType(StorageType.BUFFER));
  }

  @Test
  void testNamedDeclarationsAreNotQualifierDefaults() {
    assertFalse(parse("invariant gl_Position;").isQualifierDefault());
    assertFalse(parse("precise a, b;").isQualifierDefault());
    assertEquals(2, parse("precise a, b;").getNames().size());

    // a name makes it a declaration that applies the qualifiers to the variable
    var named = parse("layout(location = 1) in existing;");
    assertFalse(named.isQualifierDefault());
    assertNotNull(named.getLayoutQualifier());
    assertTrue(named.hasStorageType(StorageType.IN));
  }

  @Test
  void testLayoutDefaultsReprint() {
    var input = "layout(std140) uniform;\nlayout(triangles) in;\nlayout(triangle_strip, max_vertices = 4) out;\n";
    assertEquals(input, ASTPrinter.printSimple(p.parseSeparateTranslationUnit(input)));
  }
}
