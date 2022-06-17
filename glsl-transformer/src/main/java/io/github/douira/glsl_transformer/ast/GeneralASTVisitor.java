package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.*;

public interface GeneralASTVisitor<R> {
  R visit(ASTNode node);

  R visitChildren(InnerASTNode node);

  R visitDefault(ASTNode node);
}
