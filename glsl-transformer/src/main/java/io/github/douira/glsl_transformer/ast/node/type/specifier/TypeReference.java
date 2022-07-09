package io.github.douira.glsl_transformer.ast.node.type.specifier;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TypeReference extends TypeSpecifier {
  protected Identifier reference;

  public TypeReference(Identifier reference) {
    this.reference = setup(reference);
  }

  public TypeReference(ArraySpecifier arraySpecifier, Identifier reference) {
    super(arraySpecifier);
    this.reference = setup(reference);
  }

  public Identifier getReference() {
    return reference;
  }

  public void setReference(Identifier reference) {
    updateParents(this.reference, reference);
    this.reference = reference;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.REFERENCE;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitTypeReference(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTypeReference(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTypeReference(this);
  }
}
