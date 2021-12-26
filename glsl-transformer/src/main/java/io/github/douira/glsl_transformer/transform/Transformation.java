package io.github.douira.glsl_transformer.transform;

import java.util.LinkedList;
import java.util.List;

import io.github.douira.glsl_transformer.generic.ExtendedContext;

/**
 * The transformation is the vehicle through which transformation phases, which
 * do all the actual transforming work, are added to the phase collector. It
 * also holds any inter-phase state that may be necessary for a transformation.
 * 
 * Since a transformation is independent of a phase collector since it only
 * contains a list of phases and their indexes, it could be added to multiple
 * phase collectors. However, they can't execute a single transformation in
 * separate threads as that would mess up the transformation's state.
 * 
 * Phases can, however, be added multiple times or to multiple phases. Beware of
 * sharing state between transformations as that could cause issues.
 * 
 * A stateless (no inter-phase state) transformation can be created by simply
 * making an instance of this class and adding transformations to it. If state
 * between phases is needed, make a subclass and add any state as instance
 * fields. Then phases are created and added within the subclass' constructor.
 * There cannot be any state stored as local variables either in the scope that
 * created the {@code Transformation} instance or in a subclass' constructor as
 * it will not be reset if a transformation is run multiple times. In the same
 * vein, state should only be initialized in the {@link #resetState()} method.
 * 
 * TODO: unclear if sharing phases between transformation managers is
 * problematic since then the compiled paths/patterns in phases have a different
 * parser than the one being used for the transformation. Probably it doesn't
 * matter and the parser is just used to figure out how the rules of the
 * tree are.
 */
public class Transformation {
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

  private record PhaseEntry(TransformationPhase phase, int index) {
  };

  private List<PhaseEntry> phaseRegistry = new LinkedList<>();
  private int phaseCounter = 0;

  /**
   * Creates a stateless transformation and adds a single phase to it. If you want
   * to add multiple phases to a transformation, create an instance and call
   * {@link #addPhase(TransformationPhase)} multiple times.
   * 
   * @param phase The only transformation phase to add to a new stateless
   *              transformation
   */
  public Transformation(TransformationPhase phase) {
    addPhase(phase);
  }

  /**
   * Creates a stateless transformation with no transformation phases, which can
   * be added later.
   */
  public Transformation() {
  }

  /**
   * Adds a transformation phase to this transformation. There is purposefully no
   * method that adds a whole array of phases as that would make the code harder
   * to read.
   * 
   * @param phase The transformation phase to append
   */
  public void addPhase(TransformationPhase phase) {
    addPhase(phase, phaseCounter++);
  }

  /**
   * Adds a transformation phase to this transformation at a specific index. The
   * index determines in which order the phases will be executed in relation to
   * other phases within this transformation and within the phase collector.
   * 
   * @param phase The transformation phase to insert
   * @param index The index at which the phase should be executed
   */
  public void addPhase(TransformationPhase phase, int index) {
    phaseRegistry.add(new PhaseEntry(phase, index));
  }

  /**
   * This method is called by the phase collector each time a tree is transformed
   * in order to reset or initialize the state of the transformation if it has
   * any.
   */
  protected void resetState() {
  };

  /**
   * Adds all the stored phases to the given phase collector.
   * 
   * @param collector The phase collector to add the phases to
   */
  void addPhasesTo(PhaseCollector collector) {
    for (var entry : phaseRegistry) {
      collector.addPhaseAt(entry.phase(), entry.index());
    }
  }
}
