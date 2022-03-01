package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

  private static class LabeledNode<T> extends Node<T> {
    int searchIndex = -1;
  }

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

  public void finalize() {
    if (finalized) {
      throw new IllegalStateException("The execution planner should not be finalized multiple times!");
    }

    // establish the entire graph including null-nodes but no nested structures
    // (resolve nested Transformations)

    // do search (depth first) to label nodes with their depth number, while not
    // incrementing it for null-content nodes, collect transformations for init

    // sort labelled nodes into execution levels

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
