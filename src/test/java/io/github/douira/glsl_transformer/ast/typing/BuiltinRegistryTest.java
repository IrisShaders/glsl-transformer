package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.TypingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.FunctionCallExpression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.FunctionDefinition;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.ExpressionStatement;

public class BuiltinRegistryTest {
  private static final Set<String> ALL = Set.of(TypeEnvironment.ALL_EXTENSIONS);

  private static TypeEnvironment env(Version version, Profile profile, ShaderStage stage, String... extensions) {
    return new TypeEnvironment(version, profile, stage, Set.of(extensions));
  }

  private static TypeEnvironment env(Version version, String... extensions) {
    return env(version, null, null, extensions);
  }

  private static boolean has(TypeEnvironment env, String name) {
    return BuiltinRegistry.getScope(env).lookup(name) != null;
  }

  private static List<FunctionSignature> overloads(TypeEnvironment env, String name) {
    return ((FunctionSymbol) BuiltinRegistry.getScope(env).lookup(name)).overloads();
  }

  private static VariableSymbol variable(TypeEnvironment env, String name) {
    return (VariableSymbol) BuiltinRegistry.getScope(env).lookup(name);
  }

  @Test
  void testAllEnvironmentsLoad() {
    var stages = new ArrayList<ShaderStage>(Arrays.asList(ShaderStage.values()));
    stages.add(null);
    for (var version : Version.values()) {
      for (var profile : new Profile[] { Profile.CORE, Profile.COMPATIBILITY }) {
        for (var stage : stages) {
          for (var extensions : List.of(Set.<String>of(), ALL)) {
            var env = new TypeEnvironment(version, profile, stage, extensions);
            var scope = assertDoesNotThrow(() -> BuiltinRegistry.getScope(env), env::toString);
            assertNotNull(scope.lookup("sin"), env::toString);
            assertNull(scope.getParent());

            // the placeholder types are not part of the builtins
            assertNull(scope.lookup("genType"), env::toString);
            assertNull(scope.lookup("gsampler2D"), env::toString);
            assertNull(scope.lookup("string"), env::toString);
          }
        }
      }
    }
  }

  @Test
  void testScopesAreCached() {
    var env = env(Version.GLSL33);
    assertSame(BuiltinRegistry.getScope(env), BuiltinRegistry.getScope(env(Version.GLSL33)));
    assertNotSame(BuiltinRegistry.getScope(env), BuiltinRegistry.getScope(env(Version.GLSL40)));
    assertNotSame(BuiltinRegistry.getScope(env),
        BuiltinRegistry.getScope(env.withStage(ShaderStage.FRAGMENT)));
  }

  /**
   * Generates a call with arguments of exactly the parameter types for every
   * builtin function overload and checks that it resolves to that overload.
   */
  private static int assertAllPrototypesResolve(TypeEnvironment env) {
    var count = 0;
    for (var symbol : BuiltinRegistry.getScope(env).getSymbols().values()) {
      if (!(symbol instanceof FunctionSymbol function)) {
        continue;
      }
      var declarations = new StringBuilder();
      var calls = new StringBuilder();
      var variables = 0;
      for (var overload : function.overloads()) {
        var arguments = new StringJoiner(", ");
        for (var parameter : overload.parameters()) {
          if (parameter.type() == StringType.INSTANCE) {
            arguments.add("\"text\"");
          } else {
            var name = "v" + variables++;
            declarations.append(parameter.type().getTypeName()).append(' ').append(name).append(";\n");
            arguments.add(name);
          }
        }
        calls.append(function.name()).append('(').append(arguments).append(");\n");
      }

      var source = declarations + "void main() {\n" + calls + "}\n";
      var tree = parse(source);
      var analysis = new TypeAnalyzer().setEnvironment(env).analyze(tree);
      assertEquals("ok", codes(analysis), () -> source + analysis.diagnostics());
      var children = tree.getChildren();
      var statements = ((FunctionDefinition) children.get(children.size() - 1)).getBody().getStatements();
      assertEquals(function.overloads().size(), statements.size());
      for (var i = 0; i < statements.size(); i++) {
        var call = (FunctionCallExpression) ((ExpressionStatement) statements.get(i)).getExpression();
        var overload = function.overloads().get(i);
        assertSame(overload, analysis.signatureOf(call), source);
        assertSame(overload.returnType(), analysis.typeOf(call), source);
        assertNull(overload.declaration());
        assertEquals(function.name(), overload.name());
        count++;
      }
    }
    return count;
  }

