package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.collections4.trie.PatriciaTrie;

public class PrefixTrie<S extends Set<E>, E> extends PatriciaTrie<S> implements PrefixQueryable<S, E> {
  public PrefixTrie() {
  }

  public PrefixTrie(Map<? extends String, ? extends S> m) {
    super(m);
  }

  /**
   * Returns a stream of all the elements that have a given prefix.
   * 
   * @param prefix the prefix to search for
   * @return the elements that have the prefix
   */
  @Override
  public Stream<S> prefixQuery(String prefix) {
    return prefixMap(prefix).values().stream();
  }
}
