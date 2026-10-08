package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This prefix-suffix trie supports both prefix and suffix queries but no
 * infix-related queries and works by inserting the key and its reverse into the
 * underlying trie. This is more efficient than the permuterm trie since the
 * number of bits used to index an entry is not quadratic in the size of the key
 * but linear.
 */
public class PrefixSuffixTrie<S extends Set<E>, E> extends DuplicatorTrie<S> implements PrefixQueryable<S, E>, SuffixQueryable<S, E> {
  private static final int removeThreshold = 200;
  private Map<String, String> reverses = new HashMap<>();

  public PrefixSuffixTrie() {
  }

  public PrefixSuffixTrie(Map<? extends String, ? extends S> m) {
    super(m);
  }

  public PrefixSuffixTrie(char marker) {
    super(marker);
  }

  public PrefixSuffixTrie(Map<? extends String, ? extends S> m, char marker) {
    super(m, marker);
  }

  private String getReverse(String key) {
    if (reverses.containsKey(key)) {
      return reverses.get(key);
    }
    String reverse = new StringBuilder(key).reverse().toString();
    reverses.put(key, reverse);
    reverses.put(reverse, key);
    return reverse;
  }

  @Override
  protected void iterateKeyVariations(String key, Consumer<String> consumer) {
    consumer.accept(key);
    consumer.accept(marker + getReverse(key));
  }

  @Override
  public S remove(Object k) {
    var previous = super.remove(k);
    if (reverses.size() >= removeThreshold) {
      // remove both the key and the reverse of the key
      reverses.remove(reverses.remove(k));
    }
    return previous;
  }

  @Override
  public Stream<S> prefixQuery(String prefix) {
    return distinctPrefixQuery(sanitizeKey(prefix));
  }

  @Override
  public Stream<S> suffixQuery(String suffix) {
    return distinctPrefixQuery(marker + getReverse(sanitizeKey(suffix)));
  }
}
