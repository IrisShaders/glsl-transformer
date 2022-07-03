package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.basic.*;

public interface ASTVoidVisitor extends ASTVisitor<Void> {
  default void visitVoid(ASTNode node) {
  }

  @Override
  default Void visit(ASTNode node) {
    visitVoid(node);
    node.accept(this);
    return null;
  }

  @Override
  default Void visit(Void previousResult, ASTNode node) {
    visit(node);
    return null;
  }

  @Override
  default Void visitSafe(Void previousResult, ASTNode node) {
    if (node != null) {
      visit(node);
    }
    return null;
  }

  @Override
  default Void visitChildren(Void previousResult, ListNode<? extends ASTNode> node) {
    for (var child : node.getChildren()) {
      if (child != null) {
        visit(child);
      }
    }
    return null;
  };

  @Override
  default Void aggregateResult(Void aggregate, Void nextResult) {
    return null;
  }

  @Override
  default Void aggregateResult(Void aggregate, Void firstResult, Void secondResult) {
    return null;
  }

  @Override
  default Void defaultResult() {
    return null;
  }

  @Override
  default Void initialResult() {
    return null;
  }

  @Override
  default Void superNodeTypeResult() {
    return null;
  }
}
