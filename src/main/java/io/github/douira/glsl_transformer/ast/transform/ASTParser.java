package io.github.douira.glsl_transformer.ast.transform;

import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.data.TypedTreeCache;
import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.Expression;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExternalDeclaration;
import io.github.douira.glsl_transformer.ast.node.statement.Statement;
import io.github.douira.glsl_transformer.ast.query.*;
import io.github.douira.glsl_transformer.parser.*;
import io.github.douira.glsl_transformer.parser.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

public class ASTParser implements ParserInterface {
  private static ASTParser INSTANCE;

  public static ASTParser _getInternalInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ASTParser();
    }
    return INSTANCE;
  }

  private EnhancedParser parser;
  private TypedTreeCache<ASTNode> buildCache;
  private ASTCacheStrategy astCacheStrategy = ASTCacheStrategy.ALL_EXCLUDING_TRANSLATION_UNIT;
  private boolean parseLineDirectives = false;

  public enum ASTCacheStrategy {
    ALL,
    ALL_EXCLUDING_TRANSLATION_UNIT,
    NONE
  }

  public enum ParsingCacheStrategy {
    ALL,
    TWO_TIER,
    ALL_EXCLUDING_TRANSLATION_UNIT,
    NONE
  }

  public ASTParser(EnhancedParser parser, TypedTreeCache<ASTNode> buildCache) {
    this.parser = parser;
    this.buildCache = buildCache;
  }

  public ASTParser() {
    this(new CachingParser(), new TypedTreeCache<>());
  }

  public void setParser(EnhancedParser parser) {
    this.parser = parser;
  }

  public void setBuildCache(TypedTreeCache<ASTNode> buildCache) {
    this.buildCache = buildCache;
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
   * Sets the parsing cache strategy. If set to {@link ParsingCacheStrategy#ALL},
   * the parser will cache all parsed strings. If set to
   * {@link ParsingCacheStrategy#TWO_TIER}, the parser will cache all parsed strings
   * in a two-tier cache. If set to {@link ParsingCacheStrategy#NONE},
   * the parser will cache nothing. If set to
   * {@link ParsingCacheStrategy#ALL_EXCLUDING_TRANSLATION_UNIT}, the parser
   * will cache all parsed strings except for the {@link TranslationUnit}.
   * This only influences how the CST is parsed from the input and not the AST.
   *
   * @param parsingCacheStrategy the parsing cache strategy
   */
  public void setParsingCacheStrategy(ParsingCacheStrategy parsingCacheStrategy) {
    parser = switch (parsingCacheStrategy) {
      case ALL -> new CachingParser();
      case TWO_TIER -> new TwoTierCachingParser();
      case ALL_EXCLUDING_TRANSLATION_UNIT -> new TranslationUnitFilterCachingParser();
      case NONE -> new EnhancedParser();
    };
  }

  /**
   * Sets whether the AST parser should handle line directives. If set to true,
   * the parser will parse line directives and add them to the AST. If set to
   * false, the parser will ignore line directives and not add them to the AST.
   *
   * @param parseLineDirectives whether the parser should parse line directives
   */
  public void setParseLineDirectives(boolean parseLineDirectives) {
    this.parseLineDirectives = parseLineDirectives;
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

  private void setBuilderTokenStream() {
    if (parseLineDirectives) {
      ASTBuilder.setTokenStream(parser.getTokenStream());
    }
  }

  private void unsetBuilderTokenStream() {
    if (parseLineDirectives) {
      ASTBuilder.unsetTokenStream();
    }
  }

  private class EmptyRoot extends Root {
    public EmptyRoot() {
      super(null, null, null);
    }

    @Override
    public void registerIdentifierRename(Identifier identifier) {
    }

    @Override
    public void registerNode(ASTNode node, boolean isSubtreeRoot) {
    }

    @Override
    public void unregisterIdentifierRename(Identifier identifier) {
    }

    @Override
    public void unregisterNode(ASTNode node, boolean isSubtreeRoot) {
    }
  }

  @SuppressWarnings("unchecked")
  private <C extends ParserRuleContext, N extends ASTNode> N parseNodeCachedUncloned(
      String input, ParseShape<C, N> parseShape) {
    return (N) buildCache.cachedGet(input,
        parseShape.ruleType,
        () -> {
          try {
            var parsed = parser.parse(input, parseShape);
            setBuilderTokenStream();
            return ASTBuilder.build(new EmptyRoot(), parsed, parseShape.visitMethod);
          } finally {
            unsetBuilderTokenStream();
          }
        });
  }

  @SuppressWarnings("unchecked") // consistent use of the cache results in the same type
  public <C extends ParserRuleContext, N extends ASTNode> N parseNode(
      Root rootInstance,
      ParseShape<C, N> parseShape,
      String input) {
    if (astCacheStrategy == ASTCacheStrategy.NONE
        || astCacheStrategy == ASTCacheStrategy.ALL_EXCLUDING_TRANSLATION_UNIT
        && parseShape.ruleType == TranslationUnitContext.class) {
      try {
        var parsed = parser.parse(input, parseShape);
        setBuilderTokenStream();
        return ASTBuilder.buildSubtree(rootInstance, parsed, parseShape.visitMethod);
      } finally {
        unsetBuilderTokenStream();
      }
    } else {
      // cache and possibly build, always clone to return new trees
      return (N) parseNodeCachedUncloned(input, parseShape).cloneInto(rootInstance);
    }
  }

  public <C extends ParserRuleContext, N extends ASTNode> N parseNodeSeparate(
      RootSupplier rootSupplier,
      ParseShape<C, N> parseShape,
      String input) {
    return parseNode(rootSupplier.get(), parseShape, input);
  }

  public TranslationUnit parseTranslationUnit(Root rootInstance, String input) {
    return parseNode(rootInstance, ParseShape.TRANSLATION_UNIT, input);
  }

  public ExternalDeclaration parseExternalDeclaration(Root rootInstance, String input) {
    return parseNode(rootInstance, ParseShape.EXTERNAL_DECLARATION, input);
  }

  public Expression parseExpression(Root rootInstance, String input) {
    return parseNode(rootInstance, ParseShape.EXPRESSION, input);
  }

  public Statement parseStatement(Root rootInstance, String input) {
    return parseNode(rootInstance, ParseShape.STATEMENT, input);
  }

  public TranslationUnit parseTranslationUnit(RootSupplier rootSupplier, String input) {
    return parseTranslationUnit(rootSupplier.get(), input);
  }

  public ExternalDeclaration parseExternalDeclaration(RootSupplier rootSupplier, String input) {
    return parseExternalDeclaration(rootSupplier.get(), input);
  }

  public Expression parseExpression(RootSupplier rootSupplier, String input) {
    return parseExpression(rootSupplier.get(), input);
  }

  public Statement parseStatement(RootSupplier rootSupplier, String input) {
    return parseStatement(rootSupplier.get(), input);
  }

  public List<ExternalDeclaration> parseExternalDeclarations(Root rootInstance, String... inputs) {
    var nodes = new ArrayList<ExternalDeclaration>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseExternalDeclaration(rootInstance, input));
    }
    return nodes;
  }

  public List<Expression> parseExpression(Root rootInstance, String... inputs) {
    var nodes = new ArrayList<Expression>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseExpression(rootInstance, input));
    }
    return nodes;
  }

  public List<Statement> parseStatements(Root rootInstance, String... inputs) {
    var nodes = new ArrayList<Statement>(inputs.length);
    for (var input : inputs) {
      nodes.add(parseStatement(rootInstance, input));
    }
    return nodes;
  }
}
