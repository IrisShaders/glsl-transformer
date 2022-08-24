package io.github.douira.glsl_transformer.ast.node.type;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.TypeQualifier;
import io.github.douira.glsl_transformer.ast.node.type.specifier.TypeSpecifier;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FullySpecifiedType extends InnerASTNode {
  protected TypeQualifier typeQualifier; // TODO: nullable
  protected TypeSpecifier typeSpecifier;

  public FullySpecifiedType(TypeQualifier typeQualifier, TypeSpecifier typeSpecifier) {
    this.typeQualifier = setup(typeQualifier, this::setTypeQualifier);
    this.typeSpecifier = setup(typeSpecifier, this::setTypeSpecifier);
  }

  public FullySpecifiedType(TypeSpecifier typeSpecifier) {
    this.typeSpecifier = setup(typeSpecifier, this::setTypeSpecifier);
  }

  public TypeQualifier getTypeQualifier() {
    return typeQualifier;
  }

  public void setTypeQualifier(TypeQualifier typeQualifier) {
    updateParents(this.typeQualifier, typeQualifier, this::setTypeQualifier);
    this.typeQualifier = typeQualifier;
  }

  public TypeSpecifier getTypeSpecifier() {
    return typeSpecifier;
  }

  public void setTypeSpecifier(TypeSpecifier typeSpecifier) {
    updateParents(this.typeSpecifier, typeSpecifier, this::setTypeSpecifier);
    this.typeSpecifier = typeSpecifier;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitFullySpecifiedType(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFullySpecifiedType(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFullySpecifiedType(this);
  }

  @Override
  public FullySpecifiedType clone() {
    var clone = (FullySpecifiedType) super.clone();
    clone.cloneChild(typeQualifier, clone::setTypeQualifier);
    clone.cloneChild(typeSpecifier, clone::setTypeSpecifier);
    return clone;
  }

  @Override
  public FullySpecifiedType cloneInto(Root root) {
    return (FullySpecifiedType) super.cloneInto(root);
  }

  @Override
  public FullySpecifiedType cloneSeparate() {
    return (FullySpecifiedType) super.cloneSeparate();
  }
}
