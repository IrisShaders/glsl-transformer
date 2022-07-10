package io.github.douira.glsl_transformer.ast.node.declaration;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class NamedParameter extends FunctionParameter {
  protected TypeSpecifier typeSpecifier;
  protected Identifier name;
  protected ArraySpecifier arraySpecifier; // TODO: nullable

  public NamedParameter(TypeSpecifier typeSpecifier, Identifier name, ArraySpecifier arraySpecifier) {
    this(typeSpecifier, name);
    this.arraySpecifier = setup(arraySpecifier);
  }

  public NamedParameter(TypeSpecifier typeSpecifier, Identifier name) {
    this.typeSpecifier = setup(typeSpecifier);
    this.name = setup(name);
  }

  public TypeSpecifier getTypeSpecifier() {
    return typeSpecifier;
  }

  public void setTypeSpecifier(TypeSpecifier typeSpecifier) {
    updateParents(this.typeSpecifier, typeSpecifier);
    this.typeSpecifier = typeSpecifier;
  }

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    updateParents(this.name, name);
    this.name = name;
  }

  public ArraySpecifier getArraySpecifier() {
    return arraySpecifier;
  }

  public void setArraySpecifier(ArraySpecifier arraySpecifier) {
    updateParents(this.arraySpecifier, arraySpecifier);
    this.arraySpecifier = arraySpecifier;
  }

  @Override
  public ParameterKind getParameterKind() {
    return ParameterKind.NAMED;
  }

  @Override
  public <R> R functionParameterAccept(ASTVisitor<R> visitor) {
    return visitor.visitNamedParameter(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterNamedParameter(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitNamedParameter(this);
  }
}