  @Test
  void testAllPrototypesResolve() {
    // the exact counts guard against prototypes going missing or appearing
    // unnoticed, they have to be updated when the builtin definitions change
    assertEquals(4259, assertAllPrototypesResolve(
        env(Version.GLSL46, Profile.COMPATIBILITY, null, TypeEnvironment.ALL_EXTENSIONS)));
    assertEquals(1655, assertAllPrototypesResolve(env(Version.GLSL46, Profile.CORE, null)));
    assertEquals(897, assertAllPrototypesResolve(env(Version.GLSL33, Profile.CORE, null)));
    assertEquals(295, assertAllPrototypesResolve(env(Version.GLSL11)));
    assertEquals(4192, assertAllPrototypesResolve(
        env(Version.GLSLES32, Profile.ES, null, TypeEnvironment.ALL_EXTENSIONS)));
    assertEquals(841, assertAllPrototypesResolve(env(Version.GLSLES30)));
    assertEquals(234, assertAllPrototypesResolve(env(Version.GLSLES10)));
  }

  @Test
  void testGenericInstantiation() {
    var env = env(Version.GLSL46);
    assertEquals(List.of("float sin(float)", "vec2 sin(vec2)", "vec3 sin(vec3)", "vec4 sin(vec4)"),
        overloads(env, "sin").stream().map(Object::toString).toList());
    assertEquals(List.of("bool any(bvec2)", "bool any(bvec3)", "bool any(bvec4)"),
        overloads(env, "any").stream().map(Object::toString).toList());
    assertEquals(18, overloads(env, "matrixCompMult").size());
    assertEquals(List.of(
        "float modf(float, out float)", "vec2 modf(vec2, out vec2)", "vec3 modf(vec3, out vec3)",
        "vec4 modf(vec4, out vec4)", "double modf(double, out double)", "dvec2 modf(dvec2, out dvec2)",
        "dvec3 modf(dvec3, out dvec3)", "dvec4 modf(dvec4, out dvec4)"),
        overloads(env, "modf").stream().map(Object::toString).toList());
    assertEquals(List.of("int atomicAdd(inout int, int)", "uint atomicAdd(inout uint, uint)"),
        overloads(env, "atomicAdd").stream().map(Object::toString).toList());

    // the sampler families are instantiated for float, int and uint
    var texelFetch = overloads(env, "texelFetch").stream().map(Object::toString).toList();
    assertTrue(texelFetch.containsAll(List.of(
        "vec4 texelFetch(sampler2D, ivec2, int)",
        "ivec4 texelFetch(isampler2D, ivec2, int)",
        "uvec4 texelFetch(usampler2D, ivec2, int)")), texelFetch::toString);
    assertEquals(27, texelFetch.size());
    assertTrue(overloads(env, "textureGatherOffsets").stream().map(Object::toString).toList()
        .contains("vec4 textureGatherOffsets(sampler2D, vec2, ivec2[4])"));

    // the integer image families only have int and uint
    assertEquals(22, overloads(env, "imageAtomicAdd").size());
    assertTrue(overloads(env, "imageAtomicAdd").stream().map(Object::toString).toList()
        .contains("uint imageAtomicAdd(uimage2DMS, ivec2, int, uint)"));
  }

  @Test
  void testVersionGating() {
    // fma is from 4.00 and ES 3.20 or an extension
    assertFalse(has(env(Version.GLSL33), "fma"));
    assertTrue(has(env(Version.GLSL40), "fma"));
    assertTrue(has(env(Version.GLSL33, "GL_ARB_gpu_shader5"), "fma"));
    assertFalse(has(env(Version.GLSL33, "GL_ARB_unrelated"), "fma"));
    assertFalse(has(env(Version.GLSLES31), "fma"));
    assertTrue(has(env(Version.GLSLES32), "fma"));
    assertTrue(has(env(Version.GLSLES31, "GL_EXT_gpu_shader5"), "fma"));

    // the texture functions without the sampler type in the name are from 1.30
    assertFalse(has(env(Version.GLSL12), "texture"));
    assertTrue(has(env(Version.GLSL13), "texture"));
    assertFalse(has(env(Version.GLSLES10), "texture"));
    assertTrue(has(env(Version.GLSLES30), "texture"));

    assertFalse(has(env(Version.GLSL14), "determinant"));
    assertTrue(has(env(Version.GLSL15), "determinant"));
    assertFalse(has(env(Version.GLSL11), "outerProduct"));
    assertTrue(has(env(Version.GLSL12), "outerProduct"));

    // double overloads are added in 4.00
    assertEquals(4, overloads(env(Version.GLSL33), "sqrt").size());
    assertEquals(8, overloads(env(Version.GLSL40), "sqrt").size());
    assertEquals(8, overloads(env(Version.GLSL33, "GL_ARB_gpu_shader_fp64"), "sqrt").size());

    // features that are never available in one kind of GLSL
    assertTrue(has(env(Version.GLSL11), "noise1"));
    assertFalse(has(env(Version.GLSLES32), "noise1"));
    assertFalse(has(env(Version.GLSLES32), "gl_MaxClipPlanes"));
    assertFalse(has(env(Version.GLSL46), "debugPrintfEXT"));
    assertTrue(has(env(Version.GLSL46, "GL_EXT_debug_printf"), "debugPrintfEXT"));
    assertTrue(has(env(Version.GLSLES10, "GL_EXT_debug_printf"), "debugPrintfEXT"));
  }

