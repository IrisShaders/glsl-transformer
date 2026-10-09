package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * A variable, function parameter or member of an interface block without
 * instance name.
 *
 * @param name          the name of the variable
 * @param type          the type of the variable
 * @param assignable    whether the variable can be written to
 * @param constantValue the value if the variable is a constant expression,
 *                      which is {@link ConstantValue#UNKNOWN} if it is a
 *                      constant expression but the value is not known, or null
 *                      if the variable is not a constant expression
 * @param declaration   the declaring node, or null for builtins
 */
public record VariableSymbol(
    String name,
    Type type,
    boolean assignable,
    ConstantValue constantValue,
    ASTNode declaration) implements Symbol {
  public boolean isConstant() {
    return constantValue != null;
  }
}
