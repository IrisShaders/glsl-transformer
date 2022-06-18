package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.*;

public class TranslationUnit extends ArrayListASTNode<ExternalDeclaration> {
  public VersionStatement versionStatement;

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTranslationUnit(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTranslationUnit(this);
  }

  @Override
  public <R> R accept(ASTVisitor<? extends R> visitor) {
   return visitor.visitTranslationUnit(this);
  }
}
