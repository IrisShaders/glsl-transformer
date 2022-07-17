package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PrefixSuffixTrie<E> extends DuplicatorTrie<E> {
  private static final int removeThreshold = 200;
  private Map<String, String> reverses = new HashMap<>();

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
  protected void iteratePermutations(String key, Consumer<String> consumer) {
    consumer.accept(key);
    consumer.accept(marker + getReverse(key));
  }

  @Override
  public E remove(Object k) {
    var previous = super.remove(k);
    if (reverses.size() >= removeThreshold) {
      // remove both the key and the reverse of the key
      reverses.remove(reverses.remove(k));
    }
    return previous;
  }

  public Stream<E> prefixQuery(String prefix) {
    return distinctPrefixQuery(sanitizeKey(prefix));
  }

  public Stream<E> suffixQuery(String suffix) {
    return distinctPrefixQuery(marker + getReverse(sanitizeKey(suffix)));
  }
}
