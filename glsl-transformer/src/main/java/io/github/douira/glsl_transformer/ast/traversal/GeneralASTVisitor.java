package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.*;

public interface GeneralASTVisitor<R> {
  default R startVisit(ASTNode node) {
    return visit(node);
  };

  R visit(ASTNode node);

  default R visitSafe(R previousResult, ASTNode node) {
    return node == null ? previousResult : aggregateResult(previousResult, visit(node));
  };

  default R visitChildren(R previousResult, ListNode<? extends ASTNode> node) {
    for (var child : node.getChildren()) {
      previousResult = visitSafe(previousResult, child);
    }
    return previousResult;
  };

  default R visitChildren(ListNode<? extends ASTNode> node) {
    return visitChildren(initialResult(), node);
  };

  R initialResult();

  R superNodeTypeResult();

  R defaultResult();

  R aggregateResult(R aggregate, R nextResult);
}
