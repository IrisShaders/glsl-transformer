package io.github.douira.glsl_transformer.ast.typing;

import static io.github.douira.glsl_transformer.ast.typing.TypingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.expression.binary.*;
import io.github.douira.glsl_transformer.ast.node.expression.unary.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.initializer.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.node.type.struct.*;
import io.github.douira.glsl_transformer.ast.query.RootSupplier;
import io.github.douira.glsl_transformer.ast.transform.SourceLocation;
import io.github.douira.glsl_transformer.parser.ParseShape;

/**
 * Tests of the analysis result and of the entry points of the analyzer.
 */
public class TypeAnalysisTest {
  private static <N extends ASTNode> N one(ASTNode tree, Class<N> type) {
    return tree.getRoot().nodeIndex.getUnique(type);
  }

  private static <N extends ASTNode> List<N> all(ASTNode tree, Class<N> type) {
    return tree.getRoot().nodeIndex.getStream(type).toList();
  }

  private static ReferenceExpression reference(ASTNode tree, String name) {
    return (ReferenceExpression) tree.getRoot().identifierIndex.getStream(name)
        .map(ASTNode::getParent)
        .filter(ReferenceExpression.class::isInstance)
        .findFirst().orElseThrow();
  }

  @Test
  void testResultBasics() {
    var tree = parse("#version 330 core\nvoid main() {}");
    var analysis = new TypeAnalyzer().analyze(tree);
    assertSame(tree, analysis.getTop());
    assertSame(Version.GLSL33, analysis.getEnvironment().version());
    assertTrue(analysis.diagnostics().isEmpty());
    assertThrows(UnsupportedOperationException.class, () -> analysis.diagnostics().clear());
    assertNotNull(analysis.getGlobalScope().lookupLocal("main"));
    assertNull(analysis.getGlobalScope().lookupLocal("sin"));
    assertNotNull(analysis.getGlobalScope().lookup("sin"));
    assertSame(BuiltinRegistry.getScope(analysis.getEnvironment()), analysis.getGlobalScope().getParent());
  }

