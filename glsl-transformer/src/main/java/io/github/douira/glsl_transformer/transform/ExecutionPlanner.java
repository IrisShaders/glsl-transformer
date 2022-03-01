package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.antlr.v4.runtime.BufferedTokenStream;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser;
import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.transform.Transformation.Node;
import io.github.douira.glsl_transformer.util.ComparablePair;

/**
 * The execution planner finds a valid way of satisfying the root
 * transformation's dependencies. All other transformations and phases are added
 * as dependencies to the root transformation.
 */
public abstract class ExecutionPlanner<T> {
  private final Map<ComparablePair<Integer, Integer>, List<TransformationPhase<T>>> executionLevels = new TreeMap<>();
  private final Collection<Transformation<T>> transformations = new ArrayList<>();
  private Transformation<T> rootTransformation = new Transformation<>();

  private TranslationUnitContext rootNode;

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

  public void finalize() {
    // TODO: do execution planning and set flag to allow
  }

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
  public void addConcurrentTransformation(Transformation<T> transformation) {
    rootTransformation.concurrent(transformation);
  }

  /**
   * Adds a single phase entry and inserts it at its position within the list of
   * execution levels and phases within levels.
   * 
   * @param entry The entry containing the index, group index and phase to be
   *              added
   */
  void addPhaseAt(Node<T> entry) {
    var phase = entry.phase();
    var indexPair = new ComparablePair<>(entry.group(), entry.index());
    var phasesForIndex = executionLevels.get(indexPair);
    if (phasesForIndex == null) {
      phasesForIndex = new ArrayList<>();
      executionLevels.put(indexPair, phasesForIndex);
    }
    phasesForIndex.add(phase);
    phase.setPlanner(this);
    phase.lazyInit();
  }

  private void execute(TranslationUnitContext ctx) {
    rootNode = ctx;

    // refresh each transformation's state before starting the transformation
    for (var transformation : transformations) {
      transformation.setPlanner(this);
      transformation.resetStateInternal();
    }

    for (var level : executionLevels.values()) {
      var proxyListener = new ProxyParseTreeListener(new ArrayList<>());

      for (var phase : level) {
        phase.setPlanner(this);

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
