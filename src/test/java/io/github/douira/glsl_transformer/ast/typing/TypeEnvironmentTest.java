package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.TypingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective.ExtensionBehavior;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.parser.*;

public class TypeEnvironmentTest {
  @Test
  void testProfileNormalization() {
    assertSame(Profile.COMPATIBILITY, new TypeEnvironment(Version.GLSL11, null).profile());
    assertSame(Profile.COMPATIBILITY, new TypeEnvironment(Version.GLSL14, null).profile());
    assertSame(Profile.CORE, new TypeEnvironment(Version.GLSL15, null).profile());
    assertSame(Profile.CORE, new TypeEnvironment(Version.GLSL46, null).profile());
    assertSame(Profile.COMPATIBILITY, new TypeEnvironment(Version.GLSL46, Profile.COMPATIBILITY).profile());
    assertSame(Profile.CORE, new TypeEnvironment(Version.GLSL12, Profile.CORE).profile());
    assertSame(Profile.ES, new TypeEnvironment(Version.GLSLES30, null).profile());
    assertSame(Profile.ES, new TypeEnvironment(Version.GLSLES10, Profile.CORE).profile());
    assertThrows(NullPointerException.class, () -> new TypeEnvironment(null, Profile.CORE));
  }

  @Test
  void testVersionChecks() {
    var desktop = new TypeEnvironment(Version.GLSL33, null);
    assertFalse(desktop.isES());
    assertTrue(desktop.isAtLeast(330, 0));
    assertTrue(desktop.isAtLeast(130, 300));
    assertFalse(desktop.isAtLeast(400, 100));
    assertFalse(desktop.isAtLeast(0, 100));

    var es = new TypeEnvironment(Version.GLSLES30, null);
    assertTrue(es.isES());
    assertTrue(es.isAtLeast(0, 300));
    assertTrue(es.isAtLeast(460, 100));
    assertFalse(es.isAtLeast(110, 310));
    assertFalse(es.isAtLeast(110, 0));
  }

  @Test
  void testExtensions() {
    var env = new TypeEnvironment(Version.GLSL33, null, null, Set.of("GL_A"));
    assertTrue(env.hasExtension("GL_A"));
    assertFalse(env.hasExtension("GL_B"));
    assertTrue(env.withExtensions(List.of("GL_B")).hasExtension("GL_B"));
    assertFalse(env.withExtensions(List.of("GL_B")).hasExtension("GL_A"));
    assertTrue(env.withExtensions(List.of(TypeEnvironment.ALL_EXTENSIONS)).hasExtension("GL_anything"));
    assertEquals(Set.of("GL_A"), env.extensions());
    assertThrows(UnsupportedOperationException.class, () -> env.extensions().add("GL_C"));
    assertNull(env.stage());
    assertSame(ShaderStage.FRAGMENT, env.withStage(ShaderStage.FRAGMENT).stage());
    assertEquals(env, env.withStage(ShaderStage.FRAGMENT).withStage(null));
  }

  @Test
  void testFromTree() {
    // a translation unit without a version statement has version 1.10
    var defaults = TypeEnvironment.of(parse("void main() {}"));
    assertSame(Version.GLSL11, defaults.version());
    assertSame(Profile.COMPATIBILITY, defaults.profile());
    assertNull(defaults.stage());
    assertTrue(defaults.extensions().isEmpty());

    var versioned = TypeEnvironment.of(parse("#version 330 core\nvoid main() {}"));
    assertSame(Version.GLSL33, versioned.version());
    assertSame(Profile.CORE, versioned.profile());
    assertSame(Profile.CORE, TypeEnvironment.of(parse("#version 450\n")).profile());
    assertSame(Profile.COMPATIBILITY, TypeEnvironment.of(parse("#version 450 compatibility\n")).profile());
    assertSame(Profile.ES, TypeEnvironment.of(parse("#version 310 es\n")).profile());
    assertSame(Version.GLSLES31, TypeEnvironment.of(parse("#version 310 es\n")).version());

    // anything else is typed with the latest version
    assertSame(TypeEnvironment.LATEST,
        TypeEnvironment.of(ParseShape.EXPRESSION._parseNodeSeparateInternal("1 + 2")));
    assertSame(Version.latest, TypeEnvironment.LATEST.version());
    assertSame(Profile.COMPATIBILITY, TypeEnvironment.LATEST.profile());
  }

  @Test
  void testExtensionDirectives() {
    assertEquals(Set.of("GL_A", "GL_B", "GL_C", "GL_D"), TypeEnvironment.of(parse("""
        #extension GL_A : enable
        #extension GL_B : require
        #extension GL_C : warn
        #extension GL_D
        void main() {}
        """)).extensions());
    assertEquals(Set.of("GL_B"), TypeEnvironment.of(parse("""
        #extension GL_A : enable
        #extension GL_B : enable
        #extension GL_A : disable
        #extension GL_C : disable
        """)).extensions());
  }

  @Test
  void testAllExtensions() {
    // the name that stands for all extensions works like any other name
    var root = RootSupplier.DEFAULT.get();
    var enabled = root.indexNodes(() -> new TranslationUnit(Stream.of(
        new ExtensionDirective("GL_A", ExtensionBehavior.ENABLE),
        new ExtensionDirective(TypeEnvironment.ALL_EXTENSIONS, ExtensionBehavior.WARN))));
    assertTrue(TypeEnvironment.of(enabled).hasExtension("GL_anything"));
    var disabled = root.indexNodes(() -> new TranslationUnit(Stream.of(
        new ExtensionDirective("GL_A", ExtensionBehavior.ENABLE),
        new ExtensionDirective(TypeEnvironment.ALL_EXTENSIONS, ExtensionBehavior.WARN),
        new ExtensionDirective(TypeEnvironment.ALL_EXTENSIONS, ExtensionBehavior.DISABLE))));
    assertFalse(TypeEnvironment.of(disabled).hasExtension("GL_anything"));

    // imprecision: disabling all extensions does not disable the extensions that
    // were enabled by name
    assertTrue(TypeEnvironment.of(disabled).hasExtension("GL_A"));

    // limitation of the grammar: an extension directive for all extensions
    // cannot be parsed, so this can only come up in manually built trees
    assertThrows(ParsingException.class, () -> parse("#extension all : warn\n"));
  }

  @Test
  void testExtensionDirectivesAreNotPositional() {
    // imprecision: an extension is enabled for the whole translation unit even
    // if it is only enabled after its features are used
    assertEquals("ok", codes(analyze("""
        #version 330 core
        void main() { float f = fma(1.0, 2.0, 3.0); }
        #extension GL_ARB_gpu_shader5 : enable
        """)));
  }

  @Test
  void testShaderStage() {
    for (var stage : ShaderStage.values()) {
      assertSame(stage, ShaderStage.fromExtension(stage.extension));
      assertSame(stage, ShaderStage.fromExtension(stage.extension.toUpperCase(Locale.ROOT)));
    }
    assertSame(ShaderStage.FRAGMENT, ShaderStage.fromExtension("frag"));
    assertSame(ShaderStage.TESSELLATION_CONTROL, ShaderStage.fromExtension("TESC"));
    assertNull(ShaderStage.fromExtension("glsl"));
    assertNull(ShaderStage.fromExtension(""));
  }
}
