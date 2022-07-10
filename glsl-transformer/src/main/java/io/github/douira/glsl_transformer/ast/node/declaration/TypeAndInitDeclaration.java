package io.github.douira.glsl_transformer.ast.node.declaration;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TypeAndInitDeclaration extends Declaration {
  protected FullySpecifiedType type;
  public final List<DeclarationMember> members;

  public TypeAndInitDeclaration(List<DeclarationMember> members) {
    this.members = new ChildNodeList<>(members, this);
  }

  public TypeAndInitDeclaration(Stream<DeclarationMember> members) {
    this.members = ChildNodeList.collect(members, this);
  }

  public TypeAndInitDeclaration() {
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
    listener.enterTypeAndInitDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTypeAndInitDeclaration(this);
  }
}
