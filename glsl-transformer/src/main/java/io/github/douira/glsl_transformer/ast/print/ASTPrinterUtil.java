package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.print.token.*;
import io.github.douira.glsl_transformer.ast.traversal.ASTListenerVisitor;
import io.github.douira.glsl_transformer.print.filter.TokenChannel;

public abstract class ASTPrinterUtil extends ASTListenerVisitor<Void> {
  protected ASTPrinterUtil() {
    super();
  }

  protected abstract String generateString();

  protected abstract void emitToken(PrintToken token);

  public static String printAST(ASTPrinter printer, ASTNode node) {
    printer.visit(node);
    return printer.generateString();
  }

  public static String printSimple(ASTNode node) {
    return printAST(new SimpleASTPrinter(), node);
  }

  protected void emitTokens(PrintToken... token) {
    for (PrintToken t : token) {
      emitToken(t);
    }
  }

  protected void emitLiteral(ASTNode node, String literal) {
    emitToken(new LiteralToken(node, literal));
  }

  protected void emitLiterals(ASTNode node, String... literal) {
    for (String l : literal) {
      emitLiteral(node, l);
    }
  }

  protected void emitType(ASTNode node, int type) {
    emitToken(new ParserToken(node, type));
  }

  protected void emitType(ASTNode node, int... type) {
    for (int t : type) {
      emitType(node, t);
    }
  }

  protected void emitWhitespace(ASTNode node, String whitespace) {
    emitToken(new LiteralToken(node, TokenChannel.WHITESPACE, whitespace));
  }

  protected void emitSpace(ASTNode node) {
    emitWhitespace(node, " ");
  }

  protected void emitNewline(ASTNode node) {
    emitWhitespace(node, "\n");
  }

  @Override
  public Void initialResult() {
    return null;
  }

  @Override
  public Void superNodeTypeResult() {
    return null;
  }

  @Override
  public Void defaultResult() {
    throw new IllegalStateException("The default value should never be used and all nodes should be printed properly!");
  }
}
