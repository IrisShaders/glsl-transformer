package me.douira.glsl_transformer.transform;

public abstract class Transformation {
  private PhaseCollector collector;
  private int phaseCounter = 0;

  public void setCollector(PhaseCollector collector) {
    this.collector = collector;
  }

  public void addPhase(Phase phase) {
    collector.addPhaseAt(phase, phaseCounter++);
  }

  protected abstract void init();
}
