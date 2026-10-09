package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The result of analyzing a tree with the {@link TypeAnalyzer}. It holds the
 * type of every expression, the symbols names refer to, the scopes and the
 * problems that were found.
 * <p>
 * The result describes the tree as it was when it was analyzed and does not
 * change when the tree changes.
 */
public final class TypeAnalysis {
  private final ASTNode top;
  private final TypeEnvironment environment;
  private final Scope globalScope;
  final Map<ASTNode, Type> types = new IdentityHashMap<>();
  final Map<ASTNode, Symbol> symbols = new IdentityHashMap<>();
  final Map<ASTNode, FunctionSignature> signatures = new IdentityHashMap<>();
  final Map<ASTNode, Scope> scopes = new IdentityHashMap<>();
  final List<Diagnostic> diagnostics = new ArrayList<>();

  TypeAnalysis(ASTNode top, TypeEnvironment environment, Scope globalScope) {
    this.top = top;
    this.environment = environment;
    this.globalScope = globalScope;
    scopes.put(top, globalScope);
  }

  /**
   * Returns the top node of the analyzed tree.
   *
   * @return the top node
   */
  public ASTNode getTop() {
    return top;
  }

  public TypeEnvironment getEnvironment() {
    return environment;
  }

  /**
   * Returns the scope that the global declarations of the tree are in. Its
   * parent is the scope of the builtins.
   *
   * @return the global scope
   */
  public Scope getGlobalScope() {
    return globalScope;
  }

  /**
   * Returns the type of a node. Every expression has a type, which is the
   * {@link ErrorType} if the expression could not be typed. Nodes that declare
   * or specify something with a type have that type: type specifiers, fully
   * specified types, declarations, declaration members, function parameters,
   * struct declarators, initializers, function prototypes (the return type) and
   * iteration condition initializers.
   *
   * @param node the node to get the type of
   * @return the type, or null if the node has no type or is not part of the
   *         analyzed tree
   */
  public Type typeOf(ASTNode node) {
    return types.get(node);
  }

  /**
   * Returns the symbol a node refers to or declares. Reference expressions
   * refer to variables, type references to types and function calls to
   * functions or, for struct constructors, to types. Declaration members,
   * named function parameters, iteration condition initializers, named struct
   * specifiers, function prototypes and interface block declarations with an
   * instance name declare symbols.
   *
   * @param node the node to get the symbol of
   * @return the symbol, or null if there is none or it could not be resolved
   */
  public Symbol symbolOf(ASTNode node) {
    return symbols.get(node);
  }

  /**
   * Returns the signature of the overload that a function call was resolved to,
   * or the signature that a function prototype declares.
   *
   * @param node the function call or function prototype
   * @return the signature, or null if there is none or it could not be resolved
   */
  public FunctionSignature signatureOf(ASTNode node) {
    return signatures.get(node);
  }

  /**
   * Returns the node that declares what the given node refers to. For function
   * calls this is the prototype of the overload the call was resolved to.
   *
   * @param node the referring node
   * @return the declaring node, or null if the node does not refer to anything,
   *         it could not be resolved or it refers to a builtin
   */
  public ASTNode declarationOf(ASTNode node) {
    var signature = signatures.get(node);
    if (signature != null) {
      return signature.declaration();
    }
    var symbol = symbols.get(node);
    return symbol == null ? null : symbol.declaration();
  }

  /**
   * Returns the innermost scope that encloses the given node.
   *
   * @param node the node to get the scope of
   * @return the scope, or null if the node is not part of the analyzed tree
   */
  public Scope scopeOf(ASTNode node) {
    for (var current = node; current != null; current = current.getParent()) {
      var scope = scopes.get(current);
      if (scope != null) {
        return scope;
      }
    }
    return null;
  }

  /**
   * Returns the problems that were found in order of discovery.
   *
   * @return an unmodifiable view of the diagnostics
   */
  public List<Diagnostic> diagnostics() {
    return Collections.unmodifiableList(diagnostics);
  }
}
