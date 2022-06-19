package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.*;

public class ASTBaseVisitor<R> implements ASTVisitor<R> {
  @Override
  public R startVisit(ASTNode node) {
    return visit(node);
  }

  @Override
  public R visit(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public R visitSafe(R previousResult, ASTNode node) {
    return node == null ? previousResult : aggregateResult(previousResult, visit(node));
  }

  @Override
  public R visitChildren(R previousResult, ListNode<? extends ASTNode> node) {
    for (var child : node.getChildren()) {
      previousResult = visitSafe(previousResult, child);
    }
    return previousResult;
  }

  @Override
  public R visitChildren(ListNode<? extends ASTNode> node) {
    return visitChildren(initialResult(), node);
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
