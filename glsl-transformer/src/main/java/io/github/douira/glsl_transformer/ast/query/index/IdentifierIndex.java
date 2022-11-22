package io.github.douira.glsl_transformer.ast.query.index;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.github.douira.glsl_transformer.ast.node.Identifier;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.node.expression.ReferenceExpression;

/**
 * Indexes identifiers based on their content and enabled fast string queries.
 */
public class IdentifierIndex<S extends Set<Identifier>, I extends Map<String, S>> implements Index<Identifier> {
  public final I index;
  public final Supplier<S> setFactory;

  public IdentifierIndex(I index, Supplier<S> setFactory) {
    this.index = index;
    this.setFactory = setFactory;
  }

  @Override
  public void add(Identifier node) {
    var name = node.getName();
    var set = index.get(name);
    if (set == null) {
      set = setFactory.get();
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

  public Set<Identifier> get(String key) {
    var result = index.get(key);
    return result == null ? Collections.emptySet() : result;
  }

  public Stream<Identifier> getStream(String key) {
    var result = index.get(key);
    return result == null ? Stream.empty() : result.stream();
  }

  public <T extends ASTNode> Stream<T> getAncestors(String key, Class<T> ancestorType) {
    return getStream(key)
        .map(id -> id.getAncestor(ancestorType))
        .filter(Objects::nonNull);
  }

  public Stream<ReferenceExpression> getReferenceExpressions(String key) {
    return getStream(key)
        .map(id -> id.getAncestor(ReferenceExpression.class))
        .filter(Objects::nonNull);
  }

  public ReferenceExpression getOneReferenceExpression(String key) {
    return getReferenceExpressions(key).findFirst().orElse(null);
  }

  public Identifier getOne(String key) {
    var iterator = index.get(key).iterator();
    return iterator.hasNext() ? iterator.next() : null;
  }

  public boolean has(String key) {
    var result = index.get(key);
    return result != null && !result.isEmpty();
  }

  /**
   * Renames all identifiers with one name to have a new name. Since this deals
   * only with identifiers, this can be done by moving the set of identifiers
   * around as a whole.
   * 
   * @param oldName the old name
   * @param newName the new name
   * @return Whether anything was renamed
   */
  @SuppressWarnings("deprecation")
  public boolean rename(String oldName, String newName) {
    if (oldName.equals(newName)) {
      return false;
    }
    Identifier.validateContents(newName);
    var set = index.get(oldName);
    if (set == null) {
      return false;
    }
    index.remove(oldName);
    var existing = index.get(newName);
    if (existing == null) {
      index.put(newName, set);
    } else {
      existing.addAll(set);
    }
    for (var id : set) {
      id.setNameInternal(newName);
    }
    return true;
  }

  public static IdentifierIndex<HashSet<Identifier>, HashMap<String, HashSet<Identifier>>> withOnlyExact() {
    return new IdentifierIndex<>(new HashMap<>(), HashSet::new);
  }

  public static <R extends Set<Identifier>> IdentifierIndex<R, HashMap<String, R>> withOnlyExact(
      Supplier<R> setFactory) {
    return new IdentifierIndex<>(new HashMap<>(), setFactory);
  }
}
