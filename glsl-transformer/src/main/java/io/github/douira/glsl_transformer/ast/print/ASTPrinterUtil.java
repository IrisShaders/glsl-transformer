package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.*;
import io.github.douira.glsl_transformer.ast.print.token.*;
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
  public Void superNodeTypeResult() {
    return null;
  }

  @Override
  public Void defaultResult() {
    throw new IllegalStateException("The default value should never be used and all nodes should be printed properly!");
  }
}
