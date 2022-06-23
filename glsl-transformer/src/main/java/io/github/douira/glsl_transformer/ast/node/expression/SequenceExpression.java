package io.github.douira.glsl_transformer.ast.node.expression;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class SequenceExpression extends ManyExpression implements ListNode<Expression> {
  public List<Expression> expressions;

  public SequenceExpression(List<Expression> expressions) {
    this.expressions = new ChildNodeList<>(expressions, this);
  }

  public SequenceExpression(Stream<Expression> expressions) {
    this.expressions = ChildNodeList.collect(expressions, this);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.SEQUENCE;
  }

  @Override
  public List<Expression> getChildren() {
    return expressions;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitSequenceExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterSequenceExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitSequenceExpression(this);
  }
}
