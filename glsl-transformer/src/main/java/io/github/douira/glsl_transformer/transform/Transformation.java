package io.github.douira.glsl_transformer.transform;

import java.util.*;
import java.util.function.*;

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
 * An interesting effect of the automatic root and end node linking is that it's
 * impossible to create cycles that don't have dangling bits since a bare cycle
 * will be completely disconnected from the rest of the graph.
 * 
 * Since phases are deduplicated within transformations and during execution
 * planning, adding a single phase instance multiple times either doesn't do
 * anything or causes a execution planning error. In order repeat the execution
 * of a phase multiple times, simply create a lambda function (a
 * {@code Supplier<TransformationPhase<T>>}) that generates a phase and run it
 * each time you need an independent instance of it.
 * 
 * TODO: unclear if sharing phases between transformation managers is
 * problematic since then the compiled paths/patterns in phases have a different
 * parser than the one being used for the transformation. Probably it doesn't
 * matter, and the parser is just used to figure out how the rules of the
 * tree are.
 */
public class Transformation<T extends JobParameters> extends LifecycleUserImpl<T> {
  private final Map<LifecycleUser<T>, Node<T>> contentNodes = new HashMap<>();
  private final Set<Transformation<T>> transformations = new HashSet<>();
  private Node<T> rootNode;
  private Node<T> endNode;
  private Node<T> lastDependency;
  private Node<T> lastDependent;
  private boolean usesConditionalGraph = false;
  private Object updateTag;

