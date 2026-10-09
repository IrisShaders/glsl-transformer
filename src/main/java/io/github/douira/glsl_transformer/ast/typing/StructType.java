package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * A struct type. Struct types in GLSL are nominal: two struct declarations
 * with the same members are different types. Struct types are therefore
 * compared by identity.
 */
public final class StructType implements Type {
  private final String name;
  private final Map<String, Type> fields;
  private final ASTNode declaration;

  /**
   * Creates a new struct type.
   *
   * @param name        the name of the struct, null for anonymous structs
   * @param fields      the fields in declaration order
   * @param declaration the declaring node, null for builtin structs
   */
  public StructType(String name, SequencedMap<String, Type> fields, ASTNode declaration) {
    this.name = name;
    this.fields = Collections.unmodifiableSequencedMap(new LinkedHashMap<>(fields));
    this.declaration = declaration;
  }

  /**
   * Returns the name of the struct.
   *
   * @return the name, or null if the struct is anonymous
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the fields of the struct in declaration order.
   *
   * @return an unmodifiable ordered map of field names to their types
   */
  public Map<String, Type> getFields() {
    return fields;
  }

  public ASTNode getDeclaration() {
    return declaration;
  }

  @Override
  public String getTypeName() {
    return name == null ? "struct" : name;
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
