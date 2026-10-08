package io.github.douira.glsl_transformer.ast.typing;

public abstract class Type {
  public static final VoidType VOID = VoidType.INSTANCE;

  /**
   * The scope this type is defined in. Null only when there is no enclosing scope
   * (node in subtree without scope-forming structure like a translation unit or
   * compound statement) or the node has no parent.
   */
  public Scope enclosingScope; // TODO: nullable

  // public abstract Type combineType(Type assigned);
}
