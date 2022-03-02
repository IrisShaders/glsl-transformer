package io.github.douira.glsl_transformer.transform;

import java.util.Collection;
import java.util.HashSet;

class Node<T> {
  private LifecycleUser<T> content;
  private Collection<Node<T>> dependencies;
  private int dependents = 0;

  Node() {
    dependencies = new HashSet<>();
  }

  Node(LifecycleUser<T> content) {
    this();
    this.content = content;
  }

  LifecycleUser<T> getContent() {
    return content;
  }

  void setContent(LifecycleUser<T> content) {
    this.content = content;
  }

  Collection<Node<T>> getDependencies() {
    return dependencies;
  }

  private void addDependent() {
    dependents++;
  }

  private void removeDependent() {
    dependents--;
  }

  boolean hasDependencies() {
    return !dependencies.isEmpty();
  }

  boolean hasDependents() {
    return dependents > 0;
  }

  Node<T> addDependency(Node<T> dependency) {
    dependencies.add(dependency);
    dependency.addDependent();
    return dependency;
  }

  void removeDependency(Node<T> dependency) {
    dependencies.remove(dependency);
    dependency.removeDependent();
  }

  private void setDependency(Node<T> dependency, boolean enable) {
    if (enable) {
      addDependency(dependency);
    } else {
      removeDependency(dependency);
    }
  }

  /**
   * Checks if an upwards link to the root node is required.
   */
  void updateRootLink(Node<T> rootNode) {
    rootNode.setDependency(this, !hasDependents());
  }

  /**
   * Checks if a downwards link to the end node is required.
   */
  void updateEndLink(Node<T> endNode) {
    this.setDependency(endNode, !hasDependencies());
  }

  void updateBothLinks(Node<T> rootNode, Node<T> endNode) {
    updateRootLink(rootNode);
    updateEndLink(endNode);
  }
}
