package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class LoopStatement extends Statement implements BranchingStatement {
	public ControlFlowAttributes controlFlowAttributes; // TODO: nullable
	public Statement statement;

	public LoopStatement(Statement statement) {
		this.statement = statement;
	}

	public LoopStatement(
			ControlFlowAttributes controlFlowAttributes,
			Statement statement) {
		this.controlFlowAttributes = controlFlowAttributes;
		this.statement = statement;
	}

	@Override
	public ControlFlowAttributes getControlFlowAttributes() {
		return controlFlowAttributes;
	}

	@Override
	public StructureType getStructureType() {
		return StructureType.UNARY;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.aggregateResult(
				super.accept(visitor),
				visitor.visitLoopStatement(this),
				statementAccept(visitor));
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterLoopStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitLoopStatement(this);
	}
}
