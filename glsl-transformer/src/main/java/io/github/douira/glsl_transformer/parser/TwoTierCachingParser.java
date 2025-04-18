package io.github.douira.glsl_transformer.parser;

import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.ast.data.TypedTreeCache;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;
import org.antlr.v4.runtime.ParserRuleContext;

public class TwoTierCachingParser extends CachingParser {
  private static final int DEFAULT_SECONDARY_CACHE_SIZE = 10;
  
  protected TypedTreeCache<CacheContents> secondaryCache;

  public TwoTierCachingParser() {
    super();
    secondaryCache = new TypedTreeCache<>(DEFAULT_SECONDARY_CACHE_SIZE);
  }

  public TwoTierCachingParser(boolean throwParseErrors, int cacheSize, int secondaryCacheSize) {
    super(throwParseErrors, cacheSize);
    secondaryCache = new TypedTreeCache<>(secondaryCacheSize);
  }

  @Override
  public void setParseCacheSizeAndClear(int size) {
    throw new UnsupportedOperationException(
        "This parser uses a two-tier cache. Use setTwoTierCacheSizesAndClear instead.");
  }
  
  public void setTwoTierCacheSizesAndClear(int primarySize, int secondarySize) {
    parseCache = new TypedTreeCache<>(primarySize);
    secondaryCache = new TypedTreeCache<>(secondarySize);
  }

  @Override
  public <C extends ParserRuleContext> C parse(String str, ParserRuleContext parent, ParseShape<C, ?> parseShape) {
    if (parseShape.ruleType == GLSLParser.TranslationUnitContext.class) {
      return parseWithCache(str, parent, parseShape, secondaryCache);
    }
    return super.parse(str, parent, parseShape);
  }

  @Override
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    super.setTokenFilter(tokenFilter);
    secondaryCache.clear();
  }
}
