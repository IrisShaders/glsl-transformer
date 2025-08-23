package io.github.douira.glsl_transformer.ast.typing;

public class Type {
  public static final VoidType VOID = VoidType.INSTANCE;

  /**
   * The scope this type is defined in. Null only when there is no enclosing scope
   * or the node has no parent.
   */
  public Scope enclosingScope; // TODO: nullable
}
