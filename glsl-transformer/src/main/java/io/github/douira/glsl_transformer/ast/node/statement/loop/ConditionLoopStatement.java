package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;

public abstract class ConditionLoopStatement extends LoopStatement {
	public Expression condition;

	public ConditionLoopStatement(Statement statement, Expression condition) {
		super(statement);
		this.condition = condition;
	}

	public ConditionLoopStatement(
			ControlFlowAttributes controlFlowAttributes,
			Statement statement,
			Expression condition) {
		super(controlFlowAttributes, statement);
		this.condition = condition;
	}
}
