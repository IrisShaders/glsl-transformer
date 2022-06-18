package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;

public abstract class ASTPrinter extends ASTPrinterUtil {
  @Override
  public void exitTranslationUnit(TranslationUnit node) {
    emitToken(new EOFToken(node));
  }

  @Override
  public Void visitVersionStatement(VersionStatement node) {
    emitType(node, GLSLLexer.NR, GLSLLexer.VERSION);
    emitSpace(node);
    emitLiteral(node, Integer.toString(node.version));
    if (node.profile != null) {
      emitSpace(node);
      emitType(node, node.profile.tokenType);
    }
    emitNewline(node);
    return null;
  }

  @Override
  public Void visitEmptyDeclaration(EmptyDeclaration node) {
    emitType(node, GLSLLexer.SEMICOLON);
    return null;
  }

  @Override
  public Void visitIdentifier(Identifier node) {
    emitLiteral(node, node.name);
    return null;
  }
}
