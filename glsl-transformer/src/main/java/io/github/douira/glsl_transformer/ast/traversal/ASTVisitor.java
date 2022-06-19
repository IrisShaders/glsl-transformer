package io.github.douira.glsl_transformer.ast.traversal;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;

public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  default R visitTranslationUnit(TranslationUnit node) {
    var result = initialResult();
    result = visitSafe(result, node.versionStatement);
    visitChildren(result, node);
    return result;
  }

  default R visitVersionStatement(VersionStatement node) {
    return defaultResult();
  }

  default R visitExternalDeclaration(ExternalDeclaration node) {
    return superNodeTypeResult();
  }

  default R visitEmptyDeclaration(EmptyDeclaration node) {
    return defaultResult();
  }

  default R visitPragmaStatement(PragmaStatement node) {
    return defaultResult();
  }

  default R visitExtensionStatement(ExtensionStatement node) {
    return superNodeTypeResult();
  }

  default R visitLayoutDefaults(LayoutDefaults node) {
    return visit(node.qualifier); // TODO: LayoutQualifier
  }

  default R visitExpression(Expression node) {
    return superNodeTypeResult();
  }

  default R visitUnaryExpression(UnaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitBinaryExpression(BinaryExpression node) {
    return superNodeTypeResult();
  }

  default R visitStatement(Statement node) {
    return superNodeTypeResult();
  }

  default R visitEmptyStatement(EmptyStatement node) {
    return defaultResult();
  }

  default R visitCompoundStatement(CompoundStatement node) {
    return visitChildren(node);
  }

  default R visitIdentifier(Identifier node) {
    return defaultResult();
  }
}
