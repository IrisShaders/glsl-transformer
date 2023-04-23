package io.github.douira.glsl_transformer.ast.node.type.specifier;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.ast.typing.FixedType;

public class FixedTypeSpecifier extends TypeSpecifier {
  public FixedType type;

  public FixedTypeSpecifier(FixedType type) {
    this.type = type;
  }

  public FixedTypeSpecifier(FixedType type, ArraySpecifier arraySpecifier) {
    super(arraySpecifier);
    this.type = type;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.FIXED;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitFixedTypeSpecifier(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    // terminal nodes have no children
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    // terminal nodes have no children
  }

  @Override
  public FixedTypeSpecifier clone() {
    return new FixedTypeSpecifier(type, clone(arraySpecifier));
  }

  @Override
  public FixedTypeSpecifier cloneInto(Root root) {
    return (FixedTypeSpecifier) super.cloneInto(root);
  }
}
