package io.github.douira.glsl_transformer.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The transformation holds information about dependencies between
 * transformation phases and nested transformations. It also has a root node and
 * an end node. The root node dependents on all nodes that have no dependents
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

  public void addDependent(LifecycleUser<T> dependency, LifecycleUser<T> dependent) {
    addDependency(dependent, dependency);
  }

  /**
   * Adds a dependency to the last added dependency. If this is the first
   * dependency added to this transformation, this adds it as a dependency of the
   * root node.
   * 
   * @param dependency The node to add as a further dependency
   */
  public LifecycleUser<T> chainDependency(LifecycleUser<T> dependency) {
    addDependency(lastDependency, getNode(dependency));
    return dependency;
  }

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

  public LifecycleUser<T> addEndDependent(LifecycleUser<T> dependent) {
    addDependent(endNode, getNode(dependent));
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
    contentNodes.put(toAppend, previousEnd);
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
    contentNodes.put(toPrepend, previousRoot);
    rootNode.addDependency(previousRoot);
    return toPrepend;
  }

  private Node<T> getNewestSubDependency() {
    var newestDependency = lastDependency.getNewestDependency();
    if (newestDependency == null) {
      throw new IllegalStateException(
          "The latest dependency of the last added dependency was removed! Only the last dependency is stored for this feature.");
    }
    return newestDependency;
  }

  private Node<T> getNewestSuperDependent() {
    var newestDependent = lastDependent.getNewestDependent();
    if (newestDependent == null) {
      throw new IllegalStateException(
          "The latest dependent of the last added dependent was removed! Only the last dependent is stored for this feature.");
    }
    return newestDependent;
  }

  /**
   * Adds a dependency to the dependent of the last added dependency. (sibling
   * with a common dependent) If there is no such dependency this method will
   * throw. The dependencies added by this method can be run concurrently with
   * other methods added by it.
   * 
   * @param dependency The node to add as a dependency of the last added
   *                   dependency's dependent
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentDependency(LifecycleUser<T> dependency) {
    var dependencyNode = getNode(dependency);
    addDependency(getNewestSuperDependent(), dependencyNode);
    return dependency;
  }

  /**
   * Adds a dependent to the dependency of the last added node. (Sibling with a
   * common dependency)
   * 
   * @param dependent The node to add as a dependent of the last added
   *                  dependency's dependency.
   * @return The added node
   */
  public LifecycleUser<T> chainConcurrentDependent(LifecycleUser<T> dependent) {
    var dependentNode = getNode(dependent);
    addDependency(dependentNode, getNewestSubDependency());
    return dependent;
  }

  public LifecycleUser<T> chainConcurrentBoth(LifecycleUser<T> sibling) {
    var siblingNode = getNode(sibling);
    addDependency(siblingNode, getNewestSubDependency());
    addDependent(siblingNode, getNewestSuperDependent());
    return sibling;
  }
}
