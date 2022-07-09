package io.github.douira.glsl_transformer.ast.declaration;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.qualifier.TypeQualifier;
import io.github.douira.glsl_transformer.ast.node.type.specifier.ArraySpecifier;
import io.github.douira.glsl_transformer.ast.node.type.struct.StructBody;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class InterfaceBlockDeclaration extends Declaration {
  protected TypeQualifier typeQualifier;
  protected Identifier blockName;
  protected StructBody structBody;
  protected Identifier variableName; // TODO: nullable
  protected ArraySpecifier arraySpecifier; // TODO: nullable

  public InterfaceBlockDeclaration(
      TypeQualifier typeQualifier,
      Identifier blockName,
      StructBody structBody,
      Identifier variableName,
      ArraySpecifier arraySpecifier) {
    this(typeQualifier, blockName, structBody, variableName);
    this.arraySpecifier = setup(arraySpecifier);
  }

  public InterfaceBlockDeclaration(
      TypeQualifier typeQualifier,
      Identifier blockName,
      StructBody structBody,
      Identifier variableName) {
    this(typeQualifier, blockName, structBody);
    this.variableName = setup(variableName);
  }

  public InterfaceBlockDeclaration(
      TypeQualifier typeQualifier,
      Identifier blockName,
      StructBody structBody) {
    this.typeQualifier = setup(typeQualifier);
    this.blockName = setup(blockName);
    this.structBody = setup(structBody);
  }

  public TypeQualifier getTypeQualifier() {
    return typeQualifier;
  }

  public void setTypeQualifier(TypeQualifier typeQualifier) {
    updateParents(this.typeQualifier, typeQualifier);
    this.typeQualifier = typeQualifier;
  }

  public Identifier getBlockName() {
    return blockName;
  }

  public void setBlockName(Identifier blockName) {
    updateParents(this.blockName, blockName);
    this.blockName = blockName;
  }

  public StructBody getStructBody() {
    return structBody;
  }

  public void setStructBody(StructBody structBody) {
    updateParents(this.structBody, structBody);
    this.structBody = structBody;
  }

  public Identifier getVariableName() {
    return variableName;
  }

  public void setVariableName(Identifier variableName) {
    updateParents(this.variableName, variableName);
    this.variableName = variableName;
  }

  public ArraySpecifier getArraySpecifier() {
    return arraySpecifier;
  }

  public void setArraySpecifier(ArraySpecifier arraySpecifier) {
    updateParents(this.arraySpecifier, arraySpecifier);
    this.arraySpecifier = arraySpecifier;
  }

  @Override
  public DeclarationType getDeclarationType() {
    return DeclarationType.INTERFACE_BLOCK;
  }

  @Override
  public <R> R declarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitInterfaceBlockDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterInterfaceBlockDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitInterfaceBlockDeclaration(this);
  }
}
