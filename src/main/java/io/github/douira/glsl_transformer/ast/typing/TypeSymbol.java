package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * A named type, which in GLSL is always a struct.
 *
 * @param name        the name of the type
 * @param type        the type
 * @param declaration the declaring node, or null for builtins
 */
public record TypeSymbol(String name, Type type, ASTNode declaration) implements Symbol {
}
