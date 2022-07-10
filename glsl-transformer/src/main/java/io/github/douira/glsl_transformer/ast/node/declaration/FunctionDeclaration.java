package io.github.douira.glsl_transformer.ast.node.declaration;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.type.FullySpecifiedType;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionDeclaration extends Declaration {
  protected FullySpecifiedType returnType;
  protected Identifier name;
  public final List<FunctionParameter> parameters;

  public FunctionDeclaration(FullySpecifiedType returnType, Identifier name, List<FunctionParameter> parameters) {
    this.returnType = setup(returnType);
    this.name = setup(name);
    this.parameters = new ChildNodeList<>(parameters, this);
  }

  public FunctionDeclaration(FullySpecifiedType returnType, Identifier name, Stream<FunctionParameter> parameters) {
    this.returnType = setup(returnType);
    this.name = setup(name);
    this.parameters = ChildNodeList.collect(parameters, this);
  }

  public FunctionDeclaration(FullySpecifiedType returnType, Identifier name) {
    this(returnType, name, Stream.empty());
  }

  public FullySpecifiedType getReturnType() {
    return returnType;
  }

  public void setReturnType(FullySpecifiedType returnType) {
    updateParents(this.returnType, returnType);
    this.returnType = returnType;
  }

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    updateParents(this.name, name);
    this.name = name;
  }

  @Override
  public DeclarationType getDeclarationType() {
    return DeclarationType.FUNCTION;
  }

  @Override
  public <R> R declarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionDeclaration(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterFunctionDeclaration(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitFunctionDeclaration(this);
  }
}
