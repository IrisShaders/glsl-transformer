package io.github.douira.glsl_transformer.ast;

import java.util.*;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import io.github.douira.glsl_transformer.ast.node.Identifier;

/**
 * Indexes identifiers based on their content and enabled fast string queries.
 */
public class IdentifierIndex implements Index<Identifier> {
  public final Trie<String, Set<Identifier>> index = new PatriciaTrie<>();

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
}
