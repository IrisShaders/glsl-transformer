package io.github.douira.glsl_transformer.ast.node.external_declaration;

import io.github.douira.glsl_transformer.ast.node.statement.CompoundStatement;
import io.github.douira.glsl_transformer.ast.node.type.specifier.FunctionPrototype;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionDefinition extends ExternalDeclaration {
  protected FunctionPrototype functionPrototype;
  protected CompoundStatement body;

  public FunctionDefinition(FunctionPrototype functionPrototype, CompoundStatement body) {
    this.functionPrototype = setup(functionPrototype);
    this.body = setup(body);
  }

  public FunctionPrototype getFunctionPrototype() {
    return functionPrototype;
  }

  public void setFunctionPrototype(FunctionPrototype functionPrototype) {
    updateParents(this.functionPrototype, functionPrototype);
    this.functionPrototype = functionPrototype;
  }

  public CompoundStatement getBody() {
    return body;
  }

  public void setBody(CompoundStatement body) {
    updateParents(this.body, body);
    this.body = body;
  }

  @Override
  public ExternalDeclarationType getExternalDeclarationType() {
    return ExternalDeclarationType.FUNCTION_DEFINITION;
  }

  @Override
  public <R> R externalDeclarationAccept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionDefinition(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterFunctionDefinition(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.enterNode(listener);
    listener.exitFunctionDefinition(this);
  }
}
