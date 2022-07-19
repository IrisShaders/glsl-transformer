package io.github.douira.glsl_transformer.ast.data;

import java.util.*;
import java.util.function.*;

/**
 * An array list extension that calls a notification method whenever an element
 * is added or removed from the list. The notification methods should be
 * idempotent and reversible with the inverse other method.
 */
public abstract class ProxyArrayList<T> extends ArrayList<T> {
  private Set<T> elements = null;

  public ProxyArrayList() {
  }

  public ProxyArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public ProxyArrayList(Collection<? extends T> c) {
    this(c, true);
  }

  public ProxyArrayList(Collection<? extends T> c, boolean notifyInitial) {
    super(c);
    if (notifyInitial) {
      notifyAdditionSafe(c);
    }
  }

  protected abstract void notifyAddition(T added);

  protected abstract void notifyRemoval(T removed);

  void notifyAdditionSafe(T added) {
    if (added != null) {
      notifyAddition(added);
      if (elements != null) {
        elements.add(added);
      }
    }
  }

  void notifyAdditionSafe(Collection<? extends T> collection) {
    for (var element : collection) {
      notifyAdditionSafe(element);
    }
  }

  private void notifyRemovalSafe(T removed) {
    if (removed != null) {
      notifyRemoval(removed);
      if (elements != null) {
        elements.remove(removed);
      }
    }
  }

  private void notifyRemovalSafe(Collection<? extends T> collection) {
    for (var element : collection) {
      notifyRemovalSafe(element);
    }
  }

  private Set<T> getElements() {
    if (elements == null) {
      elements = new HashSet<>(this);
    }
    return elements;
  }

  @Override
  public T set(int index, T element) {
    var prev = super.set(index, element);
    if (prev != element) {
      notifyRemovalSafe(prev);
      notifyAdditionSafe(element);
    }
    return prev;
  }

  @Override
  public boolean add(T e) {
    var result = super.add(e);
    notifyAdditionSafe(e);
    return result;
  }

  @Override
  public void add(int index, T element) {
    super.add(index, element);
    notifyAdditionSafe(element);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    var result = super.addAll(c);
    if (result) {
      notifyAdditionSafe(c);
    }
    return result;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    var result = super.addAll(index, c);
    if (result) {
      notifyAdditionSafe(c);
    }
    return result;
  }

  @Override
  public T remove(int index) {
    var removed = super.remove(index);
    notifyRemovalSafe(removed);
    return removed;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    var result = super.remove(o);
    if (result) {
      // the cast is ok because only T-type elements could have been added
      notifyRemovalSafe((T) o);
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
          notifyRemovalSafe((T) element);
        }
      }
    }
    return result;
  }

  @Override
  public void replaceAll(UnaryOperator<T> operator) {
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
          notifyRemovalSafe((T) element);
        }
      }
    }
    return result;
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    var result = super.removeIf(filter);
    if (result) {
      for (var element : this) {
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
}
