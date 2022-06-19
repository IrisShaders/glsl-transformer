package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.*;

public interface GeneralASTVisitor<R> {
  R startVisit(ASTNode node);

  R visit(ASTNode node);

  R visitSafe(R previousResult, ASTNode node);

  R visitChildren(R previousResult, ListNode<? extends ASTNode> node);

  R visitChildren(ListNode<? extends ASTNode> node);

  R initialResult();

  R superNodeTypeResult();

  R defaultResult();

  R aggregateResult(R aggregate, R nextResult);
}
