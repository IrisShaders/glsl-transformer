package io.github.douira.glsl_transformer.ast.data;

import java.util.Collection;
import java.util.function.*;

/**
 * A further extension of {@link ProxyArrayList} that also calls a notification
 * method whenever an element is removed. This is considerably more expensive.
 * The removal notification method should be idempotent and reversible with the
 * addition notification method.
 */
public abstract class RemovalProxyArrayList<T> extends ProxyArrayList<T> {
  public RemovalProxyArrayList() {
  }

  public RemovalProxyArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public RemovalProxyArrayList(Collection<? extends T> c) {
    super(c);
  }

  protected abstract void notifyRemoval(T removed);

  private void notifyRemovalInternal(T removed) {
    if (removed != null) {
      notifyRemoval(removed);
    }
  }

  private void notifyRemovalInternal(Collection<? extends T> collection) {
    for (var element : collection) {
      notifyRemovalInternal(element);
    }
  }

  @Override
  public T set(int index, T element) {
    var prev = super.set(index, element);
    if (prev != element) {
      notifyRemovalInternal(prev);
    }
    return prev;
  }

  @Override
  public T remove(int index) {
    var removed = super.remove(index);
    notifyRemovalInternal(removed);
    return removed;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    var result = super.remove(o);
    if (result) {
      // the cast is ok because only T-type elements could have been added
      notifyRemovalInternal((T) o);
    }
    return result;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    notifyRemovalInternal(this);
    var result = super.removeAll(c);
    notifyAdditionInternal(this);
    return result;
  }

  @Override
  public void replaceAll(UnaryOperator<T> operator) {
    notifyRemovalInternal(this);
    super.replaceAll(operator);
    // addition is notified by {@link ProxyArrayList}
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    notifyRemovalInternal(this);
    var result = super.retainAll(c);
    notifyAdditionInternal(this);
    return result;
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    notifyRemovalInternal(this);
    var result = super.removeIf(filter);
    notifyAdditionInternal(this);
    return result;
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    for (var i = fromIndex; i < toIndex; i++) {
      notifyRemovalInternal(get(i));
    }
    super.removeRange(fromIndex, toIndex);
  }

  @Override
  public void clear() {
    notifyRemovalInternal(this);
    super.clear();
  }
}