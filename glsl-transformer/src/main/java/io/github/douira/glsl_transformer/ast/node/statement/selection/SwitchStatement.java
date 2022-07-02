package io.github.douira.glsl_transformer.ast.node.statement.selection;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

//TODO: implement, add control flow attributes
public class SwitchStatement extends ManyStatement {

	public SwitchStatement(List<Statement> statements) {
		super(statements); // TODO
	}

	public SwitchStatement(Stream<Statement> statements) {
		super(statements); // TODO
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.SWITCH; // TODO: type
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitSwitchStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterSwitchStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitSwitchStatement(this);
	}
}
