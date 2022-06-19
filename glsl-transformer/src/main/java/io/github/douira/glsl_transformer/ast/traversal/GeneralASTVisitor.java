package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.ASTNode;

public interface GeneralASTVisitor<R> {
  R startVisit(ASTNode node);

  R visit(ASTNode node);

  R visit(R previousResult, ASTNode node);

  R initialResult();

  R superNodeTypeResult();

  R defaultResult();

  R aggregateResult(R aggregate, R nextResult);
}
