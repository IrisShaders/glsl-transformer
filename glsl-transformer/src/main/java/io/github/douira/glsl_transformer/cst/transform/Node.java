package io.github.douira.glsl_transformer.cst.transform;

import java.util.*;

import io.github.douira.glsl_transformer.cst.transform.lifecycle.LifecycleUser;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

class Node<T extends JobParameters> {
  private LifecycleUser<T> content;
  private Collection<Node<T>> dependencies = new HashSet<>();
  private Collection<Node<T>> dependents = new HashSet<>();

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

  Collection<Node<T>> getDependents() {
    return dependents;
  }

  void addDependent(Node<T> dependent) {
    if (dependents.add(dependent)) {
      dependent.addDependency(this);
    }
  }

  void removeDependent(Node<T> dependent) {
    if (dependents.remove(dependent)) {
      dependent.removeDependency(this);
    }
  }

  void addDependency(Node<T> dependency) {
    if (dependencies.add(dependency)) {
      dependency.addDependent(this);
    }
  }

  void removeDependency(Node<T> dependency) {
    if (dependencies.remove(dependency)) {
      dependency.removeDependent(this);
    }
  }

  private void setDependent(Node<T> dependent, boolean enable) {
    if (dependents.contains(dependent) == enable) {
      return;
    }

    if (enable) {
      addDependent(dependent);
    } else {
      removeDependent(dependent);
    }
  }

  private void setDependency(Node<T> dependency, boolean enable) {
    if (dependencies.contains(dependency) == enable) {
      return;
    }

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
    if (this != rootNode) {
      var setRootLink = dependents.isEmpty();

      // don't remove the root dependent if it's the only one
      if (!setRootLink && dependents.size() == 1 && dependents.contains(rootNode)) {
        return;
      }
      setDependent(rootNode, setRootLink);
    }
  }

  /**
   * Checks if a downwards link to the end node is required.
   */
  void updateEndLink(Node<T> endNode) {
    if (this != endNode) {
      var setEndLink = dependencies.isEmpty();

      // don't remove the end dependency if it's the only one
      if (!setEndLink && dependencies.size() == 1 && dependencies.contains(endNode)) {
        return;
      }
      setDependency(endNode, setEndLink);
    }
  }

  void updateBothLinks(Node<T> rootNode, Node<T> endNode) {
    updateRootLink(rootNode);
    updateEndLink(endNode);
  }
}
