package io.github.douira.glsl_transformer.ast;

import java.util.*;

public abstract class ArrayListASTNode<Child extends ASTNode> extends ListlikeASTNode<Child> {
  private List<Child> children = new ArrayList<>();

  public int size() {
    return children.size();
  }

  public boolean isEmpty() {
    return children.isEmpty();
  }

  public boolean contains(Object o) {
    return children.contains(o);
  }

  public Iterator<Child> iterator() {
    return children.iterator();
  }

  public Object[] toArray() {
    return children.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return children.toArray(a);
  }

  public boolean add(Child e) {
    return children.add(e);
  }

  public boolean remove(Object o) {
    return children.remove(o);
  }

  public boolean containsAll(Collection<?> c) {
    return children.containsAll(c);
  }

  public boolean addAll(Collection<? extends Child> c) {
    return children.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends Child> c) {
    return children.addAll(index, c);
  }

  public boolean removeAll(Collection<?> c) {
    return children.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return children.retainAll(c);
  }

  public void clear() {
    children.clear();
  }

  public Child get(int index) {
    return children.get(index);
  }

  public int indexOf(Object o) {
    return children.indexOf(o);
  }

  public Child set(int index, Child element) {
    return children.set(index, element);
  }

  public void add(int index, Child element) {
    children.add(index, element);
  }

  public Child remove(int index) {
    return children.remove(index);
  }

  public int lastIndexOf(Object o) {
    return children.lastIndexOf(o);
  }

  public ListIterator<Child> listIterator() {
    return children.listIterator();
  }

  public ListIterator<Child> listIterator(int index) {
    return children.listIterator(index);
  }

  public List<Child> subList(int fromIndex, int toIndex) {
    return children.subList(fromIndex, toIndex);
  }
}
