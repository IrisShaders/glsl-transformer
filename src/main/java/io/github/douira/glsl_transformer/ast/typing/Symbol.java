package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * Something that a name can refer to: a variable, a set of overloaded
 * functions or a type. GLSL has a single namespace per scope.
 */
public sealed interface Symbol permits VariableSymbol, FunctionSymbol, TypeSymbol {
  /**
   * Returns the name the symbol is declared with.
   *
   * @return the name
   */
  String name();

  /**
   * Returns the node that declares the symbol.
   *
   * @return the declaring node, or null for builtins
   */
  ASTNode declaration();
}
