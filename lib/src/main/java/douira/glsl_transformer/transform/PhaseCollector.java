package douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import douira.glsl_transformer.generic.EditContext;
import douira.glsl_transformer.generic.ProxyParseTreeListener;

/**
 * PhaseCollector is meant to be instantiated once and contains all the
 * transformations and phases for a specific use case.
 */
public class PhaseCollector {
  protected Parser parser;
  protected EditContext editContext;
  private List<List<Phase>> phases = new ArrayList<>();
  private Collection<Transformation> transformations = new ArrayList<>();

  public PhaseCollector(Parser parser) {
    this.parser = parser;
  }

  public void registerTransformation(Transformation transformation) {
    transformation.setCollector(this);
    transformation.createPhases();
    transformations.add(transformation);
  }

  public void registerTransformationMultiple(Consumer<PhaseCollector> groupRegisterer) {
    groupRegisterer.accept(this);
  }

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

  public EditContext transformTree(TranslationUnitContext ctx, BufferedTokenStream tokenStream) {
    transformTree(ctx, new EditContext(ctx, tokenStream));
    return editContext;
  }

  public void transformTree(TranslationUnitContext ctx, EditContext editContext) {
    this.editContext = editContext;
    execute(ctx);
    editContext.finishEditing();
  }
}
