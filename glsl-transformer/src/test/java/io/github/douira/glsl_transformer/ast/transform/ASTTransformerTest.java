package io.github.douira.glsl_transformer.ast.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.query.*;
import io.github.douira.glsl_transformer.util.Type;

public class ASTTransformerTest {
  ASTTransformer transformer;

  @BeforeEach
  public void setUp() {
    transformer = new ASTTransformer();
  }

  void assertInjectExternalDeclaration(int index, String input, String output) {
    transformer.setTransformation(translationUnit -> {
      translationUnit.children.add(index, transformer.parseNode(
          "int a;",
          translationUnit,
          GLSLParser::externalDeclaration,
          ASTBuilder::visitExternalDeclaration));
    });
    assertEquals(output, transformer.transform(input));
  }

  @Test
  public void testInsertion() {
    assertInjectExternalDeclaration(0,
        "int b;\nint c;\n",
        "int a;\nint b;\nint c;\n");
    assertInjectExternalDeclaration(1,
        "int b;\nint c;",
        "int b;\nint a;\nint c;\n");
    assertInjectExternalDeclaration(2,
        "int b;\nint c;",
        "int b;\nint c;\nint a;\n");
  }

  @Test
  public void testIdentifierQuery() {
    transformer.setTransformation(translationUnit -> {
      translationUnit.getRoot().identifierIndex.index.prefixMap("a").values()
          .stream().forEach(Index.<Identifier>iterate(node -> node.name += "b"));
    });
    assertEquals(
        "int ab, ab, c;\n",
        transformer.transform("int a, a, c;\n"));
    assertEquals(
        "int ab = 4 + ab + aab, ab, c;\n",
        transformer.transform("int a = 4 + a + aa, a, c;\n"));
  }

  @Test
  public void testNodeQuery() {
    transformer.setTransformation(translationUnit -> {
      translationUnit.getRoot().nodeIndex.get(LiteralExpression.class)
          .stream().forEach(literal -> {
            literal.integerValue++;
          });
    });
    assertEquals(
        "int a = 2, b = 3, c = 4;\n",
        transformer.transform("int a = 1, b = 2, c = 3;\n"));
    assertEquals(
        "int a = 2, 3, 4;\n",
        transformer.transform("int a = 1, 2, 3;\n"));
  }

  @Test
  public void testNodeQueryAfterModification() {
    transformer.setTransformation(translationUnit -> {
      Root.<LiteralExpression>indexNodes(
          translationUnit,
          register -> translationUnit.getRoot().nodeIndex
              .get(SequenceExpression.class)
              .stream().forEach(sequence -> sequence.expressions.add(
                  register.apply(
                      new LiteralExpression(Type.INT32, 1)))));
      translationUnit.getRoot().nodeIndex.get(LiteralExpression.class)
          .stream().forEach(literal -> literal.integerValue++);
    });
    assertEquals(
        "int a = 2, b = 3, c = 4, 2;\n",
        transformer.transform("int a = 1, b = 2, c = 3;\n"));
    assertEquals(
        "int a = 2, 3, 4, 2;\n",
        transformer.transform("int a = 1, 2, 3;\n"));
  }
}
