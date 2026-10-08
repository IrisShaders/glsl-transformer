package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

public class Scope {
  // map of strings to their declaring nodes
  public final Map<String, ASTNode> declarations = new HashMap<>();

  public final Scope parent; // TODO: nullable

  public Scope(Scope parent) {
    this.parent = parent;
  }
}