  @Test
  void testTypesOfNodes() {
    var tree = parse("""
        #version 460
        struct S { float a, b[2]; };
        const vec3 v = vec3(1), w[2] = vec3[2](v, v);
        precision highp float;
        uniform Block { int x; } block;
        invariant gl_Position;
        float f(in S s, vec2[2] p[3]);
        void main() {
          for (int i = 0; bool c = i < 2; i++) {}
          mat2 m = { { 1.0, 2.0 }, { 3.0, 4.0 } };
        }
        """);
    var analysis = new TypeAnalyzer().analyze(tree);
    assertEquals("ok", codes(analysis));

    // statements and other nodes without a type
    assertNull(analysis.typeOf(tree));
    assertNull(analysis.typeOf(one(tree, ForLoopStatement.class)));
    assertNull(analysis.typeOf(one(tree, VariableDeclaration.class)));
    assertNull(analysis.typeOf(new Identifier("foreign")));

    var struct = one(tree, StructSpecifier.class);
    var structType = (StructType) analysis.typeOf(struct);
    assertEquals("S", structType.getName());
    assertEquals("S", structType.getTypeName());
    assertEquals("S", structType.toString());
    assertSame(struct, structType.getDeclaration());
    assertEquals(List.of("a", "b"), List.copyOf(structType.getFields().keySet()));
    assertSame(NumericType.FLOAT32, structType.getFields().get("a"));
    assertEquals(new ArrayType(NumericType.FLOAT32, ArraySize.of(2)), structType.getFields().get("b"));
    assertThrows(UnsupportedOperationException.class, () -> structType.getFields().clear());
    var declarators = all(tree, StructDeclarator.class);
    assertEquals(Set.of("float", "float[2]", "int"),
        new HashSet<>(declarators.stream().map(d -> analysis.typeOf(d).getTypeName()).toList()));

    var members = all(tree, DeclarationMember.class);
    var types = new HashMap<String, Type>();
    members.forEach(member -> types.put(member.getName().getName(), analysis.typeOf(member)));
    assertSame(NumericType.F32VEC3, types.get("v"));
    assertEquals(new ArrayType(NumericType.F32VEC3, ArraySize.of(2)), types.get("w"));
    assertSame(NumericType.INT32, types.get("i"));
    assertSame(NumericType.F32MAT2X2, types.get("m"));

    // the declaration has the type before the names
    var declarations = all(tree, TypeAndInitDeclaration.class);
    assertTrue(declarations.stream().anyMatch(d -> analysis.typeOf(d) == NumericType.F32VEC3));
    assertTrue(declarations.stream().anyMatch(d -> analysis.typeOf(d) == structType));
    assertSame(NumericType.FLOAT32, analysis.typeOf(one(tree, PrecisionDeclaration.class)));
    assertSame(NumericType.FLOAT32, analysis.typeOf(one(tree, FunctionDeclaration.class)));

    var block = one(tree, InterfaceBlockDeclaration.class);
    var blockType = (InterfaceBlockType) analysis.typeOf(block);
    assertEquals("Block", blockType.getBlockName());
    assertEquals("Block", blockType.getTypeName());
    assertEquals("Block", blockType.toString());
    assertSame(block, blockType.getDeclaration());
    assertEquals(Map.of("x", NumericType.INT32), blockType.getMembers());
    assertThrows(UnsupportedOperationException.class, () -> blockType.getMembers().clear());

    var parameters = all(tree, FunctionParameter.class);
    assertEquals(Set.of("S", "vec2[3][2]"),
        new HashSet<>(parameters.stream().map(p -> analysis.typeOf(p).getTypeName()).toList()));
    var prototypes = all(tree, FunctionPrototype.class);
    assertEquals(Set.of("float", "void"),
        new HashSet<>(prototypes.stream().map(p -> analysis.typeOf(p).getTypeName()).toList()));

    var condition = one(tree, IterationConditionInitializer.class);
    assertSame(NumericType.BOOL, analysis.typeOf(condition));
    assertSame(NumericType.BOOL, analysis.typeOf(condition.getSpecifiedType()));
    assertSame(NumericType.BOOL, analysis.typeOf(condition.getSpecifiedType().getTypeSpecifier()));
    assertSame(NumericType.BOOL, analysis.typeOf(condition.getInitializer()));

    var nested = all(tree, NestedInitializer.class);
    assertEquals(Set.of("mat2", "vec2"),
        new HashSet<>(nested.stream().map(n -> analysis.typeOf(n).getTypeName()).toList()));

    // every expression has a type
    for (var expression : tree.getRoot().nodeIndex.getStream(LiteralExpression.class).toList()) {
      assertNotNull(analysis.typeOf(expression));
    }
  }

