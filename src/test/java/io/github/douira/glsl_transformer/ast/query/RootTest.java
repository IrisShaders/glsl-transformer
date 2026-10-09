package io.github.douira.glsl_transformer.ast.query;

import static io.github.douira.glsl_transformer.test_util.AssertUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.AdditionExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective.ExtensionBehavior;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.NumericTypeSpecifier;
import io.github.douira.glsl_transformer.ast.transform.ASTInjectionPoint;
import io.github.douira.glsl_transformer.ast.typing.*;
import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.type.specifier.ArraySpecifier;
import io.github.douira.glsl_transformer.ast.query.match.*;
import io.github.douira.glsl_transformer.parser.ParseShape;
import io.github.douira.glsl_transformer.test_util.TestWithSingleASTTransformer;

public class RootTest extends TestWithSingleASTTransformer {
  @Test
  void testRename() {
    p.supplyRoot().indexSeparateTrees(register -> {
      var a = new Identifier("a");
      var b = new Identifier("b");
      register.apply(a);
      register.apply(b);
      var root = a.getRoot();
      var index = root.identifierIndex;
      assertTrue(index.has("a"));
      assertTrue(index.has("b"));
      assertFalse(index.has("c"));
      root.rename("a", "c");
      assertDoesNotThrow(() -> root.rename("foo", "c"));
      assertFalse(index.has("a"));
      assertTrue(index.has("b"));
      assertTrue(index.has("c"));
    });
  }

