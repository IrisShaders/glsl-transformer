package io.github.douira.glsl_transformer.ast.typing;

import java.util.List;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The signature of one overload of a function.
 *
 * @param name        the name of the function
 * @param returnType  the return type
 * @param parameters  the parameters
 * @param variadic    whether any number of additional arguments of any type
 *                    is accepted after the parameters, which is only the case
 *                    for some builtin functions
 * @param declaration the declaring node, or null for builtins
 */
public record FunctionSignature(
    String name,
    Type returnType,
    List<Parameter> parameters,
    boolean variadic,
    ASTNode declaration) {
  /**
   * The direction a parameter passes values in.
   */
  public enum Direction {
    IN,
    OUT,
    INOUT
  }

  /**
   * A parameter of a function.
   *
   * @param type      the type of the parameter
   * @param direction the direction of the parameter
   */
  public record Parameter(Type type, Direction direction) {
  }

  public FunctionSignature {
    parameters = List.copyOf(parameters);
  }

  /**
   * Checks if the other signature has the same parameter types. Two functions
   * with the same name and parameter types are the same function.
   *
   * @param other the other signature
   * @return true if the parameter types are the same
   */
  public boolean hasSameParameterTypes(FunctionSignature other) {
    return parameters.stream().map(Parameter::type).toList()
        .equals(other.parameters.stream().map(Parameter::type).toList());
  }

  @Override
  public String toString() {
    var builder = new StringBuilder(returnType.getTypeName()).append(' ').append(name).append('(');
    for (var i = 0; i < parameters.size(); i++) {
      var parameter = parameters.get(i);
      builder.append(i == 0 ? "" : ", ");
      builder.append(parameter.direction() == Direction.IN ? "" : parameter.direction().name().toLowerCase() + " ");
      builder.append(parameter.type().getTypeName());
    }
    return builder.append(variadic ? ", ...)" : ")").toString();
  }
}
