package io.github.douira.glsl_transformer.ast.node.expression;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.traversal.*;

public class SequenceExpression extends ManyExpression {
  public SequenceExpression(List<Expression> expressions) {
    super(expressions);
  }

  public SequenceExpression(Stream<Expression> expressions) {
    super(expressions);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.SEQUENCE;
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