  @Test
  void testSymbolsAndDeclarations() {
    var tree = parse("""
        #version 460
        struct S { float a; };
        const int limit = 4;
        float helper(float x) { return x * 2.0; }
        float helper(int x) { return 1.0; }
        void main() {
          S instance = S(1.0);
          S copy = instance;
          float result = helper(instance.a) + helper(limit) + sin(1.0);
          missing;
          undeclared(1);
          1.0;
        }
        """);
    var analysis = new TypeAnalyzer().analyze(tree);
    assertEquals("UNDECLARED_IDENTIFIER UNDECLARED_IDENTIFIER", codes(analysis));

    // references resolve to the declaring nodes
    var limit = reference(tree, "limit");
    var limitSymbol = (VariableSymbol) analysis.symbolOf(limit);
    assertEquals("limit", limitSymbol.name());
    assertSame(NumericType.INT32, limitSymbol.type());
    assertFalse(limitSymbol.assignable());
    assertEquals(ConstantValue.of(NumericType.INT32, 4), limitSymbol.constantValue());
    var limitDeclaration = (DeclarationMember) analysis.declarationOf(limit);
    assertEquals("limit", limitDeclaration.getName().getName());
    assertSame(limitSymbol, analysis.symbolOf(limitDeclaration));

    var instance = reference(tree, "instance");
    var instanceSymbol = (VariableSymbol) analysis.symbolOf(instance);
    assertTrue(instanceSymbol.assignable());
    assertNull(instanceSymbol.constantValue());
    assertSame(analysis.typeOf(one(tree, StructSpecifier.class)), instanceSymbol.type());

    // type references resolve to the struct
    var struct = one(tree, StructSpecifier.class);
    var typeReference = all(tree, TypeReference.class).stream()
        .filter(r -> r.getReference().getName().equals("S")).findFirst().orElseThrow();
    assertSame(struct, analysis.declarationOf(typeReference));
    assertSame(analysis.symbolOf(struct), analysis.symbolOf(typeReference));
    assertInstanceOf(TypeSymbol.class, analysis.symbolOf(struct));

    // calls resolve to the overload, constructors to the type and builtins to nothing
    var calls = new HashMap<String, List<FunctionCallExpression>>();
    for (var call : all(tree, FunctionCallExpression.class)) {
      calls.computeIfAbsent(call.getFunctionName().getName(), name -> new ArrayList<>()).add(call);
    }
    var prototypes = all(tree, FunctionPrototype.class);
    var helperCalls = calls.get("helper");
    assertEquals(2, helperCalls.size());
    for (var call : helperCalls) {
      var signature = analysis.signatureOf(call);
      var prototype = (FunctionPrototype) analysis.declarationOf(call);
      assertTrue(prototypes.contains(prototype));
      assertSame(signature, analysis.signatureOf(prototype));
      assertSame(prototype, signature.declaration());
      var argument = analysis.typeOf(call.getParameters().get(0));
      assertSame(argument, signature.parameters().get(0).type());
      var symbol = (FunctionSymbol) analysis.symbolOf(call);
      assertEquals(2, symbol.overloads().size());
      assertSame(symbol, analysis.symbolOf(prototype));
    }
    assertNotSame(analysis.declarationOf(helperCalls.get(0)), analysis.declarationOf(helperCalls.get(1)));

    var constructor = calls.get("S").get(0);
    assertSame(struct, analysis.declarationOf(constructor));
    assertNull(analysis.signatureOf(constructor));

    var builtin = calls.get("sin").get(0);
    assertNull(analysis.declarationOf(builtin));
    assertNull(analysis.signatureOf(builtin).declaration());
    assertEquals("float sin(float)", analysis.signatureOf(builtin).toString());
    assertInstanceOf(FunctionSymbol.class, analysis.symbolOf(builtin));

    // nothing is known about what could not be resolved or does not refer to anything
    var undeclared = calls.get("undeclared").get(0);
    assertNull(analysis.symbolOf(undeclared));
    assertNull(analysis.declarationOf(undeclared));
    assertNull(analysis.signatureOf(undeclared));
    var literal = all(tree, LiteralExpression.class).stream()
        .filter(node -> node.getParent() instanceof ExpressionStatement).findFirst().orElseThrow();
    assertNull(analysis.symbolOf(literal));
    assertNull(analysis.declarationOf(literal));
    assertNull(analysis.symbolOf(new Identifier("foreign")));
  }

