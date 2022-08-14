package io.github.douira.glsl_transformer.ast.transform;

import java.util.function.*;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.cst.token_filter.TokenFilter;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

public class ASTParser implements ParserInterface {
  private final CachingParser parser;

  public ASTParser() {
    parser = new CachingParser();
  }

  @Override
  public GLSLLexer getLexer() {
    return parser.getLexer();
  }

  @Override
  public GLSLParser getParser() {
    return parser.getParser();
  }

  @Override
  public void setThrowParseErrors(boolean throwParseErrors) {
    parser.setThrowParseErrors(throwParseErrors);
  }

  @Override
  public void setParsingStrategy(ParsingStrategy parsingStrategy) {
    parser.setParsingStrategy(parsingStrategy);
  }

  @Override
  public void setSLLOnly() {
    parser.setSLLOnly();
  }

  @Override
  public void setLLOnly() {
    parser.setLLOnly();
  }

  @Override
  public void setParseTokenFilter(TokenFilter<?> parseTokenFilter) {
    parser.setParseTokenFilter(parseTokenFilter);
  }

  @Override
  public TokenFilter<?> getParseTokenFilter() {
    return parser.getParseTokenFilter();
  }

  public <RuleType extends ExtendedContext, ReturnType extends ASTNode> ReturnType parseNode(
      String input,
      ASTNode parentTreeMember,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    return ASTBuilder.buildSubtree(parentTreeMember, parser.parse(input, ruleType, parseMethod), visitMethod);
  }

  public <RuleType extends ExtendedContext, ReturnType extends ASTNode> ReturnType parseNodeSeparate(
      String input,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    return ASTBuilder.build(parser.parse(input, ruleType, parseMethod), visitMethod);
  }

  public TranslationUnit parseTranslationUnit(String input) throws RecognitionException {
    return parseNodeSeparate(input,
        TranslationUnitContext.class,
        GLSLParser::translationUnit,
        ASTBuilder::visitTranslationUnit);
  }

  public ExternalDeclaration parseExternalDeclaration(ASTNode treeMember, String input)
      throws RecognitionException {
    return parseNode(input, treeMember,
        ExternalDeclarationContext.class,
        GLSLParser::externalDeclaration,
        ASTBuilder::visitExternalDeclaration);
  }

  public Expression parseExpression(ASTNode treeMember, String input) throws RecognitionException {
    return parseNode(input, treeMember,
        ExpressionContext.class,
        GLSLParser::expression,
        ASTBuilder::visitExpression);
  }

  public Statement parseStatement(ASTNode treeMember, String input) throws RecognitionException {
    return parseNode(input, treeMember,
        StatementContext.class,
        GLSLParser::statement,
        ASTBuilder::visitStatement);
  }

  public ExternalDeclaration parseSeparateExternalDeclaration(String input) throws RecognitionException {
    return parseNodeSeparate(input,
        ExternalDeclarationContext.class,
        GLSLParser::externalDeclaration,
        ASTBuilder::visitExternalDeclaration);
  }

  public Expression parseSeparateExpression(String input) throws RecognitionException {
    return parseNodeSeparate(input,
        ExpressionContext.class,
        GLSLParser::expression,
        ASTBuilder::visitExpression);
  }

  public Statement parseSeparateStatement(String input) throws RecognitionException {
    return parseNodeSeparate(input,
        StatementContext.class,
        GLSLParser::statement,
        ASTBuilder::visitStatement);
  }
}
