package me.douira.glsl_transformer.transform;

public abstract class Transformation {
  private PhaseCollector collector;
  private int phaseCounter = 0;

  void setCollector(PhaseCollector collector) {
    this.collector = collector;
  }

  protected void addPhase(Phase phase) {
    collector.addPhaseAt(phase, phaseCounter++);
  }

  protected abstract void resetState();

  protected abstract void createPhases();
}
