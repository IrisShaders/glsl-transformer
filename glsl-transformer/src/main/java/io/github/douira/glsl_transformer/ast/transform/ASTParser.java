package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;
import java.util.function.*;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.*;
import io.github.douira.glsl_transformer.ast.data.TypedTreeCache;
import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.query.EmptyRoot;
import io.github.douira.glsl_transformer.basic.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

public class ASTParser implements ParserInterface {
  private static ASTParser INSTANCE;

  public static ASTParser getInternalInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ASTParser();
    }
    return INSTANCE;
  }

  private EnhancedParser parser = new CachingParser();
  private TypedTreeCache<ASTNode> buildCache = new TypedTreeCache<>();
  private ASTCacheStrategy astCacheStrategy = ASTCacheStrategy.ALL_EXCLUDING_TRANSLATION_UNIT;

  public enum ASTCacheStrategy {
    ALL,
    ALL_EXCLUDING_TRANSLATION_UNIT,
    NONE
  }

  public enum ParsingCacheStrategy {
    ALL,
    NONE
  }

  public void setBuildCacheSizeAndClear(int size) {
    buildCache = new TypedTreeCache<>(size);
  }

  public void setParseCacheSizeAndClear(int size) {
    if (parser instanceof CachingParser cachingParser) {
      cachingParser.setParseCacheSizeAndClear(size);
    }
  }

  /**
   * Sets the AST cache strategy. If set to ALL, the parser will cache all
   * generated ASTs. If set to ALL_EXCLUDING_TRANSLATION_UNIT, the parser will
   * cache all generated ASTs except for the TranslationUnit. If set to NONE, the
   * parser will cache nothing.
   * 
   * @param astCacheStrategy the AST cache strategy
   */
  public void setASTCacheStrategy(ASTCacheStrategy astCacheStrategy) {
    this.astCacheStrategy = astCacheStrategy;
  }

  /**
   * Sets the parsing cache strategy. If set to ALL, the parser will cache all
   * parsed strings. If set to NONE, the parser will cache nothing. Only
   * influences how the CST is parsed from the input and not the AST.
   * 
   * @param parsingCacheStrategy the parsing cache strategy
   */
  public void setParsingCacheStrategy(ParsingCacheStrategy parsingCacheStrategy) {
    parser = parsingCacheStrategy == ParsingCacheStrategy.ALL ? new CachingParser() : new EnhancedParser();
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
  public void setTokenFilter(TokenFilter<?> setTokenFilter) {
    parser.setTokenFilter(setTokenFilter);
  }

  @SuppressWarnings("unchecked") // consistent use of the cache results in the same type
  public <RuleType extends ParserRuleContext, ReturnType extends ASTNode> ReturnType parseNode(
      String input,
      ASTNode parentTreeMember,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    if (ruleType == TranslationUnitContext.class) {
      throw new IllegalArgumentException("Translation units may not be parsed into another node, that makes no sense.");
    }

    if (astCacheStrategy == ASTCacheStrategy.NONE) {
      return ASTBuilder.buildSubtree(parentTreeMember, parser.parse(input, ruleType, parseMethod), visitMethod);
    } else {
      // cache and possibly build, always clone to return new trees
      return (ReturnType) buildCache.cachedGet(input, ruleType,
          () -> ASTBuilder.build(new EmptyRoot(), parser.parse(input, ruleType, parseMethod), visitMethod))
          .cloneInto(parentTreeMember);
    }
  }

  @SuppressWarnings("unchecked") // consistent use of the cache results in the same type
  public <RuleType extends ParserRuleContext, ReturnType extends ASTNode> ReturnType parseNodeSeparate(
      String input,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod,
      BiFunction<ASTBuilder, RuleType, ReturnType> visitMethod) throws RecognitionException {
    if (astCacheStrategy == ASTCacheStrategy.NONE
        || astCacheStrategy == ASTCacheStrategy.ALL_EXCLUDING_TRANSLATION_UNIT
            && ruleType == TranslationUnitContext.class) {
      return ASTBuilder.build(parser.parse(input, ruleType, parseMethod), visitMethod);
    } else {
      return (ReturnType) buildCache.cachedGet(input, ruleType,
          () -> ASTBuilder.build(new EmptyRoot(), parser.parse(input, ruleType, parseMethod), visitMethod))
          .cloneSeparate();
    }
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

  public List<ExternalDeclaration> parseExternalDeclarations(ASTNode treeMember, String... inputs) {
    var nodes = new ArrayList<ExternalDeclaration>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseExternalDeclaration(treeMember, input));
    }
    return nodes;
  }

  public List<Expression> parseExpression(ASTNode treeMember, String... inputs) {
    var nodes = new ArrayList<Expression>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseExpression(treeMember, input));
    }
    return nodes;
  }

  public List<Statement> parseStatements(ASTNode treeMember, String... inputs) {
    var nodes = new ArrayList<Statement>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseStatement(treeMember, input));
    }
    return nodes;
  }
}
