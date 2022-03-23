package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * Implemented by classes that have an execution planner parent and can receive
 * job parameters. All lifecycle users are notified of init and reset events as
 * well as job parameters by the execution planner. {@link #init()} is called
 * the before the first reset while the reset {@link #resetState()} is called
 * for every transformation job. For transformations, state is reset before
 * execution starts. For transformation phases, state is reset before the
 * phase's execution level is executed.
 */
public interface LifecycleUser<T> {
  /**
   * Sets the parent planner of this child.
   * 
   * @param planner The execution planner to set as the parent
   */
  void setPlanner(ExecutionPlanner<T> planner);

  /**
   * Returns the execution planner set on this child.
   * 
   * @return The currently set execution planner
   */
  ExecutionPlanner<T> getPlanner();

  /**
   * Returns the executing execution planner's parser.
   * 
   * @return The parser
   */
  default Parser getParser() {
    return getPlanner().getParser();
  }

  /**
   * Returns the executing execution planner's lexer.
   * 
   * @return The lexer
   */
  default Lexer getLexer() {
    return getPlanner().getLexer();
  }

  /**
   * Returns the root node taken from the execution planner that is currently
   * executing this phase.
   * 
   * @return The root node of the current executing execution planner
   */
  default TranslationUnitContext getRootNode() {
    return getPlanner().getRootNode();
  }

  /**
   * Returns the execution planner's current job parameters.
   * 
   * @see io.github.douira.glsl_transformer.transform.ExecutionPlanner#getJobParameters()
   * 
   * @return The execution planner's current job parameters
   */
  default T getJobParameters() {
    return getPlanner().getJobParameters();
  }

  /**
   * Called when this object is set up in a processing environment like the
   * printer or the execution planner.
   */
  default void init() {
  }

  /**
   * Called before this object is used on a job. This is called before each
   * transformation/printing job.
   */
  default void resetState() {
  }
}
