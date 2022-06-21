package io.github.douira.glsl_transformer.ast;

import org.antlr.v4.runtime.tree.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.expression.*;
import io.github.douira.glsl_transformer.ast.node.external_declaration.*;
import io.github.douira.glsl_transformer.ast.node.statement.*;

public class ASTBuilder extends GLSLParserBaseVisitor<ASTNode> {
  public static ASTNode build(ParseTree ctx) {
    return new ASTBuilder().visit(ctx);
  }

  @Override
  public TranslationUnit visitTranslationUnit(TranslationUnitContext ctx) {
    var versionStatement = visitVersionStatement(ctx.versionStatement());
    var externalDeclarations = ctx.externalDeclaration().stream().map(
        (declaration) -> (ExternalDeclaration) visitExternalDeclaration(declaration));
    return versionStatement == null
        ? new TranslationUnit(externalDeclarations)
        : new TranslationUnit(versionStatement, externalDeclarations);
  }

  @Override
  public VersionStatement visitVersionStatement(VersionStatementContext ctx) {
    return ctx == null ? null : VersionStatement.from(ctx);
  }

  @Override
  public EmptyDeclaration visitEmptyDeclaration(EmptyDeclarationContext ctx) {
    return new EmptyDeclaration();
  }

  @Override
  public PragmaStatement visitPragmaStatement(PragmaStatementContext ctx) {
    return PragmaStatement.from(ctx);
  }

  @Override
  public ExtensionStatement visitExtensionStatement(ExtensionStatementContext ctx) {
    return ExtensionStatement.from(ctx);
  }

  @Override
  public LayoutDefaults visitLayoutDefaults(LayoutDefaultsContext ctx) {
    return LayoutDefaults.from(visitLayoutQualifier(ctx.layoutQualifier()), ctx);
  }

  @Override
  public ConditionExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
    return new ConditionExpression(
        (Expression) visit(ctx.condition),
        (Expression) visit(ctx.trueAlternative),
        (Expression) visit(ctx.falseAlternative));
  }

  @Override
  public InnerASTNode visitLayoutQualifier(LayoutQualifierContext ctx) {
    // TODO: LayoutQualifier
    return (InnerASTNode) super.visitLayoutQualifier(ctx);
  }

  @Override
  public CompoundStatement visitCompoundStatement(CompoundStatementContext ctx) {
    return new CompoundStatement(ctx.statement().stream().map(
        (statement) -> (Statement) visitStatement(statement)));
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
