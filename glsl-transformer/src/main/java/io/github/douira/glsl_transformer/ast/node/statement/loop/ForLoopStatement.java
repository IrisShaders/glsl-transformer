package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ForLoopStatement extends LoopStatement {
  public Expression initExpression; // TODO: nullable
  public InnerASTNode initDeclaration; // TODO: nullable, Declaration
  public Expression condition; // TODO: nullable
  public IterationConditionInitializer iterationConditionInitializer; // TODO: nullable
  public Expression incrementer; // TODO: nullable

  public ForLoopStatement(
      Statement statement,
      Expression initExpression,
      Expression condition,
      Expression incrementer) {
    super(statement);
    this.initExpression = initExpression;
    this.condition = condition;
    this.incrementer = incrementer;
  }

  public ForLoopStatement(
      Statement statement,
      InnerASTNode initDeclaration,
      Expression condition,
      Expression incrementer) {
    super(statement);
    this.initDeclaration = initDeclaration;
    this.condition = condition;
    this.incrementer = incrementer;
  }

  public ForLoopStatement(
      Statement statement,
      Expression initExpression,
      IterationConditionInitializer iterationConditionInitializer,
      Expression incrementer) {
    super(statement);
    this.initExpression = initExpression;
    this.iterationConditionInitializer = iterationConditionInitializer;
    this.incrementer = incrementer;
  }

  public ForLoopStatement(
      Statement statement,
      InnerASTNode initDeclaration,
      IterationConditionInitializer iterationConditionInitializer,
      Expression incrementer) {
    super(statement);
    this.initDeclaration = initDeclaration;
    this.iterationConditionInitializer = iterationConditionInitializer;
    this.incrementer = incrementer;
  }

  public ForLoopStatement(Statement statement) {
    super(statement);
  }

  public ForLoopStatement(
      Expression initExpression,
      InnerASTNode initDeclaration,
      Expression condition,
      IterationConditionInitializer iterationConditionInitializer,
      Expression incrementer,
      Statement statement) {
    super(statement);
    this.initExpression = initExpression;
    this.initDeclaration = initDeclaration;
    this.condition = condition;
    this.iterationConditionInitializer = iterationConditionInitializer;
    this.incrementer = incrementer;
  }

  @Override
  public StatementType getStatementType() {
    return StatementType.FOR_LOOP;
  }

  @Override
  public <R> R statementAccept(ASTVisitor<R> visitor) {
    return visitor.visitForLoopStatement(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    super.enterNode(listener);
    listener.enterForLoopStatement(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    super.exitNode(listener);
    listener.exitForLoopStatement(this);
  }
}
