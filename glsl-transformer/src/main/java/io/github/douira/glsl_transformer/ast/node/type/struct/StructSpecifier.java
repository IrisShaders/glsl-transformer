package io.github.douira.glsl_transformer.ast.node.type.struct;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.specifier.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class StructSpecifier extends TypeSpecifier {
  protected Identifier name; // TODO: nullable
  protected StructBody structBody;

  public StructSpecifier(StructBody structBody) {
    this.structBody = setup(structBody);
  }

  public StructSpecifier(ArraySpecifier arraySpecifier, StructBody structBody) {
    super(arraySpecifier);
    this.structBody = setup(structBody);
  }

  public StructSpecifier(Identifier name, StructBody structBody) {
    this.name = setup(name);
    this.structBody = setup(structBody);
  }

  public StructSpecifier(
      ArraySpecifier arraySpecifier,
      Identifier name,
      StructBody structBody) {
    super(arraySpecifier);
    this.name = setup(name);
    this.structBody = setup(structBody);
  }

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    updateParents(this.name, name);
    this.name = name;
  }

  public StructBody getStructBody() {
    return structBody;
  }

  public void setStructBody(StructBody structBody) {
    updateParents(this.structBody, structBody);
    this.structBody = structBody;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.STRUCT;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitStructSpecifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterStructSpecifier(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitStructSpecifier(this);
  }
}