  @Test
  void testScopes() {
    var tree = parse("""
        int global;
        void main(int parameter) {
          int local;
          { int nested; }
          if (true) global = 1; else { int inElse; }
          for (int i = 0; i < 1; i++) { int inLoop; }
          while (true) { int inWhile; }
          do { int inDo; } while (true);
          switch (1) { default: int inSwitch; }
        }
        """);
    var analysis = new TypeAnalyzer().analyze(tree);
    var global = analysis.getGlobalScope();
    assertSame(global, analysis.scopeOf(tree));

    var declared = new HashMap<String, Scope>();
    for (var member : all(tree, DeclarationMember.class)) {
      declared.put(member.getName().getName(), analysis.scopeOf(member));
    }
    assertSame(global, declared.get("global"));
    var function = declared.get("local");
    assertSame(global, function.getParent());
    assertSame(function, analysis.scopeOf(one(tree, FunctionParameter.class)));
    assertNotNull(function.lookupLocal("parameter"));
    assertNotNull(function.lookupLocal("local"));
    assertSame(function, analysis.scopeOf(one(tree, FunctionDefinition.class)));
    assertSame(function, analysis.scopeOf(one(tree, FunctionDefinition.class).getBody()));

    for (var name : List.of("nested", "inElse", "inLoop", "inWhile", "inDo", "inSwitch")) {
      var scope = declared.get(name);
      assertNotNull(scope.lookupLocal(name), name);
      assertSame(function, scope.getParent(), name);
    }

    // the loop variable is in the same scope as the body
    assertNotNull(declared.get("inLoop").lookupLocal("i"));
    assertSame(declared.get("inLoop"), declared.get("i"));
    assertSame(declared.get("inLoop"), analysis.scopeOf(one(tree, ForLoopStatement.class)));
    assertSame(function, analysis.scopeOf(one(tree, ForLoopStatement.class).getParent()));

    // the branch that is not a compound statement has its own scope
    var selection = one(tree, SelectionStatement.class);
    assertSame(function, analysis.scopeOf(selection));
    assertNotSame(function, analysis.scopeOf(selection.getIfTrue()));
    assertSame(function, analysis.scopeOf(selection.getIfTrue()).getParent());

    assertNull(analysis.scopeOf(new Identifier("foreign")));
  }

  @Test
  void testStrictMode() {
    var tree = parse("void main() { float a = missing; int b = true; }");
    var lenient = new TypeAnalyzer().setStrict(false).analyze(tree);
    assertEquals("UNDECLARED_IDENTIFIER TYPE_MISMATCH", codes(lenient));

    var exception = assertThrows(TypeAnalysisException.class,
        () -> new TypeAnalyzer().setStrict(true).analyze(tree));
    var diagnostic = exception.getDiagnostic();
    assertSame(DiagnosticCode.UNDECLARED_IDENTIFIER, diagnostic.code());
    assertEquals(lenient.diagnostics().get(0).message(), diagnostic.message());
    assertEquals("UNDECLARED_IDENTIFIER: Undeclared identifier 'missing'", diagnostic.toString());
    assertEquals(diagnostic.toString(), exception.getMessage());
    assertInstanceOf(ReferenceExpression.class, diagnostic.node());

    assertDoesNotThrow(() -> new TypeAnalyzer().setStrict(true).analyze(parse("void main() {}")));
  }

  @Test
  void testDiagnosticLocations() {
    var tree = parse("float a = missing;\nvoid main() { float b = alsoMissing; }");
    var location = new SourceLocation(7);
    one(tree, FunctionDefinition.class).setSourceLocation(location);
    var diagnostics = new TypeAnalyzer().analyze(tree).diagnostics();
    assertEquals(2, diagnostics.size());
    assertNull(diagnostics.get(0).location());
    assertSame(location, diagnostics.get(1).location());

    // the location of the node itself is preferred
    var own = new SourceLocation(9);
    var node = reference(tree, "alsoMissing");
    node.setSourceLocation(own);
    assertSame(own, new TypeAnalyzer().analyze(tree).diagnostics().get(1).location());
    assertSame(own, new Diagnostic(DiagnosticCode.NOT_CONSTANT, "message", node).location());
    var explicit = new Diagnostic(DiagnosticCode.NOT_CONSTANT, "message", node, location);
    assertSame(location, explicit.location());
    assertEquals("message", explicit.message());
    assertSame(node, explicit.node());
  }

  @Test
  void testEnvironmentOverride() {
    var tree = parse("#version 330 core\nvoid main() { uint u = 1; vec4 c = gl_FragCoord; }");
    assertEquals("TYPE_MISMATCH", codes(new TypeAnalyzer().analyze(tree)));
    assertEquals("ok", codes(new TypeAnalyzer()
        .setEnvironment(new TypeEnvironment(Version.GLSL40, Profile.CORE)).analyze(tree)));
    assertEquals("TYPE_MISMATCH UNDECLARED_IDENTIFIER", codes(new TypeAnalyzer()
        .setEnvironment(TypeEnvironment.of(tree).withStage(ShaderStage.VERTEX)).analyze(tree)));
    assertEquals("TYPE_MISMATCH", codes(new TypeAnalyzer()
        .setEnvironment(TypeEnvironment.of(tree).withStage(ShaderStage.VERTEX))
        .setEnvironment(null).analyze(tree)));
  }

