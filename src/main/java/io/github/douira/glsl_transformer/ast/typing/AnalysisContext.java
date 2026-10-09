package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The state shared by the parts of the type analysis while a tree is analyzed.
 */
final class AnalysisContext {
  final TypeEnvironment env;
  final TypeAnalysis analysis;
  final boolean strict;

  // builtins are declared without declaring nodes and may be declared multiple times
  final boolean builtin;

  // whether the declared functions accept additional arguments, only for builtins
  boolean variadic;
  final Scope topScope;
  final DeclarationAnalyzer declarations = new DeclarationAnalyzer(this);
  final StatementAnalyzer statements = new StatementAnalyzer(this);
  final ExpressionTyper expressions = new ExpressionTyper(this);
  final ConstantEvaluator constants = new ConstantEvaluator(this);

  Scope scope;

  // the return type of the function that is being analyzed, null outside of functions
  Type returnType;

  AnalysisContext(ASTNode top, TypeEnvironment env, Scope topScope, boolean strict, boolean builtin) {
    this.env = env;
    this.topScope = topScope;
    this.scope = topScope;
    this.strict = strict;
    this.builtin = builtin;
    this.analysis = new TypeAnalysis(top, env, topScope);
  }

  /**
   * Reports a problem and returns the error type for convenience.
   */
  Type error(DiagnosticCode code, ASTNode node, String message) {
    var diagnostic = new Diagnostic(code, message, node);
    analysis.diagnostics.add(diagnostic);
    if (strict) {
      throw new TypeAnalysisException(diagnostic);
    }
    return ErrorType.INSTANCE;
  }

  void pushScope(ASTNode owner) {
    scope = new Scope(scope);
    analysis.scopes.put(owner, scope);
  }

  void popScope() {
    scope = scope.getParent();
  }

  boolean isTopScope() {
    return scope == topScope;
  }

  void declare(Symbol symbol, ASTNode node) {
    // functions are merged into the existing symbol of the same name
    var declared = scope.declare(symbol);
    analysis.symbols.put(node, declared ? scope.lookupLocal(symbol.name()) : symbol);
    if (!declared && !builtin) {
      error(DiagnosticCode.REDECLARATION, node, "'" + symbol.name() + "' is already declared in this scope");
    }
  }

  /**
   * Returns the node to store as the declaring node of something.
   */
  ASTNode declaring(ASTNode node) {
    return builtin ? null : node;
  }

  <T extends Type> T record(ASTNode node, T type) {
    analysis.types.put(node, type);
    return type;
  }

  boolean converts(Type from, Type to) {
    return Conversions.canImplicitlyConvert(from, to, env);
  }
}
