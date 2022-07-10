package io.github.douira.glsl_transformer.ast.node.declaration;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TypeAndInitDeclaration extends Declaration {
  protected FullySpecifiedType type;
  public final List<DeclarationMember> members;

  public TypeAndInitDeclaration(FullySpecifiedType type, List<DeclarationMember> members) {
    this.type = setup(type);
    this.members = new ChildNodeList<>(members, this);
  }

  public TypeAndInitDeclaration(FullySpecifiedType type, Stream<DeclarationMember> members) {
    this.type = setup(type);
    this.members = ChildNodeList.collect(members, this);
  }

  public TypeAndInitDeclaration(FullySpecifiedType type) {
    this.type = setup(type);
    this.members = new ChildNodeList<>(this);
  }

  public FullySpecifiedType getType() {
    return type;
  }

  public void setType(FullySpecifiedType type) {
    updateParents(this.type, type);
    this.type = type;
  }

  @Override
  public DeclarationType getDeclarationType() {
    return DeclarationType.TYPE_AND_INIT;
  }

  @Override
  public <R> R declarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitTypeAndInitDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterTypeAndInitDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitTypeAndInitDeclaration(this);
  }
}
