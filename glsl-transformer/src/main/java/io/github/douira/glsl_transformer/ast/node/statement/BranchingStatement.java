package io.github.douira.glsl_transformer.ast.node.statement;

import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;

public interface BranchingStatement {
	ControlFlowAttributes getControlFlowAttributes();
}
