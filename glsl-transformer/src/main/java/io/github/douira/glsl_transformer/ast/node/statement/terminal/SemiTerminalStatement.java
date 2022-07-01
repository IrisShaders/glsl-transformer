package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public abstract class SemiTerminalStatement extends Statement {
	@Override
	public StructureType getStructureType() {
		return StructureType.SEMI_TERMINAL;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.aggregateResult(
				super.accept(visitor),
				visitor.visitSemiTerminalStatement(this),
				statementAccept(visitor));
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterSemiTerminalStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitSemiTerminalStatement(this);
	}
}
