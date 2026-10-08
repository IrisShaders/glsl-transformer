package io.github.douira.glsl_transformer.ast.node.type.specifier;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.ast.typing.NumericType;

public class NumericTypeSpecifier extends TypeSpecifier {
  public NumericType type;

  public NumericTypeSpecifier(NumericType type) {
    this.type = type;
  }

  public NumericTypeSpecifier(NumericType type, ArraySpecifier arraySpecifier) {
    super(arraySpecifier);
    this.type = type;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.NUMERIC;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitNumericTypeSpecifier(this);
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
  public NumericTypeSpecifier clone() {
    return new NumericTypeSpecifier(type, clone(arraySpecifier));
  }

  @Override
  public NumericTypeSpecifier cloneInto(Root root) {
    return (NumericTypeSpecifier) super.cloneInto(root);
  }
}
