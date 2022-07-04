package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.collections4.trie.PatriciaTrie;

public class PermutermTrie<E> extends PatriciaTrie<E> {
  public static final char DEFAULT_MARKER = '$';
  private final char marker;

  public static final class Holder<V> {
    public V value;

    public Holder(V value) {
      this.value = value;
    }

    public V getValue() {
      return value;
    }
  }

  public PermutermTrie() {
    super();
    marker = DEFAULT_MARKER;
  }

  public PermutermTrie(Map<? extends String, ? extends E> m) {
    super(m);
    marker = DEFAULT_MARKER;
  }

  public PermutermTrie(char marker) {
    this.marker = marker;
  }

  public PermutermTrie(Map<? extends String, ? extends E> m, char marker) {
    super(m);
    this.marker = marker;
  }

  private void iteratePermutations(String key, Consumer<String> consumer) {
    var length = key.length();
    for (var i = 0; i <= length; i++) {
      consumer.accept(key.substring(i) + marker + key.substring(0, i));
    }
  }

  private String prepareKey(Object k) {
    return sanitizeKey(k.toString()) + marker;
  }

  private String sanitizeKey(String key) {
    if (key.indexOf(marker) >= 0) {
      throw new IllegalArgumentException("Key cannot contain marker");
    }
    return key;
  }

  @Override
  public boolean containsKey(Object k) {
    return super.containsKey(prepareKey(k));
  }

  @Override
  public E get(Object k) {
    return super.get(prepareKey(k));
  }

  @Override
  public SortedMap<String, E> headMap(String toKey) {
    return super.headMap(prepareKey(toKey));
  }

  @Override
  public String nextKey(String key) {
    return super.nextKey(prepareKey(key));
  }

  @Override
  public SortedMap<String, E> prefixMap(String key) {
    return super.prefixMap(prepareKey(key));
  }

  @Override
  public String previousKey(String key) {
    return super.previousKey(prepareKey(key));
  }

  @Override
  public E put(String key, E value) {
    var previous = get(key);
    iteratePermutations(key, k -> super.put(k, value));
    return previous;
  }

  @Override
  public E remove(Object k) {
    var previous = get(k);
    iteratePermutations((String) k, super::remove);
    return previous;
  }

  @Override
  public Entry<String, E> select(String key) {
    return super.select(prepareKey(key));
  }

  @Override
  public String selectKey(String key) {
    return super.selectKey(prepareKey(key));
  }

  @Override
  public SortedMap<String, E> subMap(String fromKey, String toKey) {
    return super.subMap(prepareKey(fromKey), prepareKey(toKey));
  }

  @Override
  public SortedMap<String, E> tailMap(String fromKey) {
    return super.tailMap(prepareKey(fromKey));
  }

  private Stream<E> distinctPrefixQuery(String prefix) {
    return super.prefixMap(prefix).values().stream().unordered().distinct();
  }

  /**
   * Returns a stream of all the elements that have a given prefix.
   * 
   * @param prefix the prefix to search for
   * @return the elements that have the prefix
   */
  public Stream<E> prefixQuery(String prefix) {
    return distinctPrefixQuery(marker + sanitizeKey(prefix));
  }

  /**
   * Returns a stream of all the elements that have a given suffix.
   * 
   * @param suffix the suffix to search for
   * @return the elements that have the suffix
   */
  public Stream<E> suffixQuery(String suffix) {
    return distinctPrefixQuery(sanitizeKey(suffix) + marker);
  }

  /**
   * Returns a stream of all the elements that have a given infix (substring).
   * 
   * @param infix the infix to search for
   * @return the elements that have the infix
   */
  public Stream<E> infixQuery(String infix) {
    return distinctPrefixQuery(sanitizeKey(infix));
  }

  /**
   * Returns a stream of all the elements that have a given prefix and suffix.
   * 
   * @param prefix the prefix to search require
   * @param suffix the suffix to search require
   * @return the elements that have the prefix and suffix
   */
  public Stream<E> suffixPrefixQuery(String prefix, String suffix) {
    return distinctPrefixQuery(sanitizeKey(suffix) + marker + sanitizeKey(prefix));
  }
}
