package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class CaseStatement extends CaseLabelStatement {
  protected Expression expression;

  public CaseStatement(Expression expression) {
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
  public CaseLabelType getCaseLabelType() {
    return CaseLabelType.CASE;
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.CASE;
  }

  @Override
  public StructureType getStructureType() {
    return StructureType.SEMI_TERMINAL;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitCaseStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterCaseStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitCaseStatement(this);
  }
}
