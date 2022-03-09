package io.github.douira.glsl_transformer.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The transformation holds information about dependencies between
 * transformation phases and nested transformations. It also has a root node and
 * an end node. The root node dependents on all nodes that have no dependents
 * while the end node is depended on by all nodes that have no dependencies. Any
 * directed acyclic graph of dependencies may be created between the nodes
 * within a transformation.
 * 
 * A dependency relationship between two nodes consists of a dependent and a
 * dependency node. The dependency node must be executed at some point before
 * the dependent node.
 * 
 * As a transformation is a lifecycle user, its internal state can be reset
 * before each transformation job.
 * 
 * A stateless (no inter-phase state) transformation can be created by simply
 * making an instance of this class and adding transformations to it. If state
 * between phases is needed, make a subclass and add any state as instance
 * fields. Then phases are created and added within the subclass' constructor.
 * There cannot be any state stored as local variables either in the scope that
 * created the {@code Transformation} instance or in a subclass' constructor as
 * it will not be reset if a transformation is run multiple times. In the same
 * vein, state should only be initialized in the {@link #init()} and
 * {@link #resetState()} methods.
 * 
 * TODO: unclear if sharing phases between transformation managers is
 * problematic since then the compiled paths/patterns in phases have a different
 * parser than the one being used for the transformation. Probably it doesn't
 * matter, and the parser is just used to figure out how the rules of the
 * tree are.
 */
public class Transformation<T> extends LifecycleUserImpl<T> {
  private final Map<LifecycleUser<T>, Node<T>> contentNodes = new HashMap<>();
  private Node<T> rootNode = new Node<>();
  private Node<T> endNode = new Node<>();

  // these defaults are for chaining in the same direction
  // (downwards for dependency, upwards for dependent)
  private Node<T> lastDependency = rootNode;
  private Node<T> lastDependent = endNode;

  {
    updateInternalLinks();
  }

  /**
   * Creates a stateless transformation and adds a single lifecycle user to it.
   * 
   * @param content The only lifecycle user to add to this transformation.
   *                Typically a transformation phase.
   */
  public Transformation(LifecycleUser<T> content) {
    addRootDependency(content);
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
    return Optional.ofNullable(contentNodes.get(content)).orElseGet(() -> {
      var newNode = new Node<T>(content);
      contentNodes.put(content, newNode);
      return newNode;
    });
  }

  private void updateInternalLinks() {
    rootNode.updateEndLink(endNode);
  }

  private void addDependency(Node<T> dependentNode, Node<T> dependencyNode) {
    // sanity check for cases that can happen when things are chained badly
    // (like chainDependent after addRootDependency)
    if (dependencyNode == rootNode) {
      throw new Error("The root node may not be made a dependency. Use prependDependent for replacing the root node.");
    }
    if (dependentNode == endNode) {
      throw new Error("The end node may not be made a dependent. Use appendDependency for replacing the end node.");
    }

    dependentNode.addDependency(dependencyNode);
    lastDependent = dependentNode;
    lastDependency = dependencyNode;

    dependentNode.updateBothLinks(rootNode, endNode);
    dependencyNode.updateBothLinks(rootNode, endNode);
    updateInternalLinks();
  }

  private void addDependent(Node<T> dependencyNode, Node<T> dependentNode) {
    addDependency(dependentNode, dependencyNode);
  }

  /**
   * Creates a dependency relationship between two nodes. This means the
   * dependency will be run before the dependent. Both of them are added to this
   * transformation if not already present.
   * 
   * @param dependent  The node depending on the dependency to have been run first
   * @param dependency The node that needs to be run before the dependent
   */
  public void addDependency(LifecycleUser<T> dependent, LifecycleUser<T> dependency) {
    addDependency(getNode(dependent), getNode(dependency));
  }

  /**
   * Creates a dependency relationship between two nodes. The meaning of dependent
   * and dependency are the same as in
   * {@link #addDependency(LifecycleUser, LifecycleUser)} but the positions are
   * switched. This is useful for constructing the dual algorithm in the
   * dependent/dependency structure. Usually the one is just the dependency graph
   * of the other but upside down.
   * 
   * @see #addDependency(LifecycleUser, LifecycleUser)
   * 
   * @param dependency The node being depended on that is executed first
   * @param dependent  The node depending on the dependency that is executed
   *                   second
   */
  public void addDependent(LifecycleUser<T> dependency, LifecycleUser<T> dependent) {
    addDependent(getNode(dependency), getNode(dependent));
  }

  /**
   * Adds a dependency to the last added dependency. If this is the first
   * dependency added to this transformation, this adds it as a dependency of the
   * root node.
   * 
   * @param dependency The node to add as a further dependency
   * @return The added node
   */
  public LifecycleUser<T> chainDependency(LifecycleUser<T> dependency) {
    addDependency(lastDependency, getNode(dependency));
    return dependency;
  }

  /**
   * Adds a dependent to the last added dependent. If this is the first dependent
   * added to this transformation, this adds it as a dependent of the end node.
   * 
   * @param dependent The node to add as a further dependent
   * @return The added node
   */
  public LifecycleUser<T> chainDependent(LifecycleUser<T> dependent) {
    addDependent(lastDependent, getNode(dependent));
    return dependent;
  }

  /**
   * Adds a dependency to the root node. All dependencies added by this method
   * can be run concurrently.
   * 
   * @param dependency The node to add as a root dependency
   * @return The added node
   */
  public LifecycleUser<T> addRootDependency(LifecycleUser<T> dependency) {
    addDependency(rootNode, getNode(dependency));
    return dependency;
  }

  /**
   * Adds a dependent to the end node. All dependents added by this method
   * can be run concurrently.
   * 
   * @param dependent The node to add as a end dependent
   * @return The added node
   */
  public LifecycleUser<T> addEndDependent(LifecycleUser<T> dependent) {
    addDependent(endNode, getNode(dependent));
    return dependent;
  }

  /**
   * Adds a dependency between the end node and all of its dependents. This
   * replaces the end node with a new end node.
   * 
   * @param newFinalDependency The node to place after all present dependencies
   * @return The added node
   */
  public LifecycleUser<T> appendDependency(LifecycleUser<T> newFinalDependency) {
    var previousEnd = endNode;
    endNode = new Node<T>();
    previousEnd.setContent(newFinalDependency);
    contentNodes.put(newFinalDependency, previousEnd);
    previousEnd.addDependency(endNode);
    lastDependent = previousEnd;
    lastDependency = endNode;
    return newFinalDependency;
  }

  /**
   * Adds a dependency between the root node and all of its dependencies. This
   * replaces the root node with a new root node.
   * 
   * @param newInitialDependent The node to place before all present dependencies
   * @return The added node
   */
  public LifecycleUser<T> prependDependent(LifecycleUser<T> newInitialDependent) {
    var previousRoot = rootNode;
    rootNode = new Node<T>();
    previousRoot.setContent(newInitialDependent);
    contentNodes.put(newInitialDependent, previousRoot);
    rootNode.addDependency(previousRoot);
    lastDependent = rootNode;
    lastDependency = previousRoot;
    return newInitialDependent;
  }

  /**
   * Adds a dependency to the last added dependent. The newly added dependency and
   * the last added dependency can be executed concurrently.
   * 
   * @param dependency The node to add as a dependency of the last added dependent
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentDependency(LifecycleUser<T> dependency) {
    addDependency(lastDependent, getNode(dependency));
    return dependency;
  }

  /**
   * Adds a dependent to the last added dependency. The newly added dependent and
   * the last added dependent can be executed concurrently.
   * 
   * @param dependent The node to add as a dependent of the last added dependency
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentDependent(LifecycleUser<T> dependent) {
    addDependent(lastDependency, getNode(dependent));
    return dependent;
  }

  /**
   * Adds the same node as a dependent to the last added dependency and as a
   * dependency to the last added dependent. The newly added node must be executed
   * before the last added dependency and after the last added dependent. This is
   * similar to inserting it directly between the two but is less invasive.
   * 
   * @param sibling The node to add between the last added dependency and
   *                dependent without breaking the existing dependency link
   *                between them
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentSibling(LifecycleUser<T> sibling) {
    var siblingNode = getNode(sibling);
    var lastDependencyLocal = lastDependency;
    addDependency(lastDependent, siblingNode);
    addDependent(lastDependencyLocal, siblingNode);
    return sibling;
  }
}
