package io.github.douira.glsl_transformer.ast.declaration;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.type.initializer.Initializer;
import io.github.douira.glsl_transformer.ast.node.type.specifier.ArraySpecifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class DeclarationMember extends InnerASTNode {
  protected ArraySpecifier arraySpecifier; // TODO: nullable
  protected Initializer initializer; // TODO: nullable

  public DeclarationMember(ArraySpecifier arraySpecifier, Initializer initializer) {
    this.arraySpecifier = setup(arraySpecifier);
    this.initializer = setup(initializer);
  }

  public DeclarationMember(ArraySpecifier arraySpecifier) {
    this.arraySpecifier = setup(arraySpecifier);
  }

  public DeclarationMember(Initializer initializer) {
    this.initializer = setup(initializer);
  }

  public DeclarationMember() {
  }

  public ArraySpecifier getArraySpecifier() {
    return arraySpecifier;
  }

  public void setArraySpecifier(ArraySpecifier arraySpecifier) {
    updateParents(this.arraySpecifier, arraySpecifier);
    this.arraySpecifier = arraySpecifier;
  }

  public Initializer getInitializer() {
    return initializer;
  }

  public void setInitializer(Initializer initializer) {
    updateParents(this.initializer, initializer);
    this.initializer = initializer;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitDeclarationMember(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterDeclarationMember(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitDeclarationMember(this);
  }
}