  @Test
  void testRemovalGating() {
    // like in glslang, the old texture functions remain in the core profile until 4.20
    assertTrue(has(env(Version.GLSL11), "texture2D"));
    assertTrue(has(env(Version.GLSL41, Profile.CORE, null), "texture2D"));
    assertFalse(has(env(Version.GLSL42, Profile.CORE, null), "texture2D"));
    assertFalse(has(env(Version.GLSL46), "texture2D"));
    assertTrue(has(env(Version.GLSL46, Profile.COMPATIBILITY, null), "texture2D"));
    assertTrue(has(env(Version.GLSLES10), "texture2D"));
    assertFalse(has(env(Version.GLSLES30), "texture2D"));
    assertFalse(has(env(Version.GLSLES10), "texture1D"));

    assertTrue(has(env(Version.GLSL33), "gl_FragColor"));
    assertFalse(has(env(Version.GLSL42), "gl_FragColor"));
    assertTrue(has(env(Version.GLSL42, Profile.COMPATIBILITY, null), "gl_FragColor"));
    assertTrue(has(env(Version.GLSLES10), "gl_FragColor"));
    assertFalse(has(env(Version.GLSLES30), "gl_FragColor"));

    // the fixed function state is only in the compatibility profile
    assertTrue(has(env(Version.GLSL13), "gl_ModelViewMatrix"));
    assertFalse(has(env(Version.GLSL15), "gl_ModelViewMatrix"));
    assertTrue(has(env(Version.GLSL15, Profile.COMPATIBILITY, null), "gl_ModelViewMatrix"));
    assertFalse(has(env(Version.GLSL12, Profile.CORE, null).withExtensions(ALL), "gl_FogCoord")
        && has(env(Version.GLSL15, Profile.CORE, null), "gl_FogCoord"));
    assertFalse(has(env(Version.GLSLES10), "gl_ModelViewMatrix"));
    assertFalse(has(env(Version.GLSL46), "ftransform"));
    assertTrue(has(env(Version.GLSL46, Profile.COMPATIBILITY, null), "ftransform"));

    // imprecision: 1.40 has no profiles and the fixed function state is treated
    // as available in it, as it is with GL_ARB_compatibility
    assertTrue(has(env(Version.GLSL14), "gl_ModelViewMatrix"));
  }

  @Test
  void testStageGating() {
    var base = env(Version.GLSL46, Profile.COMPATIBILITY, null);
    assertTrue(has(base, "dFdx"));
    assertTrue(has(base.withStage(ShaderStage.FRAGMENT), "dFdx"));
    assertFalse(has(base.withStage(ShaderStage.VERTEX), "dFdx"));

    assertTrue(has(base.withStage(ShaderStage.VERTEX), "gl_VertexID"));
    assertFalse(has(base.withStage(ShaderStage.FRAGMENT), "gl_VertexID"));
    assertTrue(has(base.withStage(ShaderStage.FRAGMENT), "gl_FragCoord"));
    assertFalse(has(base.withStage(ShaderStage.COMPUTE), "gl_FragCoord"));
    assertTrue(has(base.withStage(ShaderStage.GEOMETRY), "EmitVertex"));
    assertFalse(has(base.withStage(ShaderStage.TESSELLATION_EVALUATION), "EmitVertex"));
    assertTrue(has(base.withStage(ShaderStage.TESSELLATION_CONTROL), "barrier"));
    assertTrue(has(base.withStage(ShaderStage.COMPUTE), "barrier"));
    assertFalse(has(base.withStage(ShaderStage.GEOMETRY), "barrier"));
    assertTrue(has(base.withStage(ShaderStage.VERTEX), "ftransform"));
    assertFalse(has(base.withStage(ShaderStage.FRAGMENT), "ftransform"));

    // sections with a stage but without a version
    assertTrue(has(env(Version.GLSL11, null, ShaderStage.VERTEX), "gl_Position"));
    assertFalse(has(env(Version.GLSL11, null, ShaderStage.FRAGMENT), "gl_Position"));

    // functions without a stage are available everywhere
    for (var stage : ShaderStage.values()) {
      assertTrue(has(base.withStage(stage), "sin"), stage.toString());
      assertTrue(has(base.withStage(stage), "gl_MaxDrawBuffers"), stage.toString());
    }

    // the stage and the extension are both required
    var rayTracing = env(Version.GLSL46, null, ShaderStage.RAY_GENERATION, "GL_EXT_ray_tracing");
    assertTrue(has(rayTracing, "gl_LaunchIDEXT"));
    assertTrue(has(rayTracing, "traceRayEXT"));
    assertFalse(has(rayTracing, "gl_HitTEXT"));
    assertTrue(has(rayTracing.withStage(ShaderStage.CLOSEST_HIT), "gl_HitTEXT"));
    assertFalse(has(rayTracing.withExtensions(Set.of()), "gl_LaunchIDEXT"));
    assertFalse(has(rayTracing.withStage(ShaderStage.FRAGMENT), "gl_LaunchIDEXT"));
    assertTrue(has(rayTracing.withStage(null), "gl_HitTEXT"));
  }

