package io.github.douira.glsl_transformer.ast.node.type.specifier;

import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.util.NumericType;

public class BuiltinNumericTypeSpecifier extends TypeSpecifier {
  public NumericType type;

  public BuiltinNumericTypeSpecifier(NumericType type) {
    this.type = type;
  }

  public BuiltinNumericTypeSpecifier(NumericType type, ArraySpecifier arraySpecifier) {
    super(arraySpecifier);
    this.type = type;
  }

  @Override
  public SpecifierType getSpecifierType() {
    return SpecifierType.BUILTIN_NUMERIC;
  }

  @Override
  public <R> R typeSpecifierAccept(ASTVisitor<R> visitor) {
    return visitor.visitBuiltinNumericTypeSpecifier(this);
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
  public BuiltinNumericTypeSpecifier clone() {
    return new BuiltinNumericTypeSpecifier(type, clone(arraySpecifier));
  }

  @Override
  public BuiltinNumericTypeSpecifier cloneInto(Root root) {
    return (BuiltinNumericTypeSpecifier) super.cloneInto(root);
  }
}
