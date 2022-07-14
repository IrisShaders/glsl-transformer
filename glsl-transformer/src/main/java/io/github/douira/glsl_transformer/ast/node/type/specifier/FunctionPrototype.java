package io.github.douira.glsl_transformer.ast.node.type.specifier;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ListASTNode;
import io.github.douira.glsl_transformer.ast.node.declaration.FunctionParameter;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionPrototype extends ListASTNode<FunctionParameter> {
  protected FullySpecifiedType returnType;
  protected Identifier name;

  public FunctionPrototype(FullySpecifiedType returnType, Identifier name, Stream<FunctionParameter> parameters) {
    super(parameters);
    this.returnType = setup(returnType, this::setReturnType);
    this.name = setup(name, this::setName);
  }

  public FunctionPrototype(FullySpecifiedType returnType, Identifier name) {
    this(returnType, name, Stream.empty());
  }

  public List<FunctionParameter> getParameters() {
    return getChildren();
  }

  public FullySpecifiedType getReturnType() {
    return returnType;
  }

  public void setReturnType(FullySpecifiedType returnType) {
    updateParents(this.returnType, returnType, this::setReturnType);
    this.returnType = returnType;
  }

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    updateParents(this.name, name, this::setName);
    this.name = name;
  }

  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionPrototype(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFunctionPrototype(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFunctionPrototype(this);
  }
}
