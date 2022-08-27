package io.github.douira.glsl_transformer.util;

import java.util.*;
import java.util.function.*;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
  private final int maxSize;

  public LRUCache(int maxSize, float loadFactor) {
    super((int) Math.ceil((float) maxSize / loadFactor) + 1, loadFactor, true);
    this.maxSize = maxSize;
  }

  public LRUCache(int maxSize) {
    this(maxSize, 0.75f);
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > maxSize;
  }

  public V cachedGet(K key, Supplier<V> supplier) {
    V value = get(key);
    if (value == null) {
      value = supplier.get();
      put(key, value);
    }
    return value;
  }
}
