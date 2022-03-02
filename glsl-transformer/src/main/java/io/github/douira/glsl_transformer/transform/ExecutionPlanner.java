package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  private final List<Collection<TransformationPhase<T>>> executionLevels = new ArrayList<>();
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
   * Registers a single transformation with this execution planner. When the phase
   * planner transforms a tree, the phases contributed by this transformation
   * will be run.
   * 
   * Multiple transformations can be added by calling this function multiple times
   * or by adding a single enclosing transformation that includes multiple
   * sub-transformations as concurrent dependencies.
   * 
   * @param transformation The transformation to collect the phases from
   */
  public void addConcurrent(Transformation<T> transformation) {
    rootTransformation.concurrent(transformation);
  }

  public Transformation<T> getRootTransformation() {
    return rootTransformation;
  }

  public void planExecution() {
    if (finalized) {
      throw new IllegalStateException("The execution planner should not be finalized multiple times!");
    }

    record StackEntry<R> (TransformationPhase<R> dependent, Node<R> node, Transformation<R> context) {
      StackEntry(Transformation<R> context) {
        this(null, null, context);
      }
    }

    class LabeledNode<R> {
      final TransformationPhase<R> phase;
      Collection<TransformationPhase<R>> dependencies = new HashSet<>();
      int searchIndex = -1;

      public LabeledNode(TransformationPhase<R> phase) {
        this.phase = phase;
      }

      void addDependency(TransformationPhase<R> phase) {
        dependencies.add(phase);
      }

      public void compact() {
        dependencies = new ArrayList<>(dependencies);
      }

      public TransformationPhase<R> getPhase() {
        return phase;
      }
    }

    // establish the the un-nested version of the graph by finding dependencies
    Map<TransformationPhase<T>, LabeledNode<T>> nodeMap = new HashMap<>();
    Collection<LabeledNode<T>> nodes = new ArrayList<>();
    Deque<StackEntry<T>> stack = new LinkedList<>();
    Set<Transformation<T>> transformationSet = new HashSet<>();
    Set<Node<T>> visited = new HashSet<>();

    // start the stack with a fake node that just contains the root transformation
    // this makes the first loop iteration like finding some nested transformation
    stack.push(new StackEntry<>(null, new Node<>(rootTransformation), rootTransformation));

    // TODO: verify correctness and efficiency
    while (!stack.isEmpty()) {
      var entry = stack.pop();
      var dependent = entry.dependent();
      var node = entry.node();
      var content = node.getContent();

      if (content instanceof Transformation<T> transformation) {
        transformationSet.add(transformation);

        // add an entry with the current context used for restoring it when the inner
        // context is done
        stack.push(new StackEntry<>(transformation));

        // start processing nested nodes of transformations with the root node
        stack.push(new StackEntry<>(dependent, transformation.getRootDepNode(), transformation));

        continue;
      }

      // add phase as a content dependency, transformations are not content
      // dependencies since they don't participate in execution directly
      var closestContent = dependent;
      if (content instanceof TransformationPhase<T> phase) {
        var labeledNode = nodeMap.get(dependent);
        if (labeledNode == null) {
          labeledNode = new LabeledNode<>(dependent);
          nodeMap.put(dependent, labeledNode);
          nodes.add(labeledNode);
        }
        labeledNode.addDependency(phase);

        closestContent = phase;
      }

      // TODO: don't process the same node's dependencies multiple times
      // make sure the stack ordering isn't messed up with this though
      // multiple iteration could happen when a node has multiple dependents

      var dependencies = node.getDependencies();
      if (!dependencies.isEmpty()) {
        var context = entry.context();

        // if the node is the end node of the context, restore the context using the
        // special entry added to the stack when the transformation was entered
        if (node == context.getEndDepNode()) {
          context = stack.pop().context();
        }

        // process dependencies for non-transformation content
        for (var dependency : node.getDependencies()) {
          stack.push(new StackEntry<>(closestContent, dependency, context));
        }
      }
    }

    // compact the gathered transformations into a list for fast iteration
    transformations.addAll(transformationSet);
    for (var node : nodes) {
      node.compact();
    }

    // do search (depth first) to label nodes with their depth number, while not
    // incrementing it for null-content nodes, collect transformations for init
    // TODO

    // sort labelled nodes into execution levels
    for (var node : nodes) {
      var index = node.searchIndex;
      var level = executionLevels.get(index);
      if (level == null) {
        level = new ArrayList<>();
        executionLevels.set(index, level);
      }
      level.add(node.getPhase());
    }

    finalized = true;
  }

  private void execute(TranslationUnitContext ctx) {
    if (!finalized) {
      throw new IllegalStateException("The execution planner has to be finalized before execution!");
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
    for (var iterator = executionLevels.listIterator(executionLevels.size()); iterator.hasPrevious();) {
      var level = iterator.previous();

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
