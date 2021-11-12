package me.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import me.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import me.douira.glsl_transformer.generic.EditContext;
import me.douira.glsl_transformer.generic.ProxyParseTreeListener;

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
  }

  private void execute(TranslationUnitContext ctx) {
    // refresh each transformation's state before starting the transformation
    for (var transformation : transformations) {
      transformation.initState();
    }

    for (var level : phases) {
      // for some reason passing level directly gives a type error
      var proxyListener = new ProxyParseTreeListener(new ArrayList<>());

      for (var phase : level) {
        if (phase.doWalk()) {
          proxyListener.add(phase);
        }
        phase.beforeWalk(ctx);
      }

      ParseTreeWalker.DEFAULT.walk(proxyListener, ctx);

      for (var phase : level) {
        phase.afterWalk(ctx);
      }
    }
  }

  public EditContext transformTree(TranslationUnitContext ctx) {
    transformTree(ctx, new EditContext());
    return editContext;
  }

  public void transformTree(TranslationUnitContext ctx, EditContext editContext) {
    this.editContext = editContext;
    execute(ctx);
    editContext.finishEditing();
  }
}
