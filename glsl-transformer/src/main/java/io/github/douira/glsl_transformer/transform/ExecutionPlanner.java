package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.bsideup.jabel.Desugar;

import org.antlr.v4.runtime.BufferedTokenStream;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * The execution planner finds a valid way of satisfying the root
 * transformation's dependencies. All other transformations and phases are added
 * as dependencies to the root transformation.
 */
public abstract class ExecutionPlanner<T> {
  private List<Collection<TransformationPhase<T>>> executionLevels;
  private final Collection<Transformation<T>> transformations = new ArrayList<>();
  private final Transformation<T> rootTransformation = new Transformation<>();
  private TranslationUnitContext rootNode;
  private boolean finalized = false;
  private boolean initialized = false;

  /**
   * Returns this execution planner's parser. How the parser is stored is up to
   * the
   * implementing class.
   * 
   * @return The parser
   */
  public abstract GLSLParser getParser();

  /**
   * Returns the execution planner's lexer.
   * 
   * @return The lexer
   */
  public abstract GLSLLexer getLexer();

  /**
   * Returns the execution planner's current job parameters. This may be null if
   * the transformation manager's caller decides not to pass job parameters.
   * However, a convention to always pass valid job parameters (whatever that may
   * be) could be established if they are required for transformation phases to
   * function.
   * 
   * @return The job parameters
   */
  abstract T getJobParameters();

  /**
   * Returns the current root node being processed. Access to this method is
   * restricted to classes in this package on purpose. Classes extending
   * {@link TransformationPhase} should not have access to this but rather use it
   * through the provided injection method.
   * 
   * @return The current root node being processed
   */
  TranslationUnitContext getRootNode() {
    return rootNode;
  }

  /**
   * Registers a single lifecycle user with this execution planner. This can be a
   * transformation or a transformation phase.
   * 
   * Multiple transformations can be added by calling this function multiple times
   * or by adding a single enclosing transformation that includes multiple
   * sub-transformations as concurrent dependencies.
   * 
   * @param rootDependency The node to add as a dependency of the root node
   */
  public void addConcurrent(LifecycleUser<T> rootDependency) {
    rootTransformation.addRootDependency(rootDependency);
  }

  public Transformation<T> getRootTransformation() {
    return rootTransformation;
  }

  private static class LabeledNode<R> {
    final LifecycleUser<R> content;
    Collection<LabeledNode<R>> dependencies = new HashSet<>();
    int endDistance = -1;
    boolean dfsFinished = false;

    LabeledNode(LifecycleUser<R> content) {
      this.content = content;
    }

    LabeledNode() {
      content = null;
    }

    void addDependency(LabeledNode<R> dependency) {
      dependencies.add(dependency);
    }
  }

  @Desugar
  private static record CollectEntry<R> (Node<R> nodeToProcess, LabeledNode<R> dependent) {
  }

  @Desugar
  private static record DFSEntry<R> (LabeledNode<R> node, boolean enter) {
  }

