package io.github.douira.glsl_transformer.ast.node;

import java.util.Collection;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.basic.ListASTNode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.transform.ASTInjectionPoint;
import io.github.douira.glsl_transformer.ast.traversal.*;

public class TranslationUnit extends ListASTNode<ExternalDeclaration> {
  protected VersionStatement versionStatement;

  public TranslationUnit(VersionStatement versionStatement, Stream<ExternalDeclaration> children) {
    super(children);
    this.versionStatement = setup(versionStatement, this::setVersionStatement);
  }

  public TranslationUnit(Stream<ExternalDeclaration> children) {
    super(children);
  }

  public VersionStatement getVersionStatement() {
    return versionStatement;
  }

  public void setVersionStatement(VersionStatement versionStatement) {
    updateParents(this.versionStatement, versionStatement, this::setVersionStatement);
    this.versionStatement = versionStatement;
  }

  public void injectNode(ASTInjectionPoint injectionPoint, ExternalDeclaration node) {
    children.add(injectionPoint.getInjectionIndex(this), node);
  }

  public void injectNode(
      ASTInjectionPoint injectionPoint,
      Collection<ExternalDeclaration> nodes) {
    children.addAll(injectionPoint.getInjectionIndex(this), nodes);
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
