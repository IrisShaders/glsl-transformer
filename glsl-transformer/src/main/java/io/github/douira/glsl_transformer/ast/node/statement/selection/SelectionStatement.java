package io.github.douira.glsl_transformer.ast.node.statement.selection;

import java.util.List;
import java.util.stream.Stream;

import com.github.bsideup.jabel.Desugar;

import io.github.douira.glsl_transformer.ast.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.ControlFlowAttributes;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.util.CompatUtil;

public class SelectionStatement extends ManyStatement {
	public List<ControlFlowAttributes> controlFlowAttributes; // TODO: item-wise nullable
	public List<Expression> conditions; // TODO: last one may be null

	@Desugar
	public record Section(
			ControlFlowAttributes controlFlowAttributes,
			Expression condition,
			Statement statement) {
		public Section(Statement statement) {
			this(null, null, statement);
		}

		public Section(Expression condition, Statement statement) {
			this(null, condition, statement);
		}
	}

	public SelectionStatement(
			List<ControlFlowAttributes> controlFlowAttributes,
			List<Expression> conditions,
			List<Statement> statements) {
		super(statements);
		this.controlFlowAttributes = new ChildNodeList<>(controlFlowAttributes, this);
		this.conditions = new ChildNodeList<>(conditions, this);
	}

	public SelectionStatement(
			Stream<ControlFlowAttributes> controlFlowAttributes,
			Stream<Expression> conditions,
			Stream<Statement> statements) {
		super(statements);
		this.controlFlowAttributes = ChildNodeList.collect(controlFlowAttributes, this);
		this.conditions = ChildNodeList.collect(conditions, this);
	}

	public SelectionStatement(Stream<Section> sections) {
		// split the stream of records into streams of its components
		this(
				sections.map(Section::controlFlowAttributes),
				sections.map(Section::condition),
				sections.map(Section::statement));
	}

	public SelectionStatement(
			List<Expression> conditions,
			List<Statement> statements) {
		this(null, conditions, statements);
	}

	public SelectionStatement(
			Stream<Expression> conditions,
			Stream<Statement> statements) {
		this(null, conditions, statements);
	}

	public SelectionStatement(Expression condition, Statement statement) {
		this(CompatUtil.listOf(condition), CompatUtil.listOf(statement));
	}

	public SelectionStatement(Expression condition, Statement ifTrue, Statement ifFalse) {
		this(CompatUtil.listOf(condition, null), CompatUtil.listOf(ifTrue, ifFalse));
	}

	public SelectionStatement(
			Expression firstCondition,
			Statement firstStatement,
			Expression secondCondition,
			Statement secondStatement,
			Statement elseStatement) {
		this(
				CompatUtil.listOf(firstCondition, secondCondition, null),
				CompatUtil.listOf(firstStatement, secondStatement, elseStatement));
	}

	public List<Expression> getConditions() {
		return conditions;
	}

	public List<ControlFlowAttributes> getControlFlowAttributes() {
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
