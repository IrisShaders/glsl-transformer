package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.*;

public interface ASTVisitor<R> extends GeneralASTVisitor<R> {
  default R visitTranslationUnit(TranslationUnit node) {
    return visit(node);
  }

  default R visitVersionStatement(VersionStatement node) {
    return visit(node);
  }

  default R visitEmptyDeclaration(EmptyDeclaration node) {
    return visit(node);
  }

  default R visitIdentifier(Identifier node) {
    return visit(node);
  }
}
