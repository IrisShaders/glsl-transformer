package io.github.douira.glsl_transformer.parser;

import java.util.function.Function;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.data.TypedTreeCache;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

/**
 * The caching parser extends the enhanced parser and returns previous parse
 * trees if they are available. This can't be used if the parse tree is
 * modified after parsing since then the contents of the cache would be modified
 * too. The {@link ASTBuilder} does not modify the parse tree, and therefore it
 * is safe to use this.
 */
public class CachingParser extends EnhancedParser {
  private TypedTreeCache<ParserRuleContext> parseCache;

  public CachingParser(boolean throwParseErrors, int cacheSize) {
    super(throwParseErrors);
    parseCache = new TypedTreeCache<>(cacheSize);
  }

  public CachingParser(int cacheSize) {
    parseCache = new TypedTreeCache<>(cacheSize);
  }

  public CachingParser(boolean throwParseErrors) {
    super(throwParseErrors);
    parseCache = new TypedTreeCache<>();
  }

  public CachingParser() {
    parseCache = new TypedTreeCache<>();
  }

  public void setParseCacheSizeAndClear(int size) {
    parseCache = new TypedTreeCache<>(size);
  }

  @Override
  public TranslationUnitContext parse(String str) {
    return parse(str, TranslationUnitContext.class, GLSLParser::translationUnit);
  }

  @Override
  public <C extends ParserRuleContext> C parse(
      String str,
      Class<C> ruleType,
      Function<GLSLParser, C> parseMethod) {
    return parse(str, null, ruleType, parseMethod);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <C extends ParserRuleContext> C parse(
      String str,
      ParserRuleContext parent,
      Class<C> ruleType,
      Function<GLSLParser, C> parseMethod) {
    return (C) parseCache.cachedGet(str, ruleType,
        () -> parse(str, parent, parseMethod));
  }

  @Override
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    super.setTokenFilter(tokenFilter);
    parseCache.clear();
  }
}
