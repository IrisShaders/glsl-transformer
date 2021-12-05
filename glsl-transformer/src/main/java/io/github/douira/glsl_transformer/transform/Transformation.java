package io.github.douira.glsl_transformer.transform;

import io.github.douira.glsl_transformer.generic.ExtendedContext;

public abstract class Transformation {
  public class SemanticException extends RuntimeException {
    public ExtendedContext node;

    public SemanticException() {
    }

    public SemanticException(String message) {
      super(message);
    }

    public SemanticException(String message, ExtendedContext node) {
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
    addPhase(phase, phaseCounter++);
  }

  protected void addPhase(Phase phase, int index) {
    collector.addPhaseAt(phase, phaseCounter++);
  }

  /**
   * This method is called by the phase collector each time a tree is transformed
   * in order to reset or initialize the state of the transformation if it has
   * any.
   */
  protected void resetState() {
  };

  /**
   * This method should create phases and add them with {@link #addPhase(Phase)}
   * or {@link #addPhase(Phase, int)}. Phases are typically implemented by
   * creating anonymous classes within the implementation of this method.
   */
  protected abstract void createPhases();
}
