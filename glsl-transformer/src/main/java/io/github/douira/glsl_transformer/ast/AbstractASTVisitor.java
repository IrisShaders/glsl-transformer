package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.*;

public abstract class AbstractASTVisitor<R> implements GeneralASTVisitor<R> {
  @Override
  public R visit(ASTNode node) {
    return node.accept(this);
  }

  @Override
  public R visitChildren(InnerASTNode node) {
    var result = defaultResult();
    int n = node.getChildCount();
    for (int i = 0; i < n; i++) {
      if (!shouldVisitNextChild(node, result)) {
        break;
      }

      var child = node.getChild(i);
      var childResult = child.accept(this);
      result = aggregateResult(result, childResult);
    }

    return result;
  }

  @Override
  public R visitDefault(ASTNode node) {
    return defaultResult();
  }

  protected R defaultResult() {
    return null;
  }

  protected R aggregateResult(R aggregate, R nextResult) {
    return nextResult;
  }

  protected boolean shouldVisitNextChild(ASTNode node, R currentResult) {
    return true;
  }
}
