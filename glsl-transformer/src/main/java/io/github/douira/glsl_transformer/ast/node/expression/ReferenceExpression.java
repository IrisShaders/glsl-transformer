package io.github.douira.glsl_transformer.ast.node.expression;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ReferenceExpression extends TerminalExpression {
  public Identifier identifier;

  public ReferenceExpression(Identifier identifier) {
    this.identifier = setup(identifier);
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.REFERENCE;
  }

  @Override
  public <R> R expressionAccept(ASTVisitor<R> visitor) {
    return visitor.visitReferenceExpression(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterReferenceExpression(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitReferenceExpression(this);
  }
}
