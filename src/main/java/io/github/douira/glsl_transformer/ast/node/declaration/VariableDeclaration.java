package io.github.douira.glsl_transformer.ast.node.declaration;

import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.*;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.StorageQualifier.StorageType;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class VariableDeclaration extends Declaration {
  protected TypeQualifier typeQualifier;
  protected ChildNodeList<Identifier> names;

  public VariableDeclaration(TypeQualifier typeQualifier, Stream<Identifier> names) {
    this.typeQualifier = typeQualifier;
    this.names = ChildNodeList.collect(names, this);
  }

  public VariableDeclaration(TypeQualifier typeQualifier) {
    this(typeQualifier, Stream.empty());
  }

  public TypeQualifier getTypeQualifier() {
    return typeQualifier;
  }

  public void setTypeQualifier(TypeQualifier typeQualifier) {
    updateParents(this.typeQualifier, typeQualifier, this::setTypeQualifier);
    this.typeQualifier = typeQualifier;
  }

  public ChildNodeList<Identifier> getNames() {
    return names;
  }

  /**
   * Returns whether this declaration has no names and therefore does not apply
   * its qualifiers to existing variables but sets defaults, like
   * {@code layout(std140) uniform;}, or configures the shader as a whole, like
   * {@code layout(triangles) in;} or {@code layout(local_size_x = 8) in;}.
   * 
   * @return true if this declaration has no names
   */
  public boolean isQualifierDefault() {
    return names.isEmpty();
  }

  /**
   * Returns the first layout qualifier of this declaration.
   * 
   * @return the layout qualifier, or null if there is none
   */
  public LayoutQualifier getLayoutQualifier() {
    for (var part : typeQualifier.getParts()) {
      if (part instanceof LayoutQualifier layoutQualifier) {
        return layoutQualifier;
      }
    }
    return null;
  }

  /**
   * Checks if this declaration has a storage qualifier with the given storage
   * type. For example, the input layout defaults of a shader are the
   * declarations that are qualifier defaults and have the storage type
   * {@link StorageType#IN}.
   * 
   * @param storageType the storage type to look for
   * @return true if there is a storage qualifier with the storage type
   */
  public boolean hasStorageType(StorageType storageType) {
    for (var part : typeQualifier.getParts()) {
      if (part instanceof StorageQualifier storageQualifier && storageQualifier.storageType == storageType) {
        return true;
      }
    }
    return false;
  }

  @Override
  public DeclarationType getDeclarationType() {
    return DeclarationType.VARIABLE;
  }

  @Override
  public <R> R declarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitVariableDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterVariableDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitVariableDeclaration(this);
  }

  @Override
  public VariableDeclaration clone() {
    return new VariableDeclaration(clone(typeQualifier), clone(names));
  }

  @Override
  public VariableDeclaration cloneInto(Root root) {
    return (VariableDeclaration) super.cloneInto(root);
  }
}
