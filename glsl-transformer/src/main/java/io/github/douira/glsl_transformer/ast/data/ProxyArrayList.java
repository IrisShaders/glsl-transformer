package io.github.douira.glsl_transformer.ast.data;

import java.util.*;
import java.util.function.*;

/**
 * An array list extension that calls a notification method whenever an element
 * is added or removed from the list. The notification methods should be
 * idempotent and reversible with the inverse other method.
 */
public abstract class ProxyArrayList<V> extends ArrayList<V> {
  private Set<V> elements = null;

  public ProxyArrayList() {
  }

  public ProxyArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public ProxyArrayList(Collection<? extends V> c) {
    this(c, true);
  }

  public ProxyArrayList(Collection<? extends V> c, boolean notifyInitial) {
    super(c);
    if (notifyInitial) {
      notifyAdditionSafe(c);
    }
  }

  protected abstract void notifyAddition(V added);

  protected abstract void notifyRemoval(V removed);

  void notifyAdditionSafe(V added) {
    if (added != null) {
      notifyAddition(added);
      if (elements != null) {
        elements.add(added);
      }
    }
  }

  void notifyAdditionSafe(Collection<? extends V> collection) {
    for (V element : collection) {
      notifyAdditionSafe(element);
    }
  }

  private void notifyRemovalSafe(V removed) {
    if (removed != null) {
      notifyRemoval(removed);
      if (elements != null) {
        elements.remove(removed);
      }
    }
  }

  private void notifyRemovalSafe(Collection<? extends V> collection) {
    for (V element : collection) {
      notifyRemovalSafe(element);
    }
  }

  private Set<V> getElements() {
    if (elements == null) {
      elements = new HashSet<>(this);
    }
    return elements;
  }

  @Override
  public V set(int index, V element) {
    V prev = super.set(index, element);
    if (prev != element) {
      notifyRemovalSafe(prev);
      notifyAdditionSafe(element);
    }
    return prev;
  }

  @Override
  public boolean add(V e) {
    var result = super.add(e);
    notifyAdditionSafe(e);
    return result;
  }

  @Override
  public void add(int index, V element) {
    super.add(index, element);
    notifyAdditionSafe(element);
  }

  @Override
  public boolean addAll(Collection<? extends V> c) {
    var result = super.addAll(c);
    if (result) {
      notifyAdditionSafe(c);
    }
    return result;
  }

  @Override
  public boolean addAll(int index, Collection<? extends V> c) {
    var result = super.addAll(index, c);
    if (result) {
      notifyAdditionSafe(c);
    }
    return result;
  }

  @Override
  public V remove(int index) {
    V removed = super.remove(index);
    notifyRemovalSafe(removed);
    return removed;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    var result = super.remove(o);
    if (result) {
      // the cast is ok because only T-type elements could have been added
      notifyRemovalSafe((V) o);
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll(Collection<?> c) {
    var result = super.removeAll(c);
    if (result) {
      for (Object element : this) {
        if (getElements().contains(element)) {
          notifyRemovalSafe((V) element);
        }
      }
    }
    return result;
  }

  @Override
  public void replaceAll(UnaryOperator<V> operator) {
    for (int i = 0; i < size(); i++) {
      set(i, operator.apply(get(i)));
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean retainAll(Collection<?> c) {
    var result = super.retainAll(c);
    if (result) {
      for (Object element : this) {
        if (!getElements().contains(element)) {
          notifyRemovalSafe((V) element);
        }
      }
    }
    return result;
  }

  @Override
  public boolean removeIf(Predicate<? super V> filter) {
    var result = super.removeIf(filter);
    if (result) {
      for (V element : this) {
        if (filter.test(element)) {
          notifyRemovalSafe(element);
        }
      }
    }
    return result;
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    for (var i = fromIndex; i < toIndex; i++) {
      notifyRemovalSafe(get(i));
    }
    super.removeRange(fromIndex, toIndex);
  }

  @Override
  public void clear() {
    if (elements != null) {
      elements.clear();
    }
    notifyRemovalSafe(this);
    super.clear();
  }

  @Override
  @SuppressWarnings("unchecked")
  public ProxyArrayList<V> clone() {
    var result = (ProxyArrayList<V>) super.clone();
    result.elements = null;
    return result;
  }
}
