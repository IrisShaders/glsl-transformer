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
  public ASTNode visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
    return new FunctionCallExpression(
        (InnerASTNode) visit(ctx.functionCall())); // TODO: FunctionCall
  }

  @Override
  public ASTNode visitGroupingExpression(GroupingExpressionContext ctx) {
    return new GroupingExpression((Expression) visit(ctx.value));
  }

  @Override
  public ASTNode visitMemberAccessExpression(MemberAccessExpressionContext ctx) {
    return new MemberAccessExpression(
        (Expression) visit(ctx.operand),
        ctx.member.getText());
  }

  @Override
  public ASTNode visitMethodCallExpression(MethodCallExpressionContext ctx) {
    return new MethodCallExpression(
        (Expression) visit(ctx.operand),
        (InnerASTNode) visit(ctx.methodCall())); // TODO: MethodCall
  }

  @Override
  public ASTNode visitPostfixExpression(PostfixExpressionContext ctx) {
    var operand = (Expression) visit(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLParser.INC_OP:
        return new IncrementPostfixExpression(operand);
      case GLSLParser.DEC_OP:
        return new DecrementPostfixExpression(operand);
      default:
        throw new IllegalArgumentException("Unknown postfix operator: " + ctx.op.getText());
    }
  }

  @Override
  public ASTNode visitPrefixExpression(PrefixExpressionContext ctx) {
    var operand = (Expression) visit(ctx.operand);
    switch (ctx.op.getType()) {
      case GLSLLexer.INC_OP:
        return new IncrementPrefixExpression(operand);
      case GLSLLexer.DEC_OP:
        return new DecrementPrefixExpression(operand);
      case GLSLLexer.PLUS_OP:
        return new IdentityExpression(operand);
      case GLSLLexer.MINUS_OP:
        return new NegationExpression(operand);
      case GLSLLexer.NOT_OP:
        return new BooleanNotExpression(operand);
      case GLSLLexer.BNEG_OP:
        return new BitwiseNotExpression(operand);
      default:
        throw new IllegalStateException("Unexpected prefix operator type" + ctx.op.getText());
    }
  }

  @Override
  public InnerASTNode visitLayoutQualifier(LayoutQualifierContext ctx) {
    return (InnerASTNode) super.visitLayoutQualifier(ctx); // TODO: LayoutQualifier
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
