package me.douira.glsl_transformer.transform;

/**
 * NoWalkPhase can be used when a transformation only has one non-walking phase.
 * In other cases, beforeWalk in a normal phase can be used instead.
 */
public class NoWalkPhase extends Phase {
  @Override
  public boolean doWalk() {
    return false;
  }
}
