package io.github.douira.glsl_transformer.ast.node;

import java.util.List;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TranslationUnit extends ListASTNode<ExternalDeclaration> {
  public VersionStatement versionStatement;

  public TranslationUnit(VersionStatement versionStatement, List<ExternalDeclaration> externalDeclarations) {
    super(externalDeclarations);
    this.versionStatement = versionStatement;
  }

  public TranslationUnit(List<ExternalDeclaration> externalDeclarations) {
    super(externalDeclarations);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTranslationUnit(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTranslationUnit(this);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitTranslationUnit(this);
  }
}
