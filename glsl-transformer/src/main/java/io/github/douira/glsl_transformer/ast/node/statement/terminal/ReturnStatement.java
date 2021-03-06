package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class ReturnStatement extends SemiTerminalStatement {
  protected Expression expression; // TODO: nullable

  public ReturnStatement() {
  }

  public ReturnStatement(Expression expression) {
    this.expression = setup(expression, this::setExpression);
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    updateParents(this.expression, expression, this::setExpression);
    this.expression = expression;
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.RETURN;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitReturnStatement(this);
  }
}