  public void planExecution() {
    if (finalized) {
      throw new IllegalStateException(
          "The execution planner should not be finalized multiple times! Finalization is performed before the first execution or explicitly by calling planExecution.");
    }

    Set<Transformation<T>> transformationSet = new HashSet<>();
    Set<Node<T>> dependenciesProcessed = new HashSet<>();
    Map<Node<T>, LabeledNode<T>> endNodeMap = new HashMap<>();
    Map<LifecycleUser<T>, LabeledNode<T>> contentNodeMap = new HashMap<>();
    Deque<CollectEntry<T>> collectQueue = new LinkedList<>();

    var rootNode = new LabeledNode<T>();
    collectQueue.add(new CollectEntry<>(new Node<>(rootTransformation), rootNode));

    // traverse the tree converting all nodes to labeled nodes and combining
    // dependencies of transformations
    while (!collectQueue.isEmpty()) {
      // node can be: empty (root or end), phase, transformation
      var entry = collectQueue.poll();
      var node = entry.nodeToProcess();
      var content = node.getContent();

      LabeledNode<T> labeledNode;
      if (content == null) {
        // check if a node has already been generated if this is an end node
        labeledNode = Optional
            .ofNullable(endNodeMap.get(node))
            // if there is no content, create a new labeled node for this unlabeled node
            .orElseGet(LabeledNode::new);
      } else {
        // if there content, find the previously created labeled node for it
        labeledNode = Optional
            .ofNullable(contentNodeMap.get(content))
            .orElseGet(() -> {
              var newNode = new LabeledNode<>(content);
              contentNodeMap.put(content, newNode);
              return newNode;
            });
      }

      // tell the node that queued processing this node about this labeled node
      entry.dependent().addDependency(labeledNode);

      /*
       * Only process dependencies if not done yet for this node.
       * Since this is per node (which are transformation-specific), the same content
       * may be queued for processing multiple times but since the label nodes are
       * created only once for each content, the duplicate traversal is prevented.
       * Label nodes aren't bound for root nodes, but they aren't depended on by
       * definition. End nodes can have multiple dependents and label nodes for them
       * are de-duplicated using the endNodeMap.
       */
      if (!dependenciesProcessed.contains(node)) {
        dependenciesProcessed.add(node);

        if (Transformation.class.isInstance(content)) {
          // a transformation's dependencies should be dependencies of the end node.
          // use an existing labeled node for this end node if there is one already
          var transformation = (Transformation<T>) content;
          var endNode = transformation.getEndDepNode();
          var endLabeledNode = Optional
              .ofNullable(endNodeMap.get(endNode))
              .orElseGet(() -> {
                var newNode = new LabeledNode<T>();
                endNodeMap.put(endNode, newNode);
                return newNode;
              });
          for (var dependency : node.getDependencies()) {
            collectQueue.add(new CollectEntry<>(dependency, endLabeledNode));
          }

          // and the root node is a dependency of the node for the transformation
          collectQueue.add(new CollectEntry<>(transformation.getRootDepNode(), labeledNode));

          transformationSet.add(transformation);
        } else {
          // queue processing of dependencies
          for (var dependency : node.getDependencies()) {
            collectQueue.add(new CollectEntry<>(dependency, labeledNode));
          }
        }
      }
    }

    // compact the gathered transformations into the final list for fast iteration
    transformations.addAll(transformationSet);

    int maximumDepth = 0;
    Deque<DFSEntry<T>> dfsStack = new LinkedList<>();
    dfsStack.add(new DFSEntry<>(rootNode, true));

    // generate a topological sort with the first item in the list being an end node
    while (!dfsStack.isEmpty()) {
      var entry = dfsStack.pop();
      var node = entry.node();
      if (entry.enter()) {
        if (!node.dfsFinished) {
          dfsStack.add(new DFSEntry<>(node, false));
          for (var dependency : node.dependencies) {
            dfsStack.add(new DFSEntry<>(dependency, true));
          }
        }
      } else {
        node.dfsFinished = true;

        // nodes will be finished in the order of the topological sort.
        // find the maximum distance any dependency has from the end node
        for (var dependency : node.dependencies) {
          if (dependency.endDistance > node.endDistance) {
            node.endDistance = dependency.endDistance;
          }
        }

        // if this is a transformation phase node, increment one above the maximum
        // distance of the dependencies. other nodes don't increase the distance.
        if (TransformationPhase.class.isInstance(node.content)) {
          node.endDistance++;
          maximumDepth = Math.max(node.endDistance, maximumDepth);
        }
      }
    }

    executionLevels = new ArrayList<>(maximumDepth + 1);
    for (int i = 0; i <= maximumDepth; i++) {
      executionLevels.add(new ArrayList<TransformationPhase<T>>());
    }

    // place labelled nodes that contain phases into execution levels
    for (var node : contentNodeMap.values()) {
      if (TransformationPhase.class.isInstance(node.content)) {
        executionLevels.get(node.endDistance).add((TransformationPhase<T>) node.content);
      }
    }

    finalized = true;
  }

  private void execute(TranslationUnitContext ctx) {
    if (!finalized) {
      planExecution();
    }

    rootNode = ctx;

    // refresh each transformation's state before starting the transformation
    for (var transformation : transformations) {
      transformation.setPlanner(this);
      if (!initialized) {
        transformation.init();
      }
      transformation.resetState();
    }

    // iterate the levels in reverse order since level 0 in the execution levels
    // depends on those with higher indexes
    for (var level : executionLevels) {
      var proxyListener = new ProxyParseTreeListener(new ArrayList<>());

      for (var phase : level) {
        phase.setPlanner(this);
        if (!initialized) {
          phase.init();
        }
        phase.resetState();

        if (phase.checkBeforeWalk(ctx)) {
          proxyListener.add(phase);
        }
      }

      if (!proxyListener.isEmpty()) {
        DynamicParseTreeWalker.DEFAULT.walk(proxyListener, ctx);
      }

      for (var phase : level) {
        phase.runAfterWalk(ctx);
      }
    }

    rootNode = null;
    initialized = true;
  }

  /**
   * Transforms the given parse tree with the registered transformations.
   * 
   * @param ctx         The root node of the parse tree to be transformed
   * @param tokenStream The token stream of the parse tree
   */
  protected void transformTree(TranslationUnitContext ctx, BufferedTokenStream tokenStream) {
    ctx.makeLocalRoot(tokenStream);
    execute(ctx);
  }
}
