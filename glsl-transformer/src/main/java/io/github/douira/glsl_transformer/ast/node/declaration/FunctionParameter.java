package io.github.douira.glsl_transformer.ast.node.declaration;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.node.type.specifier.ArraySpecifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class FunctionParameter extends InnerASTNode {
  protected FullySpecifiedType type;
  protected Identifier name; // TODO: nullable
  protected ArraySpecifier arraySpecifier; // TODO: nullable

  public FunctionParameter(
      FullySpecifiedType type,
      Identifier name,
      ArraySpecifier arraySpecifier) {
    this.type = setup(type);
    this.name = setup(name);
    this.arraySpecifier = setup(arraySpecifier);
  }

  public FunctionParameter(FullySpecifiedType type, Identifier name) {
    this.type = setup(type);
    this.name = setup(name);
  }

  public FunctionParameter(FullySpecifiedType type) {
    this.type = setup(type);
  }

  public FullySpecifiedType getType() {
    return type;
  }

  public void setType(FullySpecifiedType type) {
    updateParents(this.type, type);
    this.type = type;
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
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionParameter(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFunctionParameter(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFunctionParameter(this);
  }
}
