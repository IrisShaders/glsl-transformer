package io.github.douira.glsl_transformer.ast;

public class ASTBaseVisitor<R> implements ASTVisitor<R> {
  @Override
  public R startVisit(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public R visit(ASTNode node) {
    if (node instanceof InnerASTNode innerNode) {
      return innerNode.visitChildren(this);
    }
    return defaultResult();
  }

  @Override
  public R visit(R previousResult, ASTNode node) {
    return aggregateResult(previousResult, visit(node));
  }

  @Override
  public R defaultResult() {
    return null;
  }

  public R aggregateResult(R aggregate, R nextResult) {
    return nextResult;
  }
}
