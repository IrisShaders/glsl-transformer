package me.douira.glsl_transformer.transform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import me.douira.glsl_transformer.GLSLParser;
import me.douira.glsl_transformer.generic.EditContext;
import me.douira.glsl_transformer.generic.ProxyParseTreeListener;

public class PhaseCollector {
  private EditContext editContext = new EditContext();
  private List<List<Phase>> phases = new ArrayList<>();

  private PhaseCollector() {
  }

  public void registerTransformation(Transformation transformation) {
    transformation.setCollector(this);
    transformation.init();
  }

  public void registerTransformationMultiple(Consumer<PhaseCollector> groupRegisterer) {
    groupRegisterer.accept(this);
  }

  protected void addPhaseAt(Phase phase, int index) {
    while (phases.size() <= index) {
      phases.add(new LinkedList<>());
    }
    phases.get(index).add(phase);
    phase.setEditContext(editContext);
  }

  private void execute(GLSLParser.TranslationUnitContext TUContext) {
    for (var level : phases) {
      // for some reason passing level directly gives a type error
      var proxyListener = new ProxyParseTreeListener(new LinkedList<>());

      for (var phase : level) {
        proxyListener.add(phase);
        phase.beforeWalk(TUContext);
      }

      ParseTreeWalker.DEFAULT.walk(proxyListener, TUContext);

      for (var phase : level) {
        phase.afterWalk(TUContext);
      }
    }
  }

  public static EditContext transformTree(GLSLParser.TranslationUnitContext TUContext,
      Consumer<PhaseCollector> register) {
    var collector = new PhaseCollector();
    register.accept(collector);
    collector.execute(TUContext);
    collector.editContext.finishEditing();
    return collector.editContext;
  }
}
