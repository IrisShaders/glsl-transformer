package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class TerminalStatement extends Statement {
	@Override
	public StructureType getStructureType() {
		return StructureType.TERMINAL;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.aggregateResult(
				super.accept(visitor),
				visitor.visitTerminalStatement(this),
				statementAccept(visitor));
	}

	@Override
	public void enterNode(ASTListener listener) {
		// terminal statements have no children
	}

	@Override
	public void exitNode(ASTListener listener) {
		// terminal statements have no children
	}
}
