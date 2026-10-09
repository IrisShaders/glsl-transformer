package io.github.douira.glsl_transformer.ast.typing;

import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.statement.*;
import io.github.douira.glsl_transformer.ast.node.statement.loop.*;
import io.github.douira.glsl_transformer.ast.node.statement.selection.*;
import io.github.douira.glsl_transformer.ast.node.statement.terminal.*;

/**
 * Analyzes statements: it manages the scopes that statements form and checks
 * conditions, switch statements, case labels and return statements.
 */
final class StatementAnalyzer {
  private final AnalysisContext ctx;

  StatementAnalyzer(AnalysisContext ctx) {
    this.ctx = ctx;
  }

  void statement(Statement node) {
    switch (node.getStatementType()) {
      case COMPOUND -> {
        ctx.pushScope(node);
        body(node);
        ctx.popScope();
      }
      case DECLARATION -> ctx.declarations.declaration(((DeclarationStatement) node).getDeclaration());
      case EXPRESSION -> ctx.expressions.type(((ExpressionStatement) node).getExpression());
      case SELECTION -> {
        var selection = (SelectionStatement) node;
        condition(selection.getCondition());
        branch(selection.getIfTrue());
        if (selection.hasIfFalse()) {
          branch(selection.getIfFalse());
        }
      }
      case SWITCH -> {
        var switchStatement = (SwitchStatement) node;
        integer(switchStatement.getExpression(), "The value of a switch statement");
        ctx.pushScope(node);
        body(switchStatement.getStatement());
        ctx.popScope();
      }
      case CASE -> {
        var expression = ((CaseStatement) node).getExpression();
        if (integer(expression, "A case label") && ctx.constants.evaluate(expression) == null) {
          ctx.error(DiagnosticCode.NOT_CONSTANT, expression, "A case label has to be a constant expression");
        }
      }
      case FOR_LOOP -> {
        var loop = (ForLoopStatement) node;
        ctx.pushScope(node);
        if (loop.getInitExpression() != null) {
          ctx.expressions.type(loop.getInitExpression());
        }
        if (loop.getInitDeclaration() != null) {
          ctx.declarations.declaration(loop.getInitDeclaration());
        }
        if (loop.getCondition() != null) {
          condition(loop.getCondition());
        }
        if (loop.getIterationConditionInitializer() != null) {
          conditionType(loop.getIterationConditionInitializer(),
              ctx.declarations.iterationConditionInitializer(loop.getIterationConditionInitializer()));
        }
        if (loop.getIncrementer() != null) {
          ctx.expressions.type(loop.getIncrementer());
        }
        body(loop.getStatement());
        ctx.popScope();
      }
      case WHILE_LOOP -> {
        var loop = (WhileLoopStatement) node;
        ctx.pushScope(node);
        if (loop.getCondition() != null) {
          condition(loop.getCondition());
        } else {
          conditionType(loop.getIterationConditionInitializer(),
              ctx.declarations.iterationConditionInitializer(loop.getIterationConditionInitializer()));
        }
        body(loop.getStatement());
        ctx.popScope();
      }
      case DO_WHILE_LOOP -> {
        // the condition is not in the scope of the loop body
        var loop = (DoWhileLoopStatement) node;
        branch(loop.getStatement());
        condition(loop.getCondition());
      }
      case RETURN -> {
        var expression = ((ReturnStatement) node).getExpression();
        var type = expression == null ? FixedType.VOID : ctx.expressions.type(expression);
        if (ctx.returnType != null && !ctx.converts(type, ctx.returnType)) {
          ctx.error(DiagnosticCode.INVALID_RETURN, node, "Cannot return a value of type " + type.getTypeName()
              + " from a function with the return type " + ctx.returnType.getTypeName());
        }
      }
      case EMPTY, DEFAULT, CONTINUE, BREAK, DISCARD, DEMOTE, IGNORE_INTERSECTION, TERMINATE_RAY -> {
      }
    }
  }

  /**
   * Analyzes a statement without creating a new scope if it is a compound
   * statement. This is the case for function, loop and switch bodies, which
   * share the scope of the function parameters, loop header or switch.
   */
  void body(Statement node) {
    if (node instanceof CompoundStatement compound) {
      for (var statement : compound.getStatements()) {
        statement(statement);
      }
    } else {
      statement(node);
    }
  }

  /**
   * Analyzes a statement that forms a scope even if it is not a compound
   * statement, which is the case for the branches of selection statements.
   */
  private void branch(Statement node) {
    ctx.pushScope(node);
    body(node);
    ctx.popScope();
  }

  private void condition(Expression node) {
    conditionType(node, ctx.expressions.type(node));
  }

  private void conditionType(ASTNode node, Type type) {
    if (!Conversions.isSameType(type, NumericType.BOOL)) {
      ctx.error(DiagnosticCode.INVALID_CONDITION, node,
          "A condition has to be a bool but is " + type.getTypeName());
    }
  }

  private boolean integer(Expression node, String description) {
    var type = ctx.expressions.type(node);
    var isInteger = type instanceof NumericType numeric && numeric.isScalar()
        && numeric.getNumberType().isInteger();
    if (!isInteger && !type.isError()) {
      ctx.error(DiagnosticCode.INVALID_SWITCH, node,
          description + " has to be an integer but is " + type.getTypeName());
    }
    return isInteger;
  }
}
