package io.github.douira.glsl_transformer.ast.typing;

/**
 * The type of a subroutine variable. Variables of this type can be called like
 * the function that declares the subroutine type.
 *
 * @param function the function symbol that declares the subroutine type
 */
public record SubroutineType(FunctionSymbol function) implements Type {
  @Override
  public String getTypeName() {
    return function.name();
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