  {
    resetGraph();
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

  /**
   * An alternative reset method that is also called like {@link #resetState()}
   * but should be used for core transformation internals. This is one so that
   * users of core transformations don't have to remember to call the super
   * implementation of {@link #resetState()} themselves.
   */
  protected void triggerJobInternal() {
  }

  /**
   * If conditional dependencies are required for this transformation, all
   * dependencies should be added within this method. Fixed job parameters may
   * be accessed through {@link #getJobParameters()}. Only either dependencies
   * added in this method or statically set dependencies may be used at once. If
   * dependencies are added statically, this method is never run and no
   * conditional dependencies can be created.
   * 
   * For more efficient memory usage, it's recommended to create the dependencies
   * that are setup within this method once and then simply add their references
   * as dependencies in this method. Creating them in this method will generate
   * copies of the created phases.
   */
  protected void setupGraph() {
  }

  /**
   * Called by the execution planner to reset this transformation's dependency
   * graph with the current fixed parameters.
   * 
   * On the first graph setup there will be no content nodes if a conditional
   * dependency graph is used. {@link #setupGraph()} isn't called if any static
   * dependencies were created in the constructor, in the instance initializer or
   * from outside the instance before execution planning took place.
   */
  private void doGraphSetup(Object updateTag) {
    if (this.updateTag == updateTag) {
      return;
    }
    this.updateTag = updateTag;

    if (contentNodes.isEmpty()) {
      usesConditionalGraph = true;
    }
    if (usesConditionalGraph) {
      resetGraph();
      setupGraph();
    }

    // do graph setup on all current transformations
    for (Transformation<T> transformation : transformations) {
      transformation.setPlanner(getPlanner());
      transformation.doGraphSetup(updateTag);
    }
  }

  /**
   * Generates an update tag to keep track of which update walk this is in order
   * to prevent duplicate traversal of dependencies that are used by multiple
   * transformations.
   * 
   * {@see #doGraphSetup(Object)}
   */
  void doGraphSetup() {
    doGraphSetup(new Object());
  }

  private void resetGraph() {
    rootNode = new Node<>();
    endNode = new Node<>();
    contentNodes.clear();
    transformations.clear();

    // these defaults are for chaining in the same direction
    // (downwards for dependency, upwards for dependent)
    lastDependency = rootNode;
    lastDependent = endNode;

    updateInternalLinks();
  }

  Node<T> getRootDepNode() {
    return rootNode;
  }

  Node<T> getEndDepNode() {
    return endNode;
  }

  Collection<Node<T>> getContentNodes() {
    return contentNodes.values();
  }

  private void addContentNode(LifecycleUser<T> content, Node<T> node) {
    contentNodes.put(content, node);
    if (content instanceof Transformation) {
      transformations.add((Transformation<T>) content);
    }
  }

  private Node<T> getNode(LifecycleUser<T> content) {
    return Optional.ofNullable(contentNodes.get(content)).orElseGet(() -> {
      var newNode = new Node<T>(content);
      addContentNode(content, newNode);
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
      throw new AssertionError(
          "The root node may not be made a dependency. Use prependDependency for replacing the root node.");
    }
    if (dependentNode == endNode) {
      throw new AssertionError(
          "The end node may not be made a dependent. Use appendDependent for replacing the end node.");
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
   * replaces the end node with a new end node. This method is called
   * appendDependent because it adds a new node that is the only dependent of the
   * root node after this operation. Furthermore, chaining after this method will
   * see the node with the new content as the dependent and the new end node as
   * the dependency.
   * 
   * @param newSoleEndDependent The node to place after all present dependencies
   * @return The added node
   */
  public LifecycleUser<T> appendDependent(LifecycleUser<T> newSoleEndDependent) {
    var soleEndDependency = endNode;
    endNode = new Node<T>();
    soleEndDependency.setContent(newSoleEndDependent);
    addContentNode(newSoleEndDependent, soleEndDependency);
    soleEndDependency.addDependency(endNode);
    lastDependent = soleEndDependency;
    lastDependency = endNode;
    return newSoleEndDependent;
  }

  /**
   * Adds a dependency between the root node and all of its dependencies. This
   * replaces the root node with a new root node.
   * 
   * See {@link #appendDependent(LifecycleUser)} for why this method is called
   * this way. The argument is the same.
   * 
   * @param newSoleRootDependency The node to place before all present
   *                              dependencies
   * @return The added node
   */
  public LifecycleUser<T> prependDependency(LifecycleUser<T> newSoleRootDependency) {
    var soleRootDependency = rootNode;
    rootNode = new Node<T>();
    soleRootDependency.setContent(newSoleRootDependency);
    addContentNode(newSoleRootDependency, soleRootDependency);
    rootNode.addDependency(soleRootDependency);
    lastDependent = rootNode;
    lastDependency = soleRootDependency;
    return newSoleRootDependency;
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

  /**
   * Generates a transformation that contains the lifecycle user generated by the
   * given supplier a given number of times. Since a single instance can't be
   * 
   * @param <R>       The job parameter type
   * @param count     How many copies should be generated
   * @param consumer  The function that takes the generates lifecycle users and
   *                  adds them to a transformation
   * @param generator The function that generates lifecycle users
   * @return A transformation with the added lifecycle users
   */
  public static <R extends JobParameters> Transformation<R> repeat(
      int count, BiConsumer<Transformation<R>, LifecycleUser<R>> consumer, Supplier<LifecycleUser<R>> generator) {
    return new Transformation<>() {
      {
        for (var i = 0; i < count; i++) {
          consumer.accept(this, generator.get());
        }
      }
    };
  }

  /**
   * Generates a transformation that runs a given number of copies of the given
   * lifecycle user (given by the generator function) in series.
   * 
   * @param <R>       The job parameter type
   * @param count     How many copies should be generated
   * @param generator The function that generates lifecycle users
   * @return A transformation with the added lifecycle users
   */
  public static <R extends JobParameters> Transformation<R> repeatSequential(
      int count, Supplier<LifecycleUser<R>> generator) {
    return repeat(count, Transformation::chainDependent, generator);
  }

  /**
   * Generates a transformation that runs a given number of copies of the given
   * lifecycle user (given by the generator function) in parallel.
   * 
   * @param <R>       The job parameter type
   * @param count     How many copies should be generated
   * @param generator The function that generates lifecycle users
   * @return A transformation with the added lifecycle users
   */
  public static <R extends JobParameters> Transformation<R> repeatParallel(
      int count, Supplier<LifecycleUser<R>> generator) {
    return repeat(count, Transformation::addEndDependent, generator);
  }
}
