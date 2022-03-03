package io.github.douira.glsl_transformer.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    addRootDependency(phase);
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

  private Node<T> getNode(LifecycleUser<T> content) {
    return Optional.ofNullable(nodes.get(content)).orElseGet(() -> {
      var newNode = new Node<T>(content);
      nodes.put(content, newNode);
      return newNode;
    });
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

  /**
   * Adds a dependency between two nodes. This means the dependency will be run
   * before the dependent. Both of them are added to this transformation if not
   * already present.
   * 
   * @param dependent  The node depending on the dependency to have been run first
   * @param dependency The node that needs to be run before the dependent
   */
  public void addDependency(LifecycleUser<T> dependent, LifecycleUser<T> dependency) {
    addDependency(getNode(dependent), getNode(dependency));
  }

  public void addDependent(LifecycleUser<T> dependent, LifecycleUser<T> dependency) {
    addDependency(dependency, dependent);
  }

  /**
   * Adds a dependency for the last added dependency. If this is the first
   * dependency added to this transformation, this adds it as a dependency of the
   * root node.
   * 
   * @param dependency The node to add as a further dependency
   */
  public LifecycleUser<T> chainDependency(LifecycleUser<T> dependency) {
    addDependency(Optional.ofNullable(lastAddedDependency).orElse(rootNode), getNode(dependency));
    return dependency;
  }

  public LifecycleUser<T> chainDependent(LifecycleUser<T> dependent) {
    addDependency(getNode(dependent), Optional.ofNullable(lastAddedDependency).orElse(endNode));
    return dependent;
  }

  /**
   * Adds a dependency between the end node and all of its dependents. This
   * replaces the end node with a new end node.
   * 
   * @param toAppend
   */
  public LifecycleUser<T> append(LifecycleUser<T> toAppend) {
    var previousEnd = endNode;
    endNode = new Node<T>();
    previousEnd.setContent(toAppend);
    nodes.put(toAppend, previousEnd);
    previousEnd.addDependency(endNode);
    return toAppend;
  }

  /**
   * Adds a dependency between the root node and all of its dependencies. This
   * replaces the root node with a new root node.
   * 
   * @param toPrepend The node to place before all present dependencies
   */
  public LifecycleUser<T> prepend(LifecycleUser<T> toPrepend) {
    var previousRoot = rootNode;
    rootNode = new Node<T>();
    previousRoot.setContent(toPrepend);
    nodes.put(toPrepend, previousRoot);
    rootNode.addDependency(previousRoot);
    return toPrepend;
  }

  /**
   * Adds a dependency for the root node. All dependencies added by this method
   * can be run concurrently.
   * 
   * @param dependency The node to add as a root dependency
   * @return The added node
   */
  public LifecycleUser<T> addRootDependency(LifecycleUser<T> dependency) {
    var dependencyNode = getNode(dependency);
    addDependency(rootNode, dependencyNode);
    return dependency;
  }

  public LifecycleUser<T> addEndDependent(LifecycleUser<T> dependent) {
    var dependentNode = getNode(dependent);
    addDependency(dependentNode, endNode);
    return dependent;
  }

  /**
   * Adds a dependency for the dependent of the last added dependency. If there is
   * no such dependency this method will throw. The dependencies added by this
   * method can be run concurrently with other methods added by it.
   * 
   * @param dependency The node to add as a dependency of the last added
   *                   dependency's dependent
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentDependency(LifecycleUser<T> dependency) {
    if (lastAddedDependency == null) {
      throw new IllegalStateException(
          "Can't add a concurrent dependency for the last added dependency if none was added yet!");
    }
    var latestDependent = lastAddedDependency.getLatestDependent();
    if (latestDependent == null) {
      throw new IllegalStateException(
          "Can't add a concurrent dependency for the last added dependency if it was removed again! Only the last dependency is stored for this feature.");
    }

    var dependencyNode = getNode(dependency);
    addDependency(latestDependent, dependencyNode);
    return dependency;
  }

  //TODO: chainConcurrentDependent
}
