package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Transformation {
  public class SemanticException extends RuntimeException {
    public ParserRuleContext node;

    public SemanticException() {
    }

    public SemanticException(String message) {
      super(message);
    }

    public SemanticException(String message, ParserRuleContext node) {
      this(message);
      this.node = node;
    }
  }

  private PhaseCollector collector;
  private int phaseCounter = 0;

  void setCollector(PhaseCollector collector) {
    this.collector = collector;
  }

  protected void addPhase(Phase phase) {
    collector.addPhaseAt(phase, phaseCounter++);
  }

  protected void resetState() {
  };

  protected abstract void createPhases();
}
