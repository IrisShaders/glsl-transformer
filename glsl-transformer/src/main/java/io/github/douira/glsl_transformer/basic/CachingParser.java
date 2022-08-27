package io.github.douira.glsl_transformer.basic;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.data.TypedTreeCache;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.cst.token_filter.TokenFilter;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The caching parser extends the enhanced parser and returns previous parse
 * trees if they are available. This can't be used if the parse tree is
 * modified after parsing since then the contents of the cache would be modified
 * too. The {@link ASTBuilder} does not modify the parse tree, and therefore it
 * is safe to use this.
 */
public class CachingParser extends EnhancedParser {
  private TypedTreeCache<ExtendedContext> parseCache;

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

  public <RuleType extends ExtendedContext> RuleType parse(
      String str,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod) {
    return parse(str, null, ruleType, parseMethod);
  }

  @SuppressWarnings("unchecked")
  public <RuleType extends ExtendedContext> RuleType parse(
      String str,
      ExtendedContext parent,
      Class<RuleType> ruleType,
      Function<GLSLParser, RuleType> parseMethod) {
    return (RuleType) parseCache.cachedGet(str, ruleType,
        () -> parse(str, parent, parseMethod));
  }

  @Override
  public void setParseTokenFilter(TokenFilter<?> parseTokenFilter) {
    super.setParseTokenFilter(parseTokenFilter);
    parseCache.clear();
  }
}
