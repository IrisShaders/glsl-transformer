package io.github.douira.glsl_transformer.ast.node.expression;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.basic.ListNode;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ManyExpression extends Expression implements ListNode<Expression> {
  protected List<Expression> expressions;

  public ManyExpression(Stream<Expression> expressions) {
    this.expressions = ChildNodeList.collect(expressions, this);
  }

  @Override
  public List<Expression> getChildren() {
    return expressions;
  }

  public List<Expression> getExpressions() {
    return expressions;
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        visitor.visitManyExpression(this),
        expressionAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterManyExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitManyExpression(this);
  }

  @Override
  public ManyExpression clone() {
    var clone = (ManyExpression) super.clone();
    clone.expressions = ChildNodeList.clone(expressions, clone);
    return clone;
  }

  @Override
  public ManyExpression cloneInto(Root root) {
    return (ManyExpression) super.cloneInto(root);
  }

  @Override
  public ManyExpression cloneSeparate() {
    return (ManyExpression) super.cloneSeparate();
  }
}
