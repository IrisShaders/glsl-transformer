package io.github.douira.glsl_transformer.transform;

/**
 * This implementation of a lifecycle user may be used if a class can be
 * extended. A custom implementation should be considered if another class needs
 * to be extended. An instance of this class could also be held as a "trait" and
 * accessed through delegate methods.
 */
public class LifecycleUserImpl<T extends JobParameters> implements LifecycleUser<T> {
  private ExecutionPlanner<T> planner;
  private boolean initialized = false;

  @Override
  public ExecutionPlanner<T> getPlanner() {
    return planner;
  }

  @Override
  public void setPlanner(ExecutionPlanner<T> planner) {
    this.planner = planner;
  }

  @Override
  public boolean isInitialized() {
    return initialized;
  }

  @Override
  public void setInitialized() {
    initialized = true;
  }
}
