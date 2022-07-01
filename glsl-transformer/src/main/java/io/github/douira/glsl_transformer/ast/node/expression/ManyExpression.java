package io.github.douira.glsl_transformer.ast.node.expression;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class ManyExpression extends Expression implements ListNode<Expression> {
  public List<Expression> expressions;

  public ManyExpression(List<Expression> expressions) {
    this.expressions = new ChildNodeList<>(expressions, this);
  }

  public ManyExpression(Stream<Expression> expressions) {
    this.expressions = ChildNodeList.collect(expressions, this);
  }

  @Override
  public List<Expression> getChildren() {
    return expressions;
  }

  @Override
  public OperandStructure getOperandStructure() {
    return OperandStructure.MANY;
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
    listener.enterManyExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitManyExpression(this);
  }
}
