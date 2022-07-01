package io.github.douira.glsl_transformer.ast.node.statement.selection;

import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class CaseLabelStatement extends Statement {
	public CaseLabelStatement() {
		super(); // TODO: constructor
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.CASE_LABEL;
	}

	@Override
	public StructureType getStructureType() {
		return StructureType.SPECIAL;
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitCaseLabelStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterCaseLabelStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitCaseLabelStatement(this);
	}

}
