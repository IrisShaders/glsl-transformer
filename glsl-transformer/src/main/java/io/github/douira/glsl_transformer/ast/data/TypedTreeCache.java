package io.github.douira.glsl_transformer.ast.data;

import java.util.function.*;

import org.antlr.v4.runtime.ParserRuleContext;

import io.github.douira.glsl_transformer.ast.data.TypedTreeCache.CacheKey;
import io.github.douira.glsl_transformer.util.LRUCache;

public class TypedTreeCache<V> extends LRUCache<CacheKey, V> {
  private static final int DEFAULT_CACHE_SIZE = 400;

  public TypedTreeCache(int maxSize, float loadFactor) {
    super(maxSize, loadFactor);
  }

  public TypedTreeCache(int maxSize) {
    super(maxSize);
  }

  public TypedTreeCache() {
    super(DEFAULT_CACHE_SIZE);
  }

  public static class CacheKey {
    final String input;
    final Class<? extends ParserRuleContext> ruleType;

    public CacheKey(String input, Class<? extends ParserRuleContext> ruleType) {
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
        return other.ruleType == null;
      } else return ruleType.equals(other.ruleType);
    }
  }

  public V cachedGet(String str, Class<? extends ParserRuleContext> ruleType,
      Supplier<V> supplier) {
    return super.cachedGet(new CacheKey(str, ruleType), supplier);
  }

  public V cachedGetHydrateHit(String str, Class<? extends ParserRuleContext> ruleType,
      Supplier<V> supplier, Function<V, V> hydrator) {
    return super.cachedGetHydrateHit(new CacheKey(str, ruleType), supplier, hydrator);
  }
}
