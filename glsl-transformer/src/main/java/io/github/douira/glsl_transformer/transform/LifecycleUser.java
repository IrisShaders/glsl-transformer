package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * Implemented by classes that have a execution planner parent and can receive
 * job parameters. All lifecycle users are notified of init and reset events as
 * well as job parameters by the execution planner.
 */
public interface LifecycleUser<T> {
  /**
   * Sets the parent planner of this child.
   * 
   * @param planner The execution planner to set as the parent
   */
  public void setPlanner(ExecutionPlanner<T> planner);

  /**
   * Returns the execution planner set on this child.
   * 
   * @return The currently set execution planner
   */
  public ExecutionPlanner<T> getPlanner();

  /**
   * Returns the executing execution planner's parser.
   * 
   * @return The parser
   */
  default public Parser getParser() {
    return getPlanner().getParser();
  }

  /**
   * Returns the executing execution planner's lexer.
   * 
   * @return The lexer
   */
  default public Lexer getLexer() {
    return getPlanner().getLexer();
  }

  /**
   * Returns the root node taken from the execution planner that is currently
   * executing this phase.
   * 
   * @return The root node of the current executing execution planner
   */
  default public TranslationUnitContext getRootNode() {
    return getPlanner().getRootNode();
  }

  /**
   * Returns the execution planner's current job parameters.
   * 
   * @see io.github.douira.glsl_transformer.transform.ExecutionPlanner#getJobParameters()
   * 
   * @return The execution planner's current job parameters
   */
  default public T getJobParameters() {
    return getPlanner().getJobParameters();
  }

  /**
   * Called when this object is set up in a processing environment like the
   * printer or the execution planner.
   */
  default public void init() {
  }

  /**
   * Called before this object is used on a job. This is called before each
   * transformation/printing job.
   */
  default public void resetState() {
  }
}
