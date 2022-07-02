package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class WhileLoopStatement extends ConditionLoopStatement {
	public IterationConditionInitializer iterationConditionInitializer;

	public WhileLoopStatement(Expression condition, Statement statement) {
		super(statement, condition);
	}

	public WhileLoopStatement(
			ControlFlowAttributes controlFlowAttributes,
			Expression condition,
			Statement statement) {
		super(controlFlowAttributes, statement, condition);
	}

	public WhileLoopStatement(
			IterationConditionInitializer iterationConditionInitializer,
			Statement statement) {
		super(statement, null);
		this.iterationConditionInitializer = iterationConditionInitializer;
	}

	public WhileLoopStatement(
			ControlFlowAttributes controlFlowAttributes,
			IterationConditionInitializer iterationConditionInitializer,
			Statement statement) {
		super(controlFlowAttributes, statement, null);
		this.iterationConditionInitializer = iterationConditionInitializer;
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.WHILE_LOOP;
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitWhileLoopStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterWhileLoopStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitWhileLoopStatement(this);
	}
}