  @Test
  void testReplace() {
    p.setTransformation((tree, root) -> {
      root.process("foo",
          id -> id.getParent().replaceByAndDelete(
              p.parseExpression(root, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + fooo; ",
        "int x = foo + bar + fooo;");
  }

  @Test
  void testReplaceMultiple() {
    p.setRootSupplier(RootSupplier.PREFIX_UNORDERED);
    p.setTransformation((tree, root) -> {
      root.process(root.getPrefixIdentifierIndex().prefixQueryFlat("f"),
          id -> id.getParent().replaceByAndDelete(
              p.parseExpression(root, "bam + spam")));
    });
    assertTransform(
        "int x = bam + spam + bar + bam + spam; ",
        "int x = foo + bar + fan;");
  }

  @Test
  void testNullReplaceStream() {
    p.setTransformation((tree, root) -> {
      root.process(Stream.of((Identifier) null), id -> {
      });
    });
    assertDoesNotThrow(() -> p.transform(""));
  }

  @Test
  void testReplaceReferenceExpressionsPrefix() {
    p.setRootSupplier(RootSupplier.PREFIX_UNORDERED);
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(p,
          root.getPrefixIdentifierIndex().prefixQueryFlat("f"),
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + bam + spam; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testReplaceReferenceExpressionsExact() {
    p.setTransformation((tree, root) -> {
      root.replaceReferenceExpressions(p,
          "foo",
          "bam + spam");
    });
    assertTransform(
        "int foo = bam + spam + bar + fan; ",
        "int foo = foo + bar + fan;");
  }

  @Test
  void testHintedMatcherProcessing() {
    var matcher = new HintedMatcher<>("foo[1]", ParseShape.EXPRESSION, "foo");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testHintedMatcherProcessingHintSpecificity() {
    var matcher = new HintedMatcher<>("foo[1]", ParseShape.EXPRESSION, "bar");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + foo[1] + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessing() {
    var matcher = new AutoHintedMatcher<>("foo[1]", ParseShape.EXPRESSION);
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertTransform(
        "int foo = bam + bar + 4 + foo[4]; ",
        "int foo = bam + foo[1] + foo[4];");
  }

  @Test
  void testAutoHintedMatcherProcessingWildcard() {
    var matcher = new AutoHintedMatcher<>("a[___f + 5]", ParseShape.EXPRESSION, "___");
    p.setTransformation((tree, root) -> {
      root.replaceExpressionMatches(p, matcher, "bar + 4");
    });
    assertEquals("a", matcher.getHint());
    assertTransform(
        "int foo = bam + bar + 4 + a[bar + 4]; ",
        "int foo = bam + a[bar + 5] + a[bar + 4];");
  }

  @Test
  void testNullIndexes() {
    assertCall(3, (callback) -> {
      RootSupplier.EMPTY.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertThrows(NullPointerException.class, () -> index.has("a"));
        assertThrows(NullPointerException.class, () -> root.rename("foo", "c"));
        callback.run();
      });
      RootSupplier.ONLY_IDENTIFIER_INDEX.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertDoesNotThrow(() -> index.has("a"));
        assertThrows(NullPointerException.class, () -> root.nodeIndex.has(Identifier.class));
        callback.run();
      });
      RootSupplier.ONLY_NODE_INDEX.get().indexBuildSession((root) -> {
        new Identifier("a");
        new Identifier("b");

        var index = root.identifierIndex;
        assertThrows(NullPointerException.class, () -> index.has("a"));
        assertDoesNotThrow(() -> root.nodeIndex.has(Identifier.class));
        callback.run();
      });
    });
  }

  // array specifiers of type specifiers used to be skipped by the default
  // traversal, which left them and their contents in the indexes of the old root
  @ParameterizedTest
  @ValueSource(strings = {
      "vec3[N] x;",
      "sampler2D[N] s;",
      "S[N] v;",
      "struct { int a; }[N] t;" })
  void testTypeSpecifierArraySpecifierIndexed(String input) {
    var tu = p.parseSeparateTranslationUnit(input);
    var root = tu.getRoot();
    var other = p.parseSeparateTranslationUnit(";");
    var otherRoot = other.getRoot();
    var specifier = root.nodeIndex.getOne(ArraySpecifier.class);
    assertNotNull(specifier);

    var declaration = tu.getChildren().get(0);
    declaration.detach();
    other.getChildren().add(declaration);
    assertSame(otherRoot, specifier.getRoot());
    assertFalse(root.nodeIndex.has(ArraySpecifier.class));
    assertTrue(otherRoot.nodeIndex.has(ArraySpecifier.class));
    assertSame(specifier, otherRoot.nodeIndex.getOne(ArraySpecifier.class));

    // renaming through the new root reaches the identifier in the specifier
    otherRoot.rename("N", "M");
    assertTrue(otherRoot.identifierIndex.has("M"));
    assertFalse(otherRoot.identifierIndex.has("N"));

    declaration.detachAndDelete();
    assertFalse(otherRoot.nodeIndex.has(ArraySpecifier.class));
    assertFalse(otherRoot.identifierIndex.has("M"));
  }

  @Test
  void testWhileLoopDeclarationIndexed() {
    var tu = p.parseSeparateTranslationUnit("void main() { while (bool b = N) { } }");
    var root = tu.getRoot();
    var other = p.parseSeparateTranslationUnit(";");
    var otherRoot = other.getRoot();
    var initializer = root.nodeIndex.getOne(IterationConditionInitializer.class);
    assertNotNull(initializer);

    var function = tu.getChildren().get(0);
    function.detach();
    other.getChildren().add(function);
    assertSame(otherRoot, initializer.getRoot());
    assertFalse(root.nodeIndex.has(IterationConditionInitializer.class));
    assertTrue(otherRoot.nodeIndex.has(IterationConditionInitializer.class));
    assertTrue(otherRoot.identifierIndex.has("b"));
    assertFalse(root.identifierIndex.has("b"));

    function.detachAndDelete();
    assertFalse(otherRoot.nodeIndex.has(IterationConditionInitializer.class));
    assertFalse(otherRoot.identifierIndex.has("b"));
    assertFalse(otherRoot.identifierIndex.has("N"));
  }

  private static final String TYPED_SOURCE = """
      #version 330 core
      float value;
      void main() {
        value + 1;
      }
      """;

  private static Expression typedExpression(TranslationUnit tu) {
    return tu.getRoot().nodeIndex.getOne(AdditionExpression.class);
  }

  @Test
  void testTypeAnalysisIsCached() {
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var root = tu.getRoot();
    var expression = typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());
    var analysis = expression.getTypeAnalysis();
    assertSame(analysis, tu.getTypeAnalysis());
    assertSame(analysis, root.getTypeAnalysis(expression));
    assertSame(tu, analysis.getTop());
    assertSame(Version.GLSL33, analysis.getEnvironment().version());
    assertNull(tu.getType());

    // nothing changed so nothing is recomputed
    var count = root.getModificationCount();
    assertSame(NumericType.FLOAT32, expression.getType());
    assertEquals(count, root.getModificationCount());
    assertSame(analysis, expression.getTypeAnalysis());

    root.invalidateTypeAnalysis();
    assertTrue(root.getModificationCount() > count);
    assertNotSame(analysis, expression.getTypeAnalysis());
    assertSame(NumericType.FLOAT32, expression.getType());
  }

  @Test
  void testTypeRecomputesAfterInsert() {
    var tu = p.parseSeparateTranslationUnit(
        "#version 330 core\nvoid main() { undeclared + 1; }");
    var expression = typedExpression(tu);
    assertSame(ErrorType.INSTANCE, expression.getType());
    assertEquals(1, tu.getTypeAnalysis().diagnostics().size());

    tu.parseAndInjectNode(p, ASTInjectionPoint.BEFORE_DECLARATIONS, "uniform ivec2 undeclared;");
    assertSame(NumericType.I32VEC2, expression.getType());
    assertTrue(tu.getTypeAnalysis().diagnostics().isEmpty());
  }

  @Test
  void testTypeRecomputesAfterRemove() {
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var expression = typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());
    tu.getChildren().get(0).detachAndDelete();
    assertSame(ErrorType.INSTANCE, expression.getType());

    // detaching without unregistering is noticed as well
    var other = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var otherExpression = typedExpression(other);
    assertSame(NumericType.FLOAT32, otherExpression.getType());
    var declaration = other.getChildren().get(0);
    declaration.detach();
    assertSame(ErrorType.INSTANCE, otherExpression.getType());
    other.getChildren().add(0, declaration);
    assertSame(NumericType.FLOAT32, otherExpression.getType());
  }

  @Test
  void testTypeRecomputesAfterReplace() {
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var root = tu.getRoot();
    var expression = (AdditionExpression) typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());
    expression.getRight().replaceByAndDelete(p.parseExpression(root, "vec4(1)"));
    assertSame(NumericType.F32VEC4, expression.getType());
    expression.setLeft(p.parseExpression(root, "true"));
    assertSame(ErrorType.INSTANCE, expression.getType());
  }

  @Test
  void testTypeRecomputesAfterRename() {
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var root = tu.getRoot();
    var expression = typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());

    // only the declaration is renamed
    root.identifierIndex.getStream("value")
        .filter(identifier -> !(identifier.getParent() instanceof ReferenceExpression))
        .toList().forEach(identifier -> identifier.setName("renamed"));
    assertSame(ErrorType.INSTANCE, expression.getType());
    root.rename("value", "renamed");
    assertSame(NumericType.FLOAT32, expression.getType());

    // renaming without updating the identifier index is noticed as well
    var reference = ((ReferenceExpression) ((AdditionExpression) expression).getLeft()).getIdentifier();
    reference._setNameInternal("other");
    assertSame(ErrorType.INSTANCE, expression.getType());
    reference._setNameInternal("renamed");
    assertSame(NumericType.FLOAT32, expression.getType());
  }

