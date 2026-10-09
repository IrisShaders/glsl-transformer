package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The set of functions that are declared with the same name in a scope.
 */
public final class FunctionSymbol implements Symbol {
  private final String name;
  private final List<FunctionSignature> overloads = new ArrayList<>();

  public FunctionSymbol(String name, FunctionSignature... overloads) {
    this.name = name;
    this.overloads.addAll(Arrays.asList(overloads));
  }

  @Override
  public String name() {
    return name;
  }

  /**
   * Returns the overloads in declaration order.
   *
   * @return an unmodifiable view of the overloads
   */
  public List<FunctionSignature> overloads() {
    return Collections.unmodifiableList(overloads);
  }

  /**
   * Returns the node that declares the first overload.
   */
  @Override
  public ASTNode declaration() {
    return overloads.get(0).declaration();
  }

  /**
   * Adds the overloads of the other symbol to this symbol. An overload with the
   * same parameter types as an existing overload is a redeclaration of it and
   * is not added.
   *
   * @param other the symbol to merge into this symbol
   * @return false if an overload is redeclared with a different return type
   */
  boolean merge(FunctionSymbol other) {
    var compatible = true;
    for (var added : other.overloads) {
      var existing = overloads.stream().filter(added::hasSameParameterTypes).findFirst();
      if (existing.isEmpty()) {
        overloads.add(added);
      } else if (!Conversions.isSameType(existing.get().returnType(), added.returnType())) {
        compatible = false;
      }
    }
    return compatible;
  }

  @Override
  public String toString() {
    return name + overloads;
  }
}
