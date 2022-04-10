package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
public abstract class ExecutionPlanner<T extends JobParameters> {
  private Map<T, ExecutionPlan<T>> executionPlanCache = new HashMap<>();
  private final Transformation<T> rootTransformation = new Transformation<>() {
    @Override
    public FixedActivation getFixedActivation() {
      return FixedActivation.SHALLOW;
    }
  };
  private TranslationUnitContext rootNode;
  private ProxyParseTreeListener proxyListener;

  private class ExecutionPlan<R extends JobParameters> {
    List<ExecutionLevel<R>> executionLevels;
    Collection<Transformation<R>> transformations;
    final R fixedParameters;

    ExecutionPlan(R fixedParameters) {
      this.fixedParameters = fixedParameters;
    }

    @Desugar
    private static record ExecutionLevel<S extends JobParameters> (
        Collection<TransformationPhase<S>> walkPhases,
        List<TransformationPhase<S>> nonWalkPhases) implements Iterable<TransformationPhase<S>> {
      public ExecutionLevel() {
        this(new ArrayList<>(), new ArrayList<>());
      }

      @Override
      public Iterator<TransformationPhase<S>> iterator() {
        return Stream.concat(walkPhases().stream(), nonWalkPhases().stream()).iterator();
      }
    }

    static class LabeledNode<R extends JobParameters> {
      final LifecycleUser<R> content;
      Collection<LabeledNode<R>> dependencies = new HashSet<>();
      Collection<LabeledNode<R>> dependents = new HashSet<>();

      int executionLevelIndex = Integer.MIN_VALUE;
      boolean dfsFinished = false;

      LabeledNode(LifecycleUser<R> content) {
        this.content = content;
      }

      LabeledNode() {
        content = null;
      }

      void addDependency(LabeledNode<R> dependency) {
        dependencies.add(dependency);
        dependency.dependents.add(this);
      }
    }

    @Desugar
    record CollectEntry<S extends JobParameters> (Node<S> nodeToProcess, LabeledNode<S> dependent) {
    }

    @Desugar
    record DFSEntry<S extends JobParameters> (LabeledNode<S> node, boolean enter) {
    }

