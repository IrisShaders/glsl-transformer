package io.github.douira.glsl_transformer.ast.node.statement.selection;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.data.ChildNodeList;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class SelectionStatement extends ManyStatement {
  protected List<Expression> conditions; // TODO: last one may be null

  public SelectionStatement(
      Stream<Expression> conditions,
      Stream<Statement> statements) {
    super(statements);
    this.conditions = ChildNodeList.collect(conditions, this);
  }

  public SelectionStatement(Expression condition, Statement statement) {
    this(Stream.of(condition), Stream.of(statement));
  }

  public SelectionStatement(Expression condition, Statement ifTrue, Statement ifFalse) {
    this(Stream.of(condition, null), Stream.of(ifTrue, ifFalse));
  }

  public SelectionStatement(
      Expression firstCondition,
      Statement firstStatement,
      Expression secondCondition,
      Statement secondStatement,
      Statement elseStatement) {
    this(
        Stream.of(firstCondition, secondCondition, null),
        Stream.of(firstStatement, secondStatement, elseStatement));
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
    super.enterNode(listener);
    listener.enterSelectionStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitSelectionStatement(this);
  }

  @Override
  public SelectionStatement clone() {
    var result = (SelectionStatement) super.clone();
    result.conditions = ChildNodeList.clone(conditions, result);
    return result;
  }

  @Override
  public SelectionStatement cloneInto(Root root) {
    return (SelectionStatement) super.cloneInto(root);
  }

  @Override
  public SelectionStatement cloneSeparate() {
    return (SelectionStatement) super.cloneSeparate();
  }
}
