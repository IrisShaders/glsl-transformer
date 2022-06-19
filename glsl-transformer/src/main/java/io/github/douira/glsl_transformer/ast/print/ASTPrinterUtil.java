package io.github.douira.glsl_transformer.ast.print;

import io.github.douira.glsl_transformer.ast.ASTNode;
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

  protected void emitTokens(PrintToken... tokens) {
    for (PrintToken t : tokens) {
      emitToken(t);
    }
  }

  protected void emitLiteral(ASTNode node, TokenRole role, String literal) {
    emitToken(new LiteralToken(node, role, literal));
  }

  protected void emitLiteral(ASTNode node, String literal) {
    emitLiteral(node, TokenRole.DEFAULT, literal);
  }

  protected void emitLiterals(ASTNode node, TokenRole role, String... literals) {
    for (String l : literals) {
      emitLiteral(node, role, l);
    }
  }

  protected void emitLiterals(ASTNode node, String... literals) {
    emitLiterals(node, TokenRole.DEFAULT, literals);
  }

  protected void emitType(ASTNode node, TokenRole role, int type) {
    emitToken(new ParserToken(node, role, type));
  }

  protected void emitType(ASTNode node, int type) {
    emitType(node, TokenRole.DEFAULT, type);
  }

  protected void emitType(ASTNode node, TokenRole role, int... types) {
    for (int t : types) {
      emitType(node, role, t);
    }
  }

  protected void emitType(ASTNode node, int... types) {
    emitType(node, TokenRole.DEFAULT, types);
  }

  protected void emitWhitespace(ASTNode node, TokenRole role, String whitespace) {
    emitToken(new LiteralToken(node, TokenChannel.WHITESPACE, role, whitespace));
  }

  protected void emitExactWhitespace(ASTNode node, String whitespace) {
    emitWhitespace(node, TokenRole.EXACT, whitespace);
  }

  private void emitSpace(ASTNode node, TokenRole role) {
    emitWhitespace(node, role, " ");
  }

  protected void emitExactSpace(ASTNode node) {
    emitSpace(node, TokenRole.EXACT);
  }

  protected void emitExtendableSpace(ASTNode node) {
    emitSpace(node, TokenRole.EXTENDABLE_SPACE);
  }

  protected void emitBreakableSpace(ASTNode node) {
    emitSpace(node, TokenRole.BREAKABLE_SPACE);
  }

  private void emitNewline(ASTNode node, TokenRole role) {
    emitWhitespace(node, role, "\n");
  }

  protected void emitExactNewline(ASTNode node) {
    emitNewline(node, TokenRole.EXACT);
  }

  protected void emitCommonNewline(ASTNode node) {
    emitNewline(node, TokenRole.COMMON_FORMATTING);
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
