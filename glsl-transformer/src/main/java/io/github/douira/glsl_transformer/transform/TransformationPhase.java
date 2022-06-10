package io.github.douira.glsl_transformer.transform;

import java.util.*;
import java.util.function.Supplier;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;
import io.github.douira.glsl_transformer.traversal.PartialParseTreeListener;
import io.github.douira.glsl_transformer.tree.ExtendedContext;

/**
 * The transformations phase is the smallest unit of the transformation process.
 * Its subclasses contain the specific code to make changes to the parse tree.
 */
public abstract class TransformationPhase<T extends JobParameters>
    extends TransformationPhaseBase<T>
    implements PartialParseTreeListener {
  private boolean walkFinishedNotified = false;

  /**
   * If not null, the transformation phase will only walk into a rule context's
   * children if it's an instance of these given classes.
   */
  protected Set<Class<? extends ExtendedContext>> walkIntoRules;

  /**
   * Limits how deep into the parse tree this transformation phase will walk
   * before it signals deep enough.
   */
  protected int maximumWalkDepth = -1;

  /**
   * Called during planning in order to determine if this phase does any
   * walking at all or if it just runs some code, like a RunPhase. This doesn't
   * exclude or include this phase from walking but rather helps the execution
   * planner combine walk phases into execution levels. Enabling and disabling a
   * phase should be done with the methods
   * {@link #checkBeforeWalk(TranslationUnitContext)} and
   * {@link #runAfterWalk(TranslationUnitContext)}.
   * 
   * @implNote This method should run quickly and will only be called once (or
   *           never) during execution planning.
   * 
   * @return If this phase needs to be walked on the tree
   */
  protected abstract boolean canWalk();

  /**
   * Method called by the execution planner before the walk happens. The returned
   * boolean determines if the phase is added to the list of phases that are
   * walked on the tree. Returns false by default and implementing classes should
   * overwrite this.
   * 
   * @param ctx The root node
   * @return {@code true} if the phase should be walked on the tree
   */
  protected boolean checkBeforeWalk(TranslationUnitContext ctx) {
    return false;
  }

  /**
   * Method called by the execution planner after the walk happens. Does nothing
   * by default.
   * 
   * @param ctx The root node
   */
  protected void runAfterWalk(TranslationUnitContext ctx) {
  }

  @Override
  public TransformationPhase<T> activation(Supplier<Boolean> activation) {
    super.activation(activation);
    return this;
  }

  /**
   * Marks this phase as being done walking the tree in the current execution.
   * This removes it from the proxy parse tree listener which in turn can make the
   * dynamic parse tree walker not further walk the parse tree if there are no
   * more listeners that are interested in continuing.
   * 
   * @apiNote Calling this method multiple times in the same execution has no
   *          effect but is efficient
   */
  protected void walkFinished() {
    if (walkFinishedNotified) {
      return;
    }

    walkFinishedNotified = true;
    getPlanner().removeCurrentPhaseFromWalk();
  }

  void resetWalkFinishState() {
    walkFinishedNotified = false;
  }

  @Override
  public boolean isDeepEnough(ExtendedContext node, int depth) {
    return maximumWalkDepth != -1 && depth >= maximumWalkDepth
        || walkIntoRules != null && !walkIntoRules.contains(node.getClass());
  }

  /**
   * Sets the class of rules that this phase should walk into. It will signal
   * "deep enough" to the parse tree walker for all other nodes.
   * 
   * @param walkIntoRules The class of rules that this phase should walk into
   */
  public void setWalkIntoRules(Set<Class<? extends ExtendedContext>> walkIntoRules) {
    this.walkIntoRules = walkIntoRules;
  }

  /**
   * Adds a class to the set of allowed rules for the walk.
   * 
   * @param walkIntoRule The class of a rule to allow deeper walking into
   */
  public void addWalkIntoRule(Class<? extends ExtendedContext> walkIntoRule) {
    if (walkIntoRules == null) {
      walkIntoRules = new HashSet<>();
    }
    walkIntoRules.add(walkIntoRule);
  }

  /**
   * Sets the maximum walk depth.
   * @param maximumWalkDepth The maximum walk depth
   */
  public void setMaximumWalkDepth(int maximumWalkDepth) {
    this.maximumWalkDepth = maximumWalkDepth;
  }

  /**
   * Sets the maximum walk depth to -1 which means it's not limited.
   */
  public void unlimitedWalkDepth() {
    maximumWalkDepth = Integer.MAX_VALUE;
  }
}
