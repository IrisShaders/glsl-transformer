package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ControlFlowAttribute extends InnerASTNode {
	public Identifier prefix; // TODO: nullable
	public Identifier name;
	public Expression expression; // TODO: nullable

	public ControlFlowAttribute(Identifier name) {
		this.name = name;
	}

	public ControlFlowAttribute(Identifier prefix, Identifier name) {
		this.prefix = prefix;
		this.name = name;
	}

	public ControlFlowAttribute(Identifier name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public ControlFlowAttribute(Identifier prefix, Identifier name, Expression expression) {
		this.prefix = prefix;
		this.name = name;
		this.expression = expression;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.visitControlFlowAttribute(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterControlFlowAttribute(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitControlFlowAttribute(this);
	}
}
