package io.github.douira.glsl_transformer.basic;

import java.util.function.Function;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.ast.transform.ASTBuilder;
import io.github.douira.glsl_transformer.cst.token_filter.TokenFilter;
import io.github.douira.glsl_transformer.tree.ExtendedContext;
import io.github.douira.glsl_transformer.util.LRUCache;

/**
 * The caching parser extends the enhanced parser and returns previous parse
 * trees if they are available. This can't be used if the parse tree is
 * modified after parsing since then the contents of the cache would be modified
 * too. The {@link ASTBuilder} does not modify the parse tree, and therefore it
 * is safe to use this.
 */
public class CachingParser extends EnhancedParser {
  private static final int defaultCacheSize = 400;
  private final LRUCache<CacheKey, ExtendedContext> parseCache;

  private static class CacheKey {
    final String input;
    final Class<? extends ExtendedContext> ruleType;

    public CacheKey(String input, Class<? extends ExtendedContext> ruleType) {
      this.input = input;
      this.ruleType = ruleType;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((input == null) ? 0 : input.hashCode());
      result = prime * result + ((ruleType == null) ? 0 : ruleType.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CacheKey other = (CacheKey) obj;
      if (input == null) {
        if (other.input != null)
          return false;
      } else if (!input.equals(other.input))
        return false;
      if (ruleType == null) {
        if (other.ruleType != null)
          return false;
      } else if (!ruleType.equals(other.ruleType))
        return false;
      return true;
    }
  }

  public CachingParser(boolean throwParseErrors, int cacheSize) {
    super(throwParseErrors);
    parseCache = new LRUCache<>(cacheSize);
  }

  public CachingParser(int cacheSize) {
    parseCache = new LRUCache<>(cacheSize);
  }

  public CachingParser(boolean throwParseErrors) {
    this(throwParseErrors, defaultCacheSize);
  }

  public CachingParser() {
    this(defaultCacheSize);
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
    return (RuleType) parseCache.cachedGet(new CacheKey(str, ruleType),
        () -> parse(str, parent, parseMethod));
  }

  @Override
  public void setParseTokenFilter(TokenFilter<?> parseTokenFilter) {
    super.setParseTokenFilter(parseTokenFilter);
    parseCache.clear();
  }
}
