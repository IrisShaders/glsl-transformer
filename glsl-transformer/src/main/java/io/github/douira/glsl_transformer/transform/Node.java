package io.github.douira.glsl_transformer.transform;

import java.util.Collection;
import java.util.HashSet;

class Node<T> {
  private LifecycleUser<T> content;
  private Collection<Node<T>> dependencies = new HashSet<>();
  private Node<T> latestDependent;
  private int dependents = 0;

  Node() {
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

  Node<T> getLatestDependent() {
    return latestDependent;
  }

  private void addDependent(Node<T> dependent) {
    latestDependent = dependent;
    dependents++;
  }

  private void removeDependent(Node<T> dependent) {
    if (latestDependent == dependent) {
      latestDependent = null;
    }
    dependents--;
  }

  boolean hasDependencies() {
    return !dependencies.isEmpty();
  }

  boolean hasDependents() {
    return dependents > 0;
  }

  void addDependency(Node<T> dependency) {
    dependencies.add(dependency);
    dependency.addDependent(this);
  }

  void removeDependency(Node<T> dependency) {
    dependencies.remove(dependency);
    dependency.removeDependent(this);
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
