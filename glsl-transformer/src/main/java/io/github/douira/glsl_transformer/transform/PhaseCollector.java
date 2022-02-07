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
import io.github.douira.glsl_transformer.transform.Transformation.PhaseEntry;
import io.github.douira.glsl_transformer.util.ComparablePair;

/**
 * The phase collector holds the registered transformations and manages their
 * execution. After creating an instance, transformations should be registered
 * with it. During registration the phase collector organizes the phases each
 * transformation contributes. In order to reduce the number of times the tree
 * has to be walked, it processes all walk phases of each level at the same
 * time. A level of phases consists of all phases that were added to their
 * transformation at the same index.
 */
public abstract class PhaseCollector<T> {
  private final Map<ComparablePair<Integer, Integer>, List<TransformationPhase<T>>> executionLevels = new TreeMap<>();
  private final Collection<Transformation<T>> transformations = new ArrayList<>();
  private TranslationUnitContext rootNode;

  /**
   * Returns this phase collector's parser. How the parser is stored is up to the
   * implementing class.
   * 
   * @return The parser
   */
  public abstract GLSLParser getParser();

  /**
   * Returns the phase collector's lexer.
   * 
   * @return The lexer
   */
  public abstract GLSLLexer getLexer();

  /**
   * Returns the phase collector's current job parameters. This may be null if the
   * transformation manager's caller decides not to pass job parameters. However,
   * a convention to always pass valid job parameters (whatever that may be) could
   * be established if they are required for transformation phases to function.
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
   * Registers a single transformation with this phase collector. When the phase
   * collector transforms a tree, the phases contributed by this transformation
   * will be run.
   * 
   * @param transformation The transformation to collect the phases from
   */
  public void registerTransformation(Transformation<T> transformation) {
    transformation.addPhasesTo(this);
    transformations.add(transformation);
  }

  /**
   * Registers multiple transformations by calling a function that consumes a
   * phase collector. This can be used together with transformation groups by
   * having them register many transformations with a phase collector.
   * 
   * @see #registerTransformation(Transformation)
   * 
   * @param groupRegisterer The function that registers transformations on the
   *                        phase collector it is given
   */
  public void registerTransformationMultiple(Consumer<PhaseCollector<T>> groupRegisterer) {
    groupRegisterer.accept(this);
  }

  /**
   * Adds a single phase entry and inserts it at its position within the list of
   * execution levels and phases within levels.
   * 
   * @param entry The entry containing the index, group index and phase to be
   *              added
   */
  void addPhaseAt(PhaseEntry<T> entry) {
    var phase = entry.phase();
    var indexPair = new ComparablePair<>(entry.group(), entry.index());
    var phasesForIndex = executionLevels.get(indexPair);
    if (phasesForIndex == null) {
      phasesForIndex = new ArrayList<>();
      executionLevels.put(indexPair, phasesForIndex);
    }
    phasesForIndex.add(phase);
    phase.setCollector(this);
    phase.lazyInit();
  }

  private void execute(TranslationUnitContext ctx) {
    rootNode = ctx;

    // refresh each transformation's state before starting the transformation
    for (var transformation : transformations) {
      transformation.setCollector(this);
      transformation.resetStateInternal();
    }

    for (var level : executionLevels.values()) {
      var proxyListener = new ProxyParseTreeListener(new ArrayList<>());

      for (var phase : level) {
        phase.setCollector(this);

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
