package io.github.douira.glsl_transformer.ast.node.expression.unary;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.type.specifier.TypeSpecifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class FunctionCallExpression extends TerminalExpression {
  public enum FunctionCallType {
    NONE,
    VOID,
    PARAMETERS
  }

  public enum FunctionReferenceType {
    NAME,
    TYPE_SPECIFIER
  }

  protected Identifier functionName;
  protected TypeSpecifier functionSpecifier;
  protected FunctionReferenceType referenceType;

  protected List<Expression> parameters;
  protected FunctionCallType functionCallType;

  public FunctionCallExpression(Identifier functionName) {
    this.functionName = setup(functionName);
    referenceType = FunctionReferenceType.NAME;
  }

  public FunctionCallExpression(TypeSpecifier functionSpecifier) {
    this.functionSpecifier = setup(functionSpecifier);
    referenceType = FunctionReferenceType.TYPE_SPECIFIER;
  }

  public FunctionCallExpression(
      Identifier functionName,
      List<Expression> parameters) {
    this(functionName);
    this.parameters = new ChildNodeList<>(parameters, this);
  }

  public FunctionCallExpression(
      Identifier functionName,
      Stream<Expression> parameters) {
    this(functionName);
    this.parameters = ChildNodeList.collect(parameters, this);
  }

  public FunctionCallExpression(
      TypeSpecifier functionSpecifier,
      List<Expression> parameters) {
    this(functionSpecifier);
    this.parameters = new ChildNodeList<>(parameters, this);
  }

  public FunctionCallExpression(
      TypeSpecifier functionSpecifier,
      Stream<Expression> parameters) {
    this(functionSpecifier);
    this.parameters = ChildNodeList.collect(parameters, this);
  }

  public FunctionCallExpression(Identifier functionName, FunctionCallType functionCallType) {
    this(functionName);
    this.functionCallType = functionCallType;
    if (functionCallType == FunctionCallType.PARAMETERS) {
      parameters = new ChildNodeList<>(this);
    }
  }

  public FunctionCallExpression(TypeSpecifier functionSpecifier, FunctionCallType functionCallType) {
    this(functionSpecifier);
    this.functionCallType = functionCallType;
    if (functionCallType == FunctionCallType.PARAMETERS) {
      parameters = new ChildNodeList<>(this);
    }
  }

  public Identifier getFunctionName() {
    return functionName;
  }

  protected void setFunctionName(Identifier functionName) {
    updateParents(this.functionName, functionName);
    this.functionName = functionName;
  }

  public void useFunctionName(Identifier functionName) {
    setFunctionName(functionName);
    referenceType = FunctionReferenceType.NAME;
    setFunctionSpecifier(null);
  }

  public TypeSpecifier getFunctionSpecifier() {
    return functionSpecifier;
  }

  protected void setFunctionSpecifier(TypeSpecifier functionSpecifier) {
    updateParents(this.functionSpecifier, functionSpecifier);
    this.functionSpecifier = functionSpecifier;
  }

  public void useFunctionSpecifier(TypeSpecifier functionSpecifier) {
    setFunctionSpecifier(functionSpecifier);
    referenceType = FunctionReferenceType.TYPE_SPECIFIER;
    setFunctionName(null);
  }

  public FunctionReferenceType getReferenceType() {
    return referenceType;
  }

  public List<Expression> getParameters() {
    return parameters;
  }

  public void useParameters(List<Expression> parameters) {
    this.parameters = new ChildNodeList<>(parameters, this);
    functionCallType = FunctionCallType.PARAMETERS;
  }

  public void useParameters(Stream<Expression> parameters) {
    this.parameters = ChildNodeList.collect(parameters, this);
    functionCallType = FunctionCallType.PARAMETERS;
  }

  public void useNoParameters() {
    this.parameters = null;
    functionCallType = FunctionCallType.NONE;
  }

  public void useVoidParameters() {
    this.parameters = null;
    functionCallType = FunctionCallType.VOID;
  }

  public FunctionCallType getFunctionCallType() {
    return functionCallType;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.FUNCTION_CALL;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitFunctionCallExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterFunctionCallExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitFunctionCallExpression(this);
  }
}