  @Test
  void testAnalyzeExpression() {
    var expression = ParseShape.EXPRESSION._parseNodeSeparateInternal("1 + 2.0 * vec3(1)");
    var analysis = new TypeAnalyzer().analyze(expression);
    assertSame(TypeEnvironment.LATEST, analysis.getEnvironment());
    assertSame(NumericType.F32VEC3, analysis.typeOf(expression));
    assertTrue(analysis.diagnostics().isEmpty());
    assertSame(analysis.getGlobalScope(), analysis.scopeOf(expression));

    // names cannot be resolved without their declarations
    var unresolved = ParseShape.EXPRESSION._parseNodeSeparateInternal("a + 1");
    var unresolvedAnalysis = new TypeAnalyzer().analyze(unresolved);
    assertSame(ErrorType.INSTANCE, unresolvedAnalysis.typeOf(unresolved));
    assertEquals("UNDECLARED_IDENTIFIER", codes(unresolvedAnalysis));
  }

  @Test
  void testAnalyzeStatement() {
    var statement = ParseShape.STATEMENT._parseNodeSeparateInternal("{ float a = 1.0; a += sin(a); return a; }");
    var analysis = new TypeAnalyzer().analyze(statement);
    assertEquals("ok", codes(analysis));
    assertSame(NumericType.FLOAT32, analysis.typeOf(one(statement, ReturnStatement.class).getExpression()));

    // a return statement outside of a function is not checked
    assertEquals("ok", codes(new TypeAnalyzer().analyze(
        ParseShape.STATEMENT._parseNodeSeparateInternal("return;"))));
  }

  @Test
  void testAnalyzeDeclarations() {
    var external = ParseShape.EXTERNAL_DECLARATION._parseNodeSeparateInternal(
        "float twice(float x) { return x * 2.0; }");
    var externalAnalysis = new TypeAnalyzer().analyze(external);
    assertEquals("ok", codes(externalAnalysis));
    assertNotNull(externalAnalysis.getGlobalScope().lookupLocal("twice"));

    var statement = ParseShape.STATEMENT._parseNodeSeparateInternal("ivec2 a = ivec2(1), b;");
    var declaration = one(statement, TypeAndInitDeclaration.class);
    var analysis = new TypeAnalyzer().analyze(declaration);
    assertSame(declaration, analysis.getTop());
    assertSame(NumericType.I32VEC2, analysis.typeOf(declaration));
    assertSame(NumericType.I32VEC2, ((VariableSymbol) analysis.getGlobalScope().lookupLocal("b")).type());
  }

  @Test
  void testAnalyzeTypes() {
    var specified = ParseShape.FULLY_SPECIFIED_TYPE._parseNodeSeparateInternal("const mat3[2]");
    var expected = new ArrayType(NumericType.F32MAT3X3, ArraySize.of(2));
    var analysis = new TypeAnalyzer().analyze(specified);
    assertEquals(expected, analysis.typeOf(specified));
    assertEquals(expected, analysis.typeOf(specified.getTypeSpecifier()));

    var specifier = specified.getTypeSpecifier();
    var specifierAnalysis = new TypeAnalyzer().analyze(specifier);
    assertEquals(expected, specifierAnalysis.typeOf(specifier));
    assertNull(specifierAnalysis.typeOf(specified));

    var sampler = ParseShape.FULLY_SPECIFIED_TYPE._parseNodeSeparateInternal("sampler2D");
    assertSame(FixedType.SAMPLER2D, new TypeAnalyzer().analyze(sampler).typeOf(sampler));
  }

  @Test
  void testAnalyzeOtherNodes() {
    // nodes that are not analyzed on their own yield an empty analysis
    var identifier = new Identifier("lonely");
    var analysis = new TypeAnalyzer().analyze(identifier);
    assertSame(identifier, analysis.getTop());
    assertNull(analysis.typeOf(identifier));
    assertTrue(analysis.diagnostics().isEmpty());
    assertSame(analysis.getGlobalScope(), analysis.scopeOf(identifier));
  }