  @Test
  void testTypeRecomputesAfterSetters() {
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var root = tu.getRoot();
    var expression = (AdditionExpression) typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());

    var specifier = root.nodeIndex.getStream(NumericTypeSpecifier.class)
        .filter(node -> node.type == NumericType.FLOAT32).findFirst().orElseThrow();
    assertSame(NumericType.FLOAT32, specifier.getNumericType());
    specifier.setNumericType(NumericType.F32VEC2);
    assertSame(NumericType.F32VEC2, expression.getType());

    // the literal is converted to the type of the other operand
    var literal = (LiteralExpression) expression.getRight();
    specifier.setNumericType(NumericType.INT32);
    assertSame(NumericType.INT32, expression.getType());
    literal.setInteger(NumericType.UINT32, 1);
    assertSame(NumericType.INT32, literal.getTypeAnalysis().typeOf(expression.getLeft()));
    assertSame(ErrorType.INSTANCE, expression.getType());
    literal.setFloating(1.0f);
    assertSame(NumericType.FLOAT32, expression.getType());
    literal.setBoolean(true);
    assertSame(ErrorType.INSTANCE, expression.getType());
    literal.setString("text");
    assertSame(StringType.INSTANCE, literal.getType());
    literal.setInteger(2);
    assertSame(NumericType.INT32, expression.getType());

    // the changes of the value do not change the type but still invalidate
    var analysis = expression.getTypeAnalysis();
    literal.changeInteger(3);
    assertNotSame(analysis, expression.getTypeAnalysis());
    assertSame(NumericType.INT32, expression.getType());

