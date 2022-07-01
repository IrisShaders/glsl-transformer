package io.github.douira.glsl_transformer.ast.node;

import java.util.List;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.ListASTNode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TranslationUnit extends ListASTNode<ExternalDeclaration> {
  public VersionStatement versionStatement;

  public TranslationUnit(VersionStatement versionStatement, List<ExternalDeclaration> externalDeclarations) {
    super(externalDeclarations);
    this.versionStatement = versionStatement;
    versionStatement.setParent(this);
  }

  public TranslationUnit(List<ExternalDeclaration> externalDeclarations) {
    super(externalDeclarations);
  }

  public TranslationUnit(VersionStatement versionStatement, Stream<ExternalDeclaration> children) {
    super(children);
    this.versionStatement = versionStatement;
  }

  public TranslationUnit(Stream<ExternalDeclaration> children) {
    super(children);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitTranslationUnit(this);
  }

  @Override
  public void enterNode(ASTListener listener) {
    listener.enterTranslationUnit(this);
  }

  @Override
  public void exitNode(ASTListener listener) {
    listener.exitTranslationUnit(this);
  }
}
