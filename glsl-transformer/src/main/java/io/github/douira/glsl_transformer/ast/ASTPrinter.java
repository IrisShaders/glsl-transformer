package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public abstract class ASTPrinter extends ASTListenerVisitor<Void> {
  private ASTPrinter() {
  }

  protected abstract String generateString();

  public static String printAST(ASTPrinter visitor, ASTNode node) {
    node.accept(visitor);
    return visitor.generateString();
  }

  protected abstract void emitToken(PrintToken token);

  protected void emitTokens(PrintToken... token) {
    for (PrintToken t : token) {
      emitToken(t);
    }
  }

  protected void emitLiteral(ASTNode source, String literal) {
    emitToken(new LiteralToken(source, literal));
  }

  protected void emitLiterals(ASTNode source, String... literal) {
    for (String l : literal) {
      emitLiteral(source, l);
    }
  }

  protected void emitType(ASTNode source, int type) {
    emitToken(new ParserToken(source, type));
  }

  protected void emitType(ASTNode source, int... type) {
    for (int t : type) {
      emitType(source, t);
    }
  }

  protected void emitWhitespace(ASTNode source, String whitespace) {
    emitToken(new LiteralToken(source, TokenChannel.WHITESPACE, whitespace));
  }

  protected void emitSpace(ASTNode source) {
    emitWhitespace(source, " ");
  }

  protected void emitNewline(ASTNode source) {
    emitWhitespace(source, "\n");
  }

  @Override
  public Void initialResult() {
    return null;
  }

  @Override
  public Void defaultResult() {
    throw new IllegalStateException("The default value should never be used and all nodes should be printed properly!");
  }

  @Override
  public void exitTranslationUnit(TranslationUnit node) {
    emitType(node, GLSLLexer.EOF);
  }

  @Override
  public Void visitVersionStatement(VersionStatement node) {
    emitType(node, GLSLLexer.VERSION);
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