    /**
     * Calculates the execution plan for the constructed graph of dependencies.
     * 
     * @implNote First it resolves the nested transformation so that the whole
     *           dependency graph can be traversed directly. Then nodes are visited
     *           in toplogically sorted order. Their maximum distance from the first
     *           visited node, an end node, is calculated. Finally the nodes are
     *           sorted into execution levels according to their distance values.
     *           The nodes with the lowest distance are executed first.
     */
    void planExecution(Transformation<R> rootTransformation) {
      Set<Transformation<R>> transformationSet = new HashSet<>();
      Set<Node<R>> dependenciesProcessed = new HashSet<>();
      Map<Node<R>, LabeledNode<R>> endNodeMap = new HashMap<>();
      Map<LifecycleUser<R>, LabeledNode<R>> contentNodeMap = new HashMap<>();
      Deque<CollectEntry<R>> collectQueue = new LinkedList<>();

      var rootNode = new LabeledNode<R>();
      collectQueue.add(new CollectEntry<>(new Node<>(rootTransformation), rootNode));

      //TODO: implement fixed activation with the new method

      // traverse the tree converting all nodes to labeled nodes and combining
      // dependencies of transformations
      while (!collectQueue.isEmpty()) {
        // node can be: empty (root or end), phase, transformation
        var entry = collectQueue.poll();
        var node = entry.nodeToProcess();
        var content = node.getContent();

        LabeledNode<R> labeledNode;
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
            var transformation = (Transformation<R>) content;
            var endNode = transformation.getEndDepNode();
            var endLabeledNode = Optional
                .ofNullable(endNodeMap.get(endNode))
                .orElseGet(() -> {
                  var newNode = new LabeledNode<R>();
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
      transformations = new ArrayList<>();
      transformations.addAll(transformationSet);

      Deque<DFSEntry<R>> dfsStack = new LinkedList<>();
      dfsStack.push(new DFSEntry<>(rootNode, true));

      // the topological sort starts at the end node and goes to the root node
      List<LabeledNode<R>> topoSort = new ArrayList<>();

      // generate a topological sort with the first item in the list being an end node
      while (!dfsStack.isEmpty()) {
        if (dfsStack.size() > dependenciesProcessed.size() * 2) {
          throw new Error(
              "The dependency graph could not be satisfied! There is may be a cycle in it or the root and end nodes are messed up. Check for cycles in the graph after construction and after resolving transformations. Also make sure there is a single end and a single (generated) root node.");
        }

        var entry = dfsStack.pop();
        var node = entry.node();
        if (entry.enter()) {
          if (!node.dfsFinished) {
            dfsStack.push(new DFSEntry<>(node, false));
            for (var dependency : node.dependencies) {
              dfsStack.push(new DFSEntry<>(dependency, true));
            }
          }
        } else {
          // nodes will be finished in the order of the topological sort.
          node.dfsFinished = true;
          topoSort.add(node);
        }
      }

      // The minimum execution level of a node is the maximum minimum execution level
      // of all dependencies + 1.
      executionLevels = new ArrayList<>();
      executionLevels.add(new ExecutionLevel<>());
      topoSort.get(0).executionLevelIndex = -1;

      // iterate the nodes topologically sorted in the end to root direction
      for (var node : topoSort) {
        for (var neighbor : node.dependencies) {
          if (neighbor.executionLevelIndex > node.executionLevelIndex) {
            node.executionLevelIndex = neighbor.executionLevelIndex;
          }
        }

        if (TransformationPhase.class.isInstance(node.content)) {
          var phase = (TransformationPhase<R>) node.content;
          if (phase.canWalk()) {
            node.executionLevelIndex++;

            if (executionLevels.size() <= node.executionLevelIndex + 1) {
              executionLevels.add(new ExecutionLevel<>());
            }
            executionLevels.get(node.executionLevelIndex + 1).walkPhases().add(phase);
          } else {
            executionLevels.get(node.executionLevelIndex + 1).nonWalkPhases.add(phase);
          }
        }
      }
    }

    void execute(ExecutionPlanner<R> planner) {
      // refresh each transformation's state before starting the transformation
      for (var transformation : transformations) {
        transformation.setPlanner(planner);
        transformation.initOnce();
        transformation.resetState();
      }

      // iterate the levels in reverse order since level 0 in the execution levels
      // depends on those with higher indexes
      for (var level : executionLevels) {
        proxyListener = new ProxyParseTreeListener(new ArrayList<>());

        // process the concurrently processable walk phases in one tree-walk
        for (var walkPhase : level.walkPhases()) {
          walkPhase.setPlanner(planner);
          walkPhase.initOnce();
          walkPhase.resetState();
          if (walkPhase.checkBeforeWalk(rootNode)) {
            proxyListener.add(walkPhase);
            walkPhase.resetWalkFinishState();
          }
        }
        if (!proxyListener.isEmpty()) {
          DynamicParseTreeWalker.DEFAULT.walk(proxyListener, rootNode);
        }
        for (var walkPhase : level.walkPhases()) {
          walkPhase.runAfterWalk(rootNode);
        }

        // process each non-walking phase individually
        for (var nonWalkPhase : level.nonWalkPhases()) {
          nonWalkPhase.setPlanner(planner);
          nonWalkPhase.initOnce();
          nonWalkPhase.resetState();
          nonWalkPhase.checkBeforeWalk(rootNode);
          nonWalkPhase.runAfterWalk(rootNode);
        }
      }

      proxyListener = null;
    }
  }

  /**
   * Returns this execution planner's parser. How the parser is stored is up to
   * the implementing class.
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

  /**
   * Returns the root transformation that contains all other phases and
   * transformations. This is exposed so that simple structures may be added
   * without requiring nested transformations.
   * 
   * @return The root transformation instance
   */
  public Transformation<T> getRootTransformation() {
    return rootTransformation;
  }

  void removeCurrentPhaseFromWalk() {
    proxyListener.removeCurrentListener();
  }

  private ExecutionPlan<T> getExecutionPlanFor(T parameters) {
    // make sure there is a planned execution plan for the fixed part of the
    // parameters
    var jobParameters = getJobParameters();
    var plan = executionPlanCache.get(jobParameters);
    if (plan == null) {
      plan = new ExecutionPlan<>(jobParameters);
      plan.planExecution(rootTransformation);
      executionPlanCache.put(jobParameters, plan);
    }
    return plan;
  }

  public void planExecutionFor(T parameters) {
    getExecutionPlanFor(parameters);
  }

  private void execute(TranslationUnitContext ctx) {
    var plan = getExecutionPlanFor(getJobParameters());
    rootNode = ctx;
    plan.execute(this);
    rootNode = null;
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
