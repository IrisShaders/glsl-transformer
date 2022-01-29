package io.github.douira.glsl_transformer.transform;

/**
 * This implementation of a collector child may be used if a class can be
 * extended. A custom implementation should be considered if another class needs
 * to be extended. An instance of this class could also be held as a "trait" and
 * accessed through delegate methods.
 */
public class CollectorChildImpl<T> implements CollectorChild<T> {
  private PhaseCollector<T> collector;

  @Override
  public PhaseCollector<T> getCollector() {
    return collector;
  }

  @Override
  public void setCollector(PhaseCollector<T> collector) {
    this.collector = collector;
  }
}
