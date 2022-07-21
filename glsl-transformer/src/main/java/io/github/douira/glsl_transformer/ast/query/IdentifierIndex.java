package io.github.douira.glsl_transformer.ast.query;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.collections4.trie.PatriciaTrie;

import io.github.douira.glsl_transformer.ast.node.Identifier;

/**
 * Indexes identifiers based on their content and enabled fast string queries.
 */
public class IdentifierIndex<I extends PatriciaTrie<Set<Identifier>>>
    implements Index<Identifier>, PrefixQueryable<Identifier> {
  public final I index;
  private List<Identifier> nodeList;

  public IdentifierIndex(I index) {
    this.index = index;
  }

  @Override
  public void add(Identifier node) {
    var name = node.getName();
    var set = index.get(name);
    if (set == null) {
      set = new HashSet<>();
      index.put(name, set);
    }
    set.add(node);
  }

  @Override
  public void remove(Identifier node) {
    var name = node.getName();
    var set = index.get(name);
    if (set == null) {
      return;
    }
    set.remove(node);
    if (set.isEmpty()) {
      index.remove(name);
    }
  }

  public void merge(IdentifierIndex<?> other) {
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

  public Stream<Identifier> getStream(String k) {
    var result = index.get(k);
    return result == null ? Stream.empty() : result.stream();
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

  @Override
  public Stream<Set<Identifier>> prefixQuery(String key) {
    return index.prefixMap(key).values().stream();
  }

  @Override
  public Stream<Identifier> prefixQueryFlat(String key) {
    return prefixQuery(key).flatMap(Set::stream);
  }

  private void ensureNodeList() {
    if (nodeList == null) {
      nodeList = new ArrayList<>();
    } else {
      nodeList.clear();
    }
  }

  public void renameAll(String oldName, String newName) {
    ensureNodeList();
    var set = index.get(oldName);
    if (set == null) {
      return;
    }
    nodeList.addAll(set);
    for (var identifier : nodeList) {
      identifier.setName(newName);
    }
  }

  public void replaceAll(String name, Consumer<Identifier> replacer) {
    ensureNodeList();
    var set = index.get(name);
    if (set == null) {
      return;
    }
    nodeList.addAll(set);
    for (var identifier : nodeList) {
      replacer.accept(identifier);
    }
  }

  public void replaceAll(Stream<Identifier> targets, Consumer<Identifier> replacer) {
    ensureNodeList();
    if (targets == null) {
      return;
    }
    targets.forEach(nodeList::add);
    for (var identifier : nodeList) {
      replacer.accept(identifier);
    }
  }

  public static IdentifierIndex<PrefixTrie<Identifier>> withPrefix() {
    return new IdentifierIndex<>(new PrefixTrie<>());
  }

  public static IdentifierIndex<PrefixSuffixTrie<Identifier>> withPrefixSuffix() {
    return new IdentifierIndex<>(new PrefixSuffixTrie<>());
  }

  public static IdentifierIndex<PermutermTrie<Identifier>> withPermuterm() {
    return new IdentifierIndex<>(new PermutermTrie<>());
  }
}
