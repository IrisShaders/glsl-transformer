package io.github.douira.glsl_transformer.ast.node;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.ListASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ControlFlowAttributes extends ListASTNode<ControlFlowAttribute> {
	public ControlFlowAttributes(List<ControlFlowAttribute> children) {
		super(children);
	}

	public ControlFlowAttributes(Stream<ControlFlowAttribute> children) {
		super(children);
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		return visitor.visitControlFlowAttributes(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterControlFlowAttributes(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitControlFlowAttributes(this);
	}
}
