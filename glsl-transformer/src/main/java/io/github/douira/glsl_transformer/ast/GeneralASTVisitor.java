package io.github.douira.glsl_transformer.ast;

public interface GeneralASTVisitor<R> {
  R startVisit(ASTNode node);

  R visit(ASTNode node);
}
