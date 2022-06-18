package io.github.douira.glsl_transformer.ast;

public class ASTBaseVisitor<R> implements ASTVisitor<R> {
  @Override
  public R startVisit(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public R visit(ASTNode node) {
    return defaultResult();
  }

  protected R defaultResult() {
    return null;
  }
}
