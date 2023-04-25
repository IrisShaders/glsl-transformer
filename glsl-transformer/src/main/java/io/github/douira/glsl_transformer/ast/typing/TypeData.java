package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

public class TypeData {
  public Type type;
  // for references to declarations (parameters, variable declarations, function
  // declarations)
  public ASTNode declaredBy;
}
