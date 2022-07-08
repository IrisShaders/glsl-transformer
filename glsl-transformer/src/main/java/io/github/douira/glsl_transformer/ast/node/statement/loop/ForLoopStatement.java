package io.github.douira.glsl_transformer.ast.node.statement.loop;

import io.github.douira.glsl_transformer.ast.node.IterationConditionInitializer;
import io.github.douira.glsl_transformer.ast.node.basic.InnerASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class ForLoopStatement extends LoopStatement {
  protected Expression initExpression; // TODO: nullable
  protected InnerASTNode initDeclaration; // TODO: nullable, Declaration
  protected Expression condition; // TODO: nullable
  protected IterationConditionInitializer iterationConditionInitializer; // TODO: nullable
  protected Expression incrementer; // TODO: nullable

  public ForLoopStatement(
      Statement statement,
      Expression initExpression,
      Expression condition,
      Expression incrementer) {
    super(statement);
    this.initExpression = setup(initExpression);
    this.condition = setup(condition);
    this.incrementer = setup(incrementer);
  }

  public ForLoopStatement(
      Statement statement,
      InnerASTNode initDeclaration,
      Expression condition,
      Expression incrementer) {
    super(statement);
    this.initDeclaration = setup(initDeclaration);
    this.condition = setup(condition);
    this.incrementer = setup(incrementer);
  }

  public ForLoopStatement(
      Statement statement,
      Expression initExpression,
      IterationConditionInitializer iterationConditionInitializer,
      Expression incrementer) {
    super(statement);
    this.initExpression = setup(initExpression);
    this.iterationConditionInitializer = setup(iterationConditionInitializer);
    this.incrementer = setup(incrementer);
  }

  public ForLoopStatement(
      Statement statement,
      InnerASTNode initDeclaration,
      IterationConditionInitializer iterationConditionInitializer,
      Expression incrementer) {
    super(statement);
    this.initDeclaration = setup(initDeclaration);
    this.iterationConditionInitializer = setup(iterationConditionInitializer);
    this.incrementer = setup(incrementer);
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
    this.initExpression = setup(initExpression);
    this.initDeclaration = setup(initDeclaration);
    this.condition = setup(condition);
    this.iterationConditionInitializer = setup(iterationConditionInitializer);
    this.incrementer = setup(incrementer);
  }

  public Expression getInitExpression() {
    return initExpression;
  }

  public void setInitExpression(Expression initExpression) {
    updateParents(this.initExpression, initExpression);
    this.initExpression = initExpression;
  }

  public InnerASTNode getInitDeclaration() {
    return initDeclaration;
  }

  public void setInitDeclaration(InnerASTNode initDeclaration) {
    updateParents(this.initDeclaration, initDeclaration);
    this.initDeclaration = initDeclaration;
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    updateParents(this.condition, condition);
    this.condition = condition;
  }

  public IterationConditionInitializer getIterationConditionInitializer() {
    return iterationConditionInitializer;
  }

  public void setIterationConditionInitializer(IterationConditionInitializer iterationConditionInitializer) {
    updateParents(this.iterationConditionInitializer, iterationConditionInitializer);
    this.iterationConditionInitializer = iterationConditionInitializer;
  }

  public Expression getIncrementer() {
    return incrementer;
  }

  public void setIncrementer(Expression incrementer) {
    updateParents(this.incrementer, incrementer);
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
