package io.github.douira.glsl_transformer.transform;

import java.util.HashMap;
import java.util.Map;

/**
 * The transformation holds information about dependencies between
 * transformation phases and nested transformations. It also has a root node and
 * an end node. The root node dependends on all nodes that have no dependents
 * while the end node is depended on by all nodes that have no dependencies.
 * 
 * Any directed acyclic graph of dependencies may be created between the nodes
 * within a transformation. As a transformation is a lifecycle user it's
 * internal state can be reset before each transformation job.
 * 
 * A stateless (no inter-phase state) transformation can be created by simply
 * making an instance of this class and adding transformations to it. If state
 * between phases is needed, make a subclass and add any state as instance
 * fields. Then phases are created and added within the subclass' constructor.
 * There cannot be any state stored as local variables either in the scope that
 * created the {@code Transformation} instance or in a subclass' constructor as
 * it will not be reset if a transformation is run multiple times. In the same
 * vein, state should only be initialized in the {@link #resetState()} method.
 * 
 * TODO: unclear if sharing phases between transformation managers is
 * problematic since then the compiled paths/patterns in phases have a different
 * parser than the one being used for the transformation. Probably it doesn't
 * matter and the parser is just used to figure out how the rules of the
 * tree are.
 */
public class Transformation<T> extends LifecycleUserImpl<T> {
  private final Map<LifecycleUser<T>, Node<T>> nodes = new HashMap<>();
  private Node<T> rootNode = new Node<>();
  private Node<T> endNode = new Node<>();
  private Node<T> lastAddedDependency;

  {
    updateInternalLinks();
  }

  /**
   * Creates a stateless transformation and adds a single phase to it.
   * 
   * @param phase The only transformation phase to add to a new stateless
   *              transformation
   */
  public Transformation(TransformationPhase<T> phase) {
    concurrent(phase);
  }

  /**
   * Creates a stateless transformation with no content, which can be added later.
   */
  public Transformation() {
  }

  Node<T> getRootDepNode() {
    return rootNode;
  }

  Node<T> getEndDepNode() {
    return endNode;
  }

  Node<T> getNode(LifecycleUser<T> content) {
    var node = nodes.get(content);
    if (node == null) {
      node = new Node<T>(content);
      nodes.put(content, node);
    }
    return node;
  }

  private void updateInternalLinks() {
    rootNode.updateEndLink(endNode);
  }

  private void addDependency(Node<T> dependentNode, Node<T> dependencyNode) {
    dependentNode.addDependency(dependencyNode);
    lastAddedDependency = dependencyNode;

    updateInternalLinks();
    dependentNode.updateBothLinks(rootNode, endNode);
    dependencyNode.updateBothLinks(rootNode, endNode);
  }

  public void addDependency(LifecycleUser<T> dependent, LifecycleUser<T> dependency) {
    addDependency(getNode(dependent), getNode(dependency));
  }

  public void chainDependency(LifecycleUser<T> dependency) {
    addDependency(lastAddedDependency, getNode(dependency));
  }

  public void append(LifecycleUser<T> toAppend) {
    var previousEnd = endNode;
    endNode = new Node<T>();
    previousEnd.setContent(toAppend);
    previousEnd.addDependency(endNode);
  }

  public void prepend(LifecycleUser<T> toPrepend) {
    var previousRoot = rootNode;
    rootNode = new Node<T>();
    previousRoot.setContent(toPrepend);
    rootNode.addDependency(previousRoot);
  }

  public Node<T> concurrent(LifecycleUser<T> dependency) {
    return rootNode.addDependency(getNode(dependency));
  }
}
