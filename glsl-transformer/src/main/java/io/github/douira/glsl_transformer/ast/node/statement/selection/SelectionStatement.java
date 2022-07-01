package io.github.douira.glsl_transformer.ast.node.statement.selection;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class SelectionStatement extends ManyStatement implements BranchingStatement {
	public ControlFlowAttributes controlFlowAttributes; // TODO: nullable
	public List<Expression> conditions;

	public record Section(Expression condition, Statement statement) {
		public Section(Statement statement) {
			this(null, statement);
		}
	}

	public SelectionStatement(
			ControlFlowAttributes controlFlowAttributes,
			List<Statement> statements,
			List<Expression> conditions) {
		super(statements);
		this.controlFlowAttributes = controlFlowAttributes;
		this.conditions = new ChildNodeList<>(conditions, this);
	}

	public SelectionStatement(
			ControlFlowAttributes controlFlowAttributes,
			Stream<Statement> statements,
			Stream<Expression> conditions) {
		super(statements);
		this.controlFlowAttributes = controlFlowAttributes;
		this.conditions = ChildNodeList.collect(conditions, this);
	}

	public SelectionStatement(
			ControlFlowAttributes controlFlowAttributes,
			Stream<Section> sections) {
		// split the stream of records into two streams of its components
		this(
				controlFlowAttributes,
				sections.map(Section::statement),
				sections.map(Section::condition));
	}

	public SelectionStatement(
			List<Statement> statements,
			List<Expression> conditions) {
		this(null, statements, conditions);
	}

	public SelectionStatement(
			Stream<Statement> statements,
			Stream<Expression> conditions) {
		this(null, statements, conditions);
	}

	public SelectionStatement(Stream<Section> sections) {
		this(null, sections);
	}

	public List<Expression> getConditions() {
		return conditions;
	}

	@Override
	public ControlFlowAttributes getControlFlowAttributes() {
		return controlFlowAttributes;
	}

	@Override
	public StatementType getStatementType() {
		return StatementType.SELECTION;
	}

	@Override
	public <R> R statementAccept(ASTVisitor<R> visitor) {
		return visitor.visitSelectionStatement(this);
	}

	@Override
	public void enterNode(ASTListener listener) {
		listener.enterSelectionStatement(this);
	}

	@Override
	public void exitNode(ASTListener listener) {
		listener.exitSelectionStatement(this);
	}
}
