package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;

/**
 * The type of an instance of an interface block. Like struct types, interface
 * block types are compared by identity.
 */
public final class InterfaceBlockType implements Type {
  private final String blockName;
  private final Map<String, Type> members;
  private final ASTNode declaration;

  /**
   * Creates a new interface block type.
   *
   * @param blockName   the name of the block
   * @param members     the members in declaration order
   * @param declaration the declaring node, null for builtin blocks
   */
  public InterfaceBlockType(String blockName, SequencedMap<String, Type> members, ASTNode declaration) {
    this.blockName = blockName;
    this.members = Collections.unmodifiableSequencedMap(new LinkedHashMap<>(members));
    this.declaration = declaration;
  }

  public String getBlockName() {
    return blockName;
  }

  /**
   * Returns the members of the block in declaration order.
   *
   * @return an unmodifiable ordered map of member names to their types
   */
  public Map<String, Type> getMembers() {
    return members;
  }

  public ASTNode getDeclaration() {
    return declaration;
  }

  @Override
  public String getTypeName() {
    return blockName;
  }

  @Override
  public String toString() {
    return getTypeName();
  }
}
