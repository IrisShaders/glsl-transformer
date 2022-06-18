package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;

public interface ASTListener extends GeneralASTListener {
    default void enterTranslationUnit(TranslationUnit node) {
  }

  default void exitTranslationUnit(TranslationUnit node) {
  }
}
