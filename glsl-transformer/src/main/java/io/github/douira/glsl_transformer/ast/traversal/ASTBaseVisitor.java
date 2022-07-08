package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;

public abstract class ASTBaseVisitor<R> implements ASTVisitor<R>, ContextTracker {
  protected ASTNode context;

  @Override
  public R startVisit(ASTNode node) {
    context = node;
    return ASTVisitor.super.startVisit(node);
  }

  @Override
  public R visit(ASTNode node) {
    var previousContext = context;
    setContext(node);
    var result = visitRaw(node);
    enterContext(previousContext);
    return result;
  }

  protected void setContext(ASTNode node) {
    context = node;
    enterContext(node);
  }

  protected R visitRaw(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public R initialResult() {
    return defaultResult();
  }

  @Override
  public R superNodeTypeResult() {
    return defaultResult();
  }

  @Override
  public R defaultResult() {
    return null;
  }

  @Override
  public R aggregateResult(R aggregate, R nextResult) {
    return nextResult;
  }
}
