package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ExpressionStatement extends SemiTerminalStatement {
	public Expression expression;

  public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}

  @Override
  public StatementType getStatementType() {
		return StatementType.EXPRESSION;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitExpressionStatement(this);
  }

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterExpressionStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitExpressionStatement(this);
	}
}
