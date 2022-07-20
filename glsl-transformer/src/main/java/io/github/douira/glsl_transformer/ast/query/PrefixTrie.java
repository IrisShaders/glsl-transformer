package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.collections4.trie.PatriciaTrie;

public class PrefixTrie<E> extends PatriciaTrie<Set<E>> implements PrefixQueryable<E> {
  public PrefixTrie() {
  }

  public PrefixTrie(Map<? extends String, ? extends Set<E>> m) {
    super(m);
  }

  /**
   * Returns a stream of all the elements that have a given prefix.
   * 
   * @param prefix the prefix to search for
   * @return the elements that have the prefix
   */
  @Override
  public Stream<Set<E>> prefixQuery(String prefix) {
    return prefixMap(prefix).values().stream();
  }
}
