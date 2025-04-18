package io.github.douira.glsl_transformer.parser;

import org.antlr.v4.runtime.*;

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
  protected record CacheContents(ParserRuleContext parseTree, BufferedTokenStream tokenStream) {
  }

  protected TypedTreeCache<CacheContents> parseCache;

  public CachingParser(boolean throwParseErrors, int cacheSize) {
    super(throwParseErrors);
    parseCache = new TypedTreeCache<>(cacheSize);
  }

  public CachingParser() {
    parseCache = new TypedTreeCache<>();
  }

  public void setParseCacheSizeAndClear(int size) {
    parseCache = new TypedTreeCache<>(size);
  }

  @Override
  public <C extends ParserRuleContext> C parse(
      String str,
      ParseShape<C, ?> parseShape) {
    return parse(str, null, parseShape);
  }

  @Override
  public <C extends ParserRuleContext> C parse(
      String str,
      ParserRuleContext parent,
      ParseShape<C, ?> parseShape) {
    return parseWithCache(str, parent, parseShape, parseCache);
  }

  @SuppressWarnings("unchecked")
  protected <C extends ParserRuleContext> C parseWithCache(
      String str,
      ParserRuleContext parent,
      ParseShape<C, ?> parseShape,
      TypedTreeCache<CacheContents> cache) {
    var result = cache.cachedGet(str, parseShape.ruleType,
        () -> {
          var node = parse(str, parent, parseShape.parseMethod);
          return new CacheContents(node, getTokenStream());
        });
    if (result != null) {
      // so that when a cache hit happens, the getTokenStream method returns the
      // correct token stream
      tokenStream = result.tokenStream;
      return (C) result.parseTree;
    } else {
      return null;
    }
  }

  @Override
  public void setTokenFilter(TokenFilter<?> tokenFilter) {
    super.setTokenFilter(tokenFilter);
    parseCache.clear();
  }
}
