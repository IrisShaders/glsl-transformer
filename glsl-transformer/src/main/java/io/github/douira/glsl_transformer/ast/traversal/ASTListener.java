package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.*;

public interface ASTListener extends GeneralASTListener {
  default void enterTranslationUnit(TranslationUnit node) {
  }

  default void exitTranslationUnit(TranslationUnit node) {
  }

  default void enterExternalDeclaration(ExternalDeclaration node) {
  }

  default void exitExternalDeclaration(ExternalDeclaration node) {
  }
}
