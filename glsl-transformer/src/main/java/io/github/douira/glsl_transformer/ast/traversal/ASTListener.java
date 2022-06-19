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

  default void enterBinaryExpression(BinaryExpression node) {
  }

  default void exitBinaryExpression(BinaryExpression node) {
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