    // the version decides if the conversion from int to uint exists
    specifier.setNumericType(NumericType.UINT32);
    assertSame(ErrorType.INSTANCE, expression.getType());
    tu.getVersionStatement().setVersion(Version.GLSL40, Profile.CORE);
    assertSame(NumericType.UINT32, expression.getType());
  }

  @Test
  void testTypeRecomputesAfterQualifierAndExtensionSetters() {
    var tu = p.parseSeparateTranslationUnit("""
        #version 330 core
        #extension GL_ARB_gpu_shader5 : disable
        uniform float value;
        void main() {
          value = fma(1.0, 2.0, 3.0);
        }
        """);
    var root = tu.getRoot();
    assertEquals(List.of(DiagnosticCode.UNDECLARED_IDENTIFIER, DiagnosticCode.NOT_ASSIGNABLE),
        tu.getTypeAnalysis().diagnostics().stream().map(Diagnostic::code).toList());

    var qualifier = root.nodeIndex.getOne(StorageQualifier.class);
    assertSame(StorageType.UNIFORM, qualifier.getStorageType());
    qualifier.setStorageType(StorageType.OUT);
    assertEquals(List.of(DiagnosticCode.UNDECLARED_IDENTIFIER),
        tu.getTypeAnalysis().diagnostics().stream().map(Diagnostic::code).toList());

    var directive = root.nodeIndex.getOne(ExtensionDirective.class);
    assertSame(ExtensionBehavior.DISABLE, directive.getBehavior());
    directive.setBehavior(ExtensionBehavior.ENABLE);
    assertTrue(tu.getTypeAnalysis().diagnostics().isEmpty());
    directive.setName("GL_ARB_other");
    assertEquals(1, tu.getTypeAnalysis().diagnostics().size());
  }

  @Test
  void testTypeAfterWritingToFieldsDirectly() {
    // writing to public fields directly is not noticed until the cache is invalidated
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var root = tu.getRoot();
    var expression = typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());
    var specifier = root.nodeIndex.getStream(NumericTypeSpecifier.class)
        .filter(node -> node.type == NumericType.FLOAT32).findFirst().orElseThrow();
    specifier.type = NumericType.F32VEC3;
    assertSame(NumericType.FLOAT32, expression.getType());
    root.invalidateTypeAnalysis();
    assertSame(NumericType.F32VEC3, expression.getType());
  }

  @Test
  void testTypeEnvironmentOfRoot() {
    var tu = p.parseSeparateTranslationUnit(
        "#version 330 core\nvoid main() { gl_FragCoord.x + 1; }");
    var root = tu.getRoot();
    var expression = typedExpression(tu);
    assertNull(root.getTypeEnvironment());
    assertSame(NumericType.FLOAT32, expression.getType());

    var environment = new TypeEnvironment(Version.GLSL46, Profile.CORE, ShaderStage.VERTEX, Set.of());
    root.setTypeEnvironment(environment);
    assertSame(environment, root.getTypeEnvironment());
    assertSame(environment, tu.getTypeAnalysis().getEnvironment());
    assertSame(ErrorType.INSTANCE, expression.getType());
    root.setTypeEnvironment(environment.withStage(ShaderStage.FRAGMENT));
    assertSame(NumericType.FLOAT32, expression.getType());
    root.setTypeEnvironment(null);
    assertSame(Version.GLSL33, tu.getTypeAnalysis().getEnvironment().version());
  }

  @Test
  void testTypeOfSeparateTrees() {
    // each tree in a root has its own analysis
    var root = p.supplyRoot();
    var first = p.parseExpression(root, "1 + 1.0");
    var second = p.parseExpression(root, "ivec2(1) * 2");
    assertSame(NumericType.FLOAT32, first.getType());
    assertSame(NumericType.I32VEC2, second.getType());
    assertNotSame(first.getTypeAnalysis(), second.getTypeAnalysis());
    assertSame(first.getTypeAnalysis(), first.getTypeAnalysis());
    assertSame(first, first.getTypeAnalysis().getTop());

    // a detached subtree is analyzed on its own
    var tu = p.parseSeparateTranslationUnit(TYPED_SOURCE);
    var expression = typedExpression(tu);
    assertSame(NumericType.FLOAT32, expression.getType());
    expression.detach();
    assertSame(ErrorType.INSTANCE, expression.getType());
    assertSame(expression, expression.getTypeAnalysis().getTop());
  }

  @Test
  void testTypeWithoutRoot() {
    // nodes created outside of a build session have no root, they are analyzed
    // without caching
    var identifier = new Identifier("alone");
    assertNull(identifier.getRoot());
    assertNull(identifier.getType());
    assertNotSame(identifier.getTypeAnalysis(), identifier.getTypeAnalysis());
    var literal = new LiteralExpression(NumericType.FLOAT64, 1.0);
    assertSame(NumericType.FLOAT64, literal.getType());
    literal.setInteger(1);
    assertSame(NumericType.INT32, literal.getType());
  }
}