  @Test
  void testLayoutQualifierParts() {
    // layout defaults are variable declarations without names
    var analysis = analyze("""
        #version 460
        layout(location = 3, binding = missing, std140, shared) uniform;
        """);
    var literal = one(analysis.getTop(), LiteralExpression.class);
    assertSame(NumericType.INT32, analysis.typeOf(literal));
    assertSame(ErrorType.INSTANCE, analysis.typeOf(reference(analysis.getTop(), "missing")));
    assertEquals("UNDECLARED_IDENTIFIER", codes(analysis));
    assertTrue(one(analysis.getTop(), VariableDeclaration.class).isQualifierDefault());
  }

  @Test
  void testTypeNames() {
    assertEquals("float[2]", new ArrayType(NumericType.FLOAT32, ArraySize.of(2)).getTypeName());
    assertEquals("float[]", new ArrayType(NumericType.FLOAT32, ArraySize.UNSIZED).getTypeName());
    assertEquals("float[?]", new ArrayType(NumericType.FLOAT32, ArraySize.UNKNOWN).toString());
    assertEquals("vec2[3][2]", new ArrayType(
        new ArrayType(NumericType.F32VEC2, ArraySize.of(2)), ArraySize.of(3)).getTypeName());
    assertEquals("2", ArraySize.of(2).toString());
    assertEquals("", ArraySize.UNSIZED.toString());
    assertEquals("?", ArraySize.UNKNOWN.toString());
    assertTrue(ArraySize.of(2).isKnown());
    assertFalse(ArraySize.UNSIZED.isKnown());
    assertFalse(ArraySize.UNKNOWN.isKnown());
    assertEquals(2, ArraySize.of(2).length());
    assertSame(ArraySize.Kind.KNOWN, ArraySize.of(2).kind());
    assertTrue(ArraySize.of(2).isCompatibleWith(ArraySize.of(2)));
    assertFalse(ArraySize.of(2).isCompatibleWith(ArraySize.of(3)));
    assertTrue(ArraySize.of(2).isCompatibleWith(ArraySize.UNSIZED));
    assertTrue(ArraySize.UNKNOWN.isCompatibleWith(ArraySize.of(3)));
    assertTrue(ArraySize.UNSIZED.isCompatibleWith(ArraySize.UNKNOWN));

    assertEquals("<error>", ErrorType.INSTANCE.getTypeName());
    assertEquals("<error>", ErrorType.INSTANCE.toString());
    assertTrue(ErrorType.INSTANCE.isError());
    assertEquals("string", StringType.INSTANCE.getTypeName());
    assertEquals("string", StringType.INSTANCE.toString());
    assertFalse(StringType.INSTANCE.isError());
    assertFalse(NumericType.FLOAT32.isError());

    var anonymous = new StructType(null, new LinkedHashMap<>(), null);
    assertNull(anonymous.getName());
    assertNull(anonymous.getDeclaration());
    assertEquals("struct", anonymous.getTypeName());
    assertEquals("struct", anonymous.toString());

    var function = new FunctionSymbol("chooser",
        new FunctionSignature("chooser", FixedType.VOID, List.of(), false, null));
    var subroutine = new SubroutineType(function);
    assertEquals("chooser", subroutine.getTypeName());
    assertEquals("chooser", subroutine.toString());
    assertSame(function, subroutine.function());
  }

  @Test
  void testStringLiterals() {
    var analysis = analyze("""
        #version 460
        #extension GL_EXT_debug_printf : enable
        void main() { debugPrintfEXT("text %d", 1); }
        """);
    assertEquals("ok", codes(analysis));
    var call = one(analysis.getTop(), FunctionCallExpression.class);
    assertSame(StringType.INSTANCE, analysis.typeOf(call.getParameters().get(0)));
    assertSame(FixedType.VOID, analysis.typeOf(call));
    assertTrue(analysis.signatureOf(call).variadic());
  }
}
