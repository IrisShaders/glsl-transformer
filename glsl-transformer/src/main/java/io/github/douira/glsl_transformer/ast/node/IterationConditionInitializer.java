package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class IterationConditionInitializer extends InnerASTNode {
	public InnerASTNode fullySpecifiedType; // TODO: FullySpecifiedType
	public InnerASTNode initializer; // TODO: Initializer

	public IterationConditionInitializer(InnerASTNode fullySpecifiedType, InnerASTNode initializer) {
		this.fullySpecifiedType = fullySpecifiedType;
		this.initializer = initializer;
	}

	@Override
	public <R> R accept(ASTVisitor<R> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterNode(ASTListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitNode(ASTListener listener) {
		// TODO Auto-generated method stub

	}
}
