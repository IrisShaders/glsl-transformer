package io.github.douira.glsl_transformer.ast.node.type;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.TypeQualifier;
import io.github.douira.glsl_transformer.ast.node.type.specifier.TypeSpecifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FullySpecifiedType extends InnerASTNode {
  protected TypeQualifier typeQualifier; // TODO: nullable
  protected TypeSpecifier typeSpecifier;

  public FullySpecifiedType(TypeQualifier typeQualifier, TypeSpecifier typeSpecifier) {
    this.typeQualifier = setup(typeQualifier);
    this.typeSpecifier = setup(typeSpecifier);
  }

  public FullySpecifiedType(TypeSpecifier typeSpecifier) {
    this.typeSpecifier = setup(typeSpecifier);
  }

  public TypeQualifier getTypeQualifier() {
    return typeQualifier;
  }

  public void setTypeQualifier(TypeQualifier typeQualifier) {
    updateParents(this.typeQualifier, typeQualifier);
    this.typeQualifier = typeQualifier;
  }

  public TypeSpecifier getTypeSpecifier() {
    return typeSpecifier;
  }

  public void setTypeSpecifier(TypeSpecifier typeSpecifier) {
    updateParents(this.typeSpecifier, typeSpecifier);
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
}
