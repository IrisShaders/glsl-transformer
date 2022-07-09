package io.github.douira.glsl_transformer.ast.declaration;

import io.github.douira.glsl_transformer.ast.declaration.Declaration.DeclarationType;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FullTypeParameter extends FunctionParameter {
  protected FullySpecifiedType type;

  public FullTypeParameter(FullySpecifiedType type) {
    this.type = setup(type);
  }

  public FullySpecifiedType getType() {
    return type;
  }

  public void setType(FullySpecifiedType type) {
    updateParents(this.type, type);
    this.type = type;
  }

  @Override
  public ParameterKind getParameterKind() {
    return ParameterKind.FULLY_SPECIFIED_TYPE;
  }

  @Override
  public <R> R functionParameterAccept(ASTVisitor<R> visitor) {
    return visitor.visitFullTypeParameter(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFullTypeParameter(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFullTypeParameter(this);
  }
}
