package io.github.douira.glsl_transformer.ast.node.statement.terminal;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class DeclarationStatement extends SemiTerminalStatement {
	public InnerASTNode declaration; // TODO: Declaration

	public DeclarationStatement(InnerASTNode declaration) {
		this.declaration = declaration;
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.DECLARATION;
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitDeclarationStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterDeclarationStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitDeclarationStatement(this);
	}
}
