package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class DoWhileLoopStatement extends ConditionLoopStatement {
	public DoWhileLoopStatement(Statement statement, Expression condition) {
		super(statement, condition);
	}

	public DoWhileLoopStatement(
			ControlFlowAttributes controlFlowAttributes,
			Statement statement,
			Expression condition) {
		super(controlFlowAttributes, statement, condition);
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.DO_WHILE_LOOP;
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitDoWhileLoopStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterDoWhileLoopStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitDoWhileLoopStatement(this);
	}
}
