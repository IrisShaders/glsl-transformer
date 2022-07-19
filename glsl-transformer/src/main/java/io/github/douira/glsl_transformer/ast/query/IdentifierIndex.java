package io.github.douira.glsl_transformer.ast.query;

import java.util.*;

import org.apache.commons.collections4.trie.PatriciaTrie;

import io.github.douira.glsl_transformer.ast.node.Identifier;

/**
 * Indexes identifiers based on their content and enabled fast string queries.
 */
public class IdentifierIndex implements Index<Identifier> {
  public final PatriciaTrie<Set<Identifier>> index;

  public IdentifierIndex(PatriciaTrie<Set<Identifier>> index) {
    this.index = index;
  }

  public IdentifierIndex() {
    this(new PatriciaTrie<Set<Identifier>>());
  }

  @Override
  public void add(Identifier node) {
    var set = index.get(node.name);
    if (set == null) {
      set = new HashSet<>();
      index.put(node.name, set);
    }
    set.add(node);
  }

  @Override
  public void remove(Identifier node) {
    var set = index.get(node.name);
    if (set == null) {
      return;
    }
    set.remove(node);
    if (set.isEmpty()) {
      index.remove(node.name);
    }
  }

  public void merge(IdentifierIndex other) {
    for (var entry : other.index.entrySet()) {
      var set = index.get(entry.getKey());
      if (set == null) {
        set = new HashSet<>();
        index.put(entry.getKey(), set);
      }
      set.addAll(entry.getValue());
    }
  }

  public Set<Identifier> get(String k) {
    var result = index.get(k);
    return result == null ? Collections.emptySet() : result;
  }

  public Identifier getOne(String k) {
    return index.get(k).iterator().next();
  }

  public boolean has(String k) {
    var result = index.get(k);
    return result != null && !result.isEmpty();
  }

  public SortedMap<String, Set<Identifier>> prefixMap(String key) {
    return index.prefixMap(key);
  }
}
