package io.github.douira.glsl_transformer.ast;

import io.github.douira.glsl_transformer.ast.node.*;

public abstract class PrintASTVisitor extends ASTBaseVisitor<Void> {
  private PrintASTVisitor() {
  }

  protected abstract String generateString();

  public static String printAST(PrintASTVisitor visitor, ASTNode node) {
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

  @Override
  public Void visitIdentifier(Identifier identifier) {
    emitLiteral(identifier, identifier.name);
    return null;
  }
}
