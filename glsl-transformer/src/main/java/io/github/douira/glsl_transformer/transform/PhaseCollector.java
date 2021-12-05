package io.github.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.generic.EditContext;
import io.github.douira.glsl_transformer.generic.ProxyParseTreeListener;

/**
 * The phase collector holds the registered transformations and manages their
 * execution. After creating an instance, transformations should be registered
 * with it. During registration the phase collector organizes the phases each
 * transformation contributes. In order to reduce the number of times the tree
 * has to be walked, it processes all walk phases of each level at the same
 * time. A level of phases consists of all phases that were added to their
 * transformation at the same index.
 */
public class PhaseCollector {
  protected EditContext editContext;
  private Parser parser;
  private List<List<Phase>> phases = new ArrayList<>();
  private Collection<Transformation> transformations = new ArrayList<>();

  /**
   * Creates a new phase collector for a parser.
   * 
   * @param parser The parser to create this phase collector for
   */
  public PhaseCollector(Parser parser) {
    this.parser = parser;
  }

  /**
   * Registers a single transformation with this phase collector. When the phase
   * collector transforms a tree, the phases contributed by this transformation
   * will be run.
   * 
   * @param transformation The transformation to collect the phases from
   */
  public void registerTransformation(Transformation transformation) {
    transformation.setCollector(this);
    transformation.createPhases();
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
  public void registerTransformationMultiple(Consumer<PhaseCollector> groupRegisterer) {
    groupRegisterer.accept(this);
  }

  /**
   * Returns this phase collector's parser
   * 
   * @return The parser
   */
  public Parser getParser() {
    return parser;
  }

  /**
   * Collects a phase of a transformation and inserts it at a given level index.
   * During transformations the phases of each level are executed in the order
   * they were added.
   * 
   * @param phase The phase to collect into the specified level
   * @param index The level this phase should be added to
   */
  protected void addPhaseAt(Phase phase, int index) {
    while (phases.size() <= index) {
      phases.add(new ArrayList<>());
    }
    phases.get(index).add(phase);
    phase.setParent(this);
    phase.init();
  }

  private void execute(TranslationUnitContext ctx) {
    // refresh each transformation's state before starting the transformation
    for (var transformation : transformations) {
      transformation.resetState();
    }

    for (var level : phases) {
      var proxyListener = new ProxyParseTreeListener(new ArrayList<>());

      for (var phase : level) {
        if (phase instanceof WalkPhase walkPhase) {
          if (walkPhase.isActiveBeforeWalk()) {
            walkPhase.beforeWalk(ctx);
          }
          if (walkPhase.isActiveAtWalk()) {
            proxyListener.add(walkPhase);
          }
        } else if (phase.isActive()) {
          ((RunPhase) phase).run(ctx);
        }
      }

      if (!proxyListener.isEmpty()) {
        ParseTreeWalker.DEFAULT.walk(proxyListener, ctx);
      }

      for (var phase : level) {
        if (phase instanceof WalkPhase walkPhase) {
          if (walkPhase.isActiveAfterWalk()) {
            walkPhase.afterWalk(ctx);
          }
        }
      }
    }
  }

  /**
   * Transforms the given parse tree with the registered transformations. The
   * generated edit context has to be passed along to the printer in order for the
   * applied transformations to be printed correctly.
   * 
   * @see #transformTree(TranslationUnitContext, EditContext)
   * 
   * @param ctx         The root node of the parse tree to be transformed
   * @param tokenStream The token stream of the parse tree
   * @return An edit context containing the changes made by the applied
   *         transformations
   */
  public EditContext transformTree(TranslationUnitContext ctx, BufferedTokenStream tokenStream) {
    transformTree(ctx, new EditContext(ctx, tokenStream));
    return editContext;
  }

  /**
   * Transforms the given parse tree with the registered transformations recording
   * the changes to the given edit context. The edit context is sealed after
   * transformation and cannot be used in another transformation since all
   * transformations should be performed using a single phase collector.
   * 
   * @param ctx         The root node of the parse tree to be transformed
   * @param editContext The edit context to which the transformation's changes
   *                    should be recorded
   */
  public void transformTree(TranslationUnitContext ctx, EditContext editContext) {
    this.editContext = editContext;
    execute(ctx);
    editContext.finishEditing();
  }
}
