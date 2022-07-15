package io.github.douira.glsl_transformer.ast.query;

import java.util.stream.Stream;

import org.apache.commons.collections4.trie.PatriciaTrie;

public class PrefixTrie<E> extends PatriciaTrie<E> {
  /**
   * Returns a stream of all the elements that have a given prefix.
   * 
   * @param prefix the prefix to search for
   * @return the elements that have the prefix
   */
  public Stream<E> prefixQuery(String prefix) {
    return prefixMap(prefix).values().stream();
  }
}