  @Test
  void testVariables() {
    var compatibility = env(Version.GLSL46, Profile.COMPATIBILITY, null);

    // outputs can be written to, inputs and constants cannot
    assertTrue(variable(compatibility, "gl_Position").assignable());
    assertFalse(variable(compatibility, "gl_FragCoord").assignable());
    assertFalse(variable(compatibility, "gl_ModelViewMatrix").assignable());
    assertTrue(variable(compatibility, "gl_FragDepth").assignable());
    assertSame(NumericType.F32VEC4, variable(compatibility, "gl_Position").type());
    assertNull(variable(compatibility, "gl_Position").declaration());
    assertFalse(variable(compatibility, "gl_Position").isConstant());

    // the values of the builtin constants are implementation dependent
    var constant = variable(compatibility, "gl_MaxDrawBuffers");
    assertFalse(constant.assignable());
    assertTrue(constant.isConstant());
    assertSame(ConstantValue.UNKNOWN, constant.constantValue());
    assertSame(NumericType.I32VEC3, variable(compatibility, "gl_MaxComputeWorkGroupSize").type());
    assertEquals(new ArrayType(NumericType.F32MAT4X4, ArraySize.UNKNOWN),
        variable(compatibility, "gl_TextureMatrix").type());
    assertEquals(new ArrayType(NumericType.FLOAT32, ArraySize.UNSIZED),
        variable(compatibility, "gl_ClipDistance").type());
    assertEquals(new ArrayType(NumericType.FLOAT32, ArraySize.of(4)),
        variable(compatibility, "gl_TessLevelOuter").type());

    // builtin structs and blocks
    var depthRange = (StructType) variable(compatibility, "gl_DepthRange").type();
    assertEquals("gl_DepthRangeParameters", depthRange.getName());
    assertNull(depthRange.getDeclaration());
    assertEquals(List.of("near", "far", "diff"), List.copyOf(depthRange.getFields().keySet()));
    var typeSymbol = (TypeSymbol) BuiltinRegistry.getScope(compatibility).lookup("gl_DepthRangeParameters");
    assertSame(depthRange, typeSymbol.type());
    assertNull(typeSymbol.declaration());
    var perVertex = (InterfaceBlockType) ((ArrayType) variable(compatibility, "gl_in").type()).element();
    assertEquals("gl_PerVertex", perVertex.getBlockName());
    assertNull(perVertex.getDeclaration());
    assertSame(NumericType.F32VEC4, perVertex.getMembers().get("gl_Position"));

    // variables that are inputs in one stage and outputs in another
    assertTrue(variable(compatibility, "gl_PrimitiveID").assignable());
    assertTrue(variable(compatibility.withStage(ShaderStage.GEOMETRY), "gl_PrimitiveID").assignable());
    assertFalse(variable(compatibility.withStage(ShaderStage.FRAGMENT), "gl_PrimitiveID").assignable());
    assertTrue(variable(compatibility.withStage(ShaderStage.VERTEX), "gl_ClipDistance").assignable());
    assertFalse(variable(compatibility.withStage(ShaderStage.FRAGMENT), "gl_ClipDistance").assignable());
  }

  @Test
  void testVariadicFunctions() {
    var env = env(Version.GLSL46, "GL_EXT_debug_printf");
    var printf = overloads(env, "debugPrintfEXT");
    assertEquals(1, printf.size());
    assertTrue(printf.get(0).variadic());
    assertEquals("void debugPrintfEXT(string, ...)", printf.get(0).toString());
    assertFalse(overloads(env, "sin").get(0).variadic());
  }
}
