package io.github.douira.glsl_transformer.ast.node.statement.selection;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class SwitchStatement extends Statement {
  public Expression expression;
  public CompoundStatement statement;

  public SwitchStatement(Expression expression, CompoundStatement statement) {
    this.expression = setup(expression);
    this.statement = setup(statement);
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.SWITCH;
  }

  @Override
  public StructureType getStructureType() {
    return StructureType.UNARY;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitSwitchStatement(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.aggregateResult(
        super.accept(visitor),
        statementAccept(visitor));
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterSwitchStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitSwitchStatement(this);
  }
}
