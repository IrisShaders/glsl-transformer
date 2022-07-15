package io.github.douira.glsl_transformer.ast.data;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * An array list extension that calls a notification method whenever an element
 * is added. The addition notification method should be idempotent and
 * reversible with the removal notification method.
 */
public abstract class ProxyArrayList<T> extends ArrayList<T> {
  public ProxyArrayList() {
  }

  public ProxyArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public ProxyArrayList(Collection<? extends T> c) {
    this(c, true);
  }

  public ProxyArrayList(Collection<? extends T> c, boolean doNotification) {
    super(c);
    if (doNotification) {
      notifyAdditionInternal(c);
    }
  }

  protected abstract void notifyAddition(T added);

  void notifyAdditionInternal(T added) {
    if (added != null) {
      notifyAddition(added);
    }
  }

  void notifyAdditionInternal(Collection<? extends T> collection) {
    for (var element : collection) {
      notifyAdditionInternal(element);
    }
  }

  @Override
  public boolean add(T e) {
    var result = super.add(e);
    notifyAdditionInternal(e);
    return result;
  }

  @Override
  public void add(int index, T element) {
    super.add(index, element);
    notifyAdditionInternal(element);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    var result = super.addAll(c);
    if (result) {
      notifyAdditionInternal(c);
    }
    return result;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    var result = super.addAll(index, c);
    if (result) {
      notifyAdditionInternal(c);
    }
    return result;
  }

  @Override
  public T set(int index, T element) {
    var prev = super.set(index, element);
    if (prev != element) {
      notifyAdditionInternal(element);
    }
    return prev;
  }

  @Override
  public void replaceAll(UnaryOperator<T> operator) {
    super.replaceAll(operator);
    notifyAdditionInternal(this);
  }
}
