package io.github.douira.glsl_transformer.core;

import java.util.Collection;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.core.target.HandlerTarget;

/**
 * This class extends the search terminals implementation by adding at method
 * {@link #getTargetsDynamic()} that returns the list of targets to search for
 * dynamically but is only called if the targets are every actually needed. The
 * targets are generated each time the phase is run.
 */
public abstract class SearchTerminalsDynamic<T> extends SearchTerminalsImpl<T> {
  /**
   * Creates a new dynamic search terminals phase.
   */
  public SearchTerminalsDynamic() {
    super((Collection<HandlerTarget<T>>) null);
  }

  /**
   * Returns the collection of targets to search for. This method is called at
   * most once per run of this phase.
   * 
   * @see SearchTerminals#getTargets()
   * 
   * @return The targets to search for
   */
  protected abstract Collection<HandlerTarget<T>> getTargetsDynamic();

  @Override
  protected void beforeWalk(TranslationUnitContext ctx) {
    targets = null;
  }

  @Override
  protected boolean isActiveBeforeWalk() {
    return true;
  }

  @Override
  public Collection<HandlerTarget<T>> getTargets() {
    if (targets == null) {
      targets = getTargetsDynamic();
    }
    return targets;
  }
}
