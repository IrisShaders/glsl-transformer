package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.*;

public class ASTBaseVisitor<R> implements ASTVisitor<R> {
  @Override
  public R visit(ASTNode node) {
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
