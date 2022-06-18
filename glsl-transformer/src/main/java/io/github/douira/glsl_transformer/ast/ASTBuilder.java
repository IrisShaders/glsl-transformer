package io.github.douira.glsl_transformer.ast;

import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;

public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  public static ASTNode build(ParseTree ctx) {
    return new ASTBuilder().visit(ctx);
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration()
        .stream()
        .map((declaration) -> (ExternalDeclaration) visitExternalDeclaration(declaration))
        .collect(Collectors.toList());
    return versionStatement == null
        ? new TranslationUnit(externalDeclarations)
        : new TranslationUnit(versionStatement, externalDeclarations);
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    return ctx == null ? null : VersionStatement.from(ctx);
  }

  @Override
  public ASTNode visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public ASTNode visitPragmaStatement(PragmaStatementContext ctx) {
    return PragmaStatement.from(ctx);
  }

  @Override
  public ASTNode visitTerminal(TerminalNode node) {
    var type = node.getSymbol().getType();
    if (type == GLSLLexer.IDENTIFIER) {
      return Identifier.from(node);
    }
    throw new IllegalStateException("Unhandled terminal node: " + node.getText());
  }
}
