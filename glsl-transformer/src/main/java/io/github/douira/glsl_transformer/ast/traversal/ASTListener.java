package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;

public interface ASTListener extends GeneralASTListener {
  default void enterTranslationUnit(TranslationUnit node) {
  }

  default void exitTranslationUnit(TranslationUnit node) {
  }

  default void enterExternalDeclaration(ExternalDeclaration node) {
  }

  default void exitExternalDeclaration(ExternalDeclaration node) {
  }

  default void enterLayoutDefaults(LayoutDefaults node) {
  }

  default void exitLayoutDefaults(LayoutDefaults node) {
  }

  default void enterExpression(Expression node) {
  }

  default void exitExpression(Expression node) {
  }

  default void enterUnaryExpression(UnaryExpression node) {
  }

  default void exitUnaryExpression(UnaryExpression node) {
  }

  default void enterBitwiseNotExpression(BitwiseNotExpression node) {
  }

  default void exitBitwiseNotExpression(BitwiseNotExpression node) {
  }

  default void enterBooleanNotExpression(BooleanNotExpression node) {
  }

  default void exitBooleanNotExpression(BooleanNotExpression node) {
  }

  default void enterDecrementPostfixExpression(DecrementPostfixExpression node) {
  }

  default void exitDecrementPostfixExpression(DecrementPostfixExpression node) {
  }

  default void enterDecrementPrefixExpression(DecrementPrefixExpression node) {
  }

  default void exitDecrementPrefixExpression(DecrementPrefixExpression node) {
  }

  default void enterFunctionCallExpression(FunctionCallExpression node) {
  }

  default void exitFunctionCallExpression(FunctionCallExpression node) {
  }

  default void enterGroupingExpression(GroupingExpression node) {
  }

  default void exitGroupingExpression(GroupingExpression node) {
  }

  default void enterIncrementPostfixExpression(IncrementPostfixExpression node) {
  }

  default void exitIncrementPostfixExpression(IncrementPostfixExpression node) {
  }

  default void enterIncrementPrefixExpression(IncrementPrefixExpression node) {
  }

  default void exitIncrementPrefixExpression(IncrementPrefixExpression node) {
  }

  default void enterMemberAccessExpression(MemberAccessExpression node) {
  }

  default void exitMemberAccessExpression(MemberAccessExpression node) {
  }

  default void enterMethodCallExpression(MethodCallExpression node) {
  }

  default void exitMethodCallExpression(MethodCallExpression node) {
  }

  default void enterNegationExpression(NegationExpression node) {
  }

  default void exitNegationExpression(NegationExpression node) {
  }

  default void enterIdentityExpression(IdentityExpression node) {
  }

  default void exitIdentityExpression(IdentityExpression node) {
  }

  default void enterBinaryExpression(BinaryExpression node) {
  }

  default void exitBinaryExpression(BinaryExpression node) {
  }

  default void enterTernaryExpression(TernaryExpression node) {
  }

  default void exitTernaryExpression(TernaryExpression node) {
  }

  default void enterConditionExpression(ConditionExpression node) {
  }

  default void exitConditionExpression(ConditionExpression node) {
  }

  default void enterStatement(Statement node) {
  }

  default void exitStatement(Statement node) {
  }

  default void enterCompoundStatement(CompoundStatement node) {
  }

  default void exitCompoundStatement(CompoundStatement node) {
  }
}
