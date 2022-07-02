package io.github.douira.glsl_transformer.ast.node.statement.selection;

import java.util.List;
import java.util.stream.Stream;

import com.github.bsideup.jabel.Desugar;

import io.github.douira.glsl_transformer.ast.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.traversal.*;
import io.github.douira.glsl_transformer.util.CompatUtil;

public class SelectionStatement extends ManyStatement {
	public List<Expression> conditions; // TODO: last one may be null

	@Desugar
	public record Section(
			Expression condition,
			Statement statement) {
		public Section(Statement statement) {
			this(null, statement);
		}
	}

	public SelectionStatement(
			List<Expression> conditions,
			List<Statement> statements) {
		super(statements);
		this.conditions = new ChildNodeList<>(conditions, this);
	}

	public SelectionStatement(
			Stream<Expression> conditions,
			Stream<Statement> statements) {
		super(statements);
		this.conditions = ChildNodeList.collect(conditions, this);
	}

	public SelectionStatement(Stream<Section> sections) {
		// split the stream of records into streams of its components
		this(
				sections.map(Section::condition),
				sections.map(Section::statement));
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
