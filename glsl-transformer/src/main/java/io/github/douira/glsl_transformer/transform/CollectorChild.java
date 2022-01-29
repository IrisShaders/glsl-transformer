package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

import io.github.douira.glsl_transformer.GLSLParser.TranslationUnitContext;

/**
 * Implemented by classes that have a phase collector parent and can receive job
 * parameters.
 */
public interface CollectorChild<T> {
  /**
   * Sets the parent collector of this child.
   * 
   * @param collector The phase collector to set as the parent
   */
  void setCollector(PhaseCollector<T> collector);

  /**
   * Returns the phase collector set on this child.
   * 
   * @return The currently set phase collector
   */
  PhaseCollector<T> getCollector();

  /**
   * Returns the executing phase collector's parser.
   * 
   * @return The parser
   */
  default public Parser getParser() {
    return getCollector().getParser();
  }

  /**
   * Returns the executing phase collector's lexer.
   * 
   * @return The lexer
   */
  default public Lexer getLexer() {
    return getCollector().getLexer();
  }

  /**
   * Returns the root node taken from the phase collector that is currently
   * executing this phase.
   * 
   * @return The root node of the current executing phase collector
   */
  default public TranslationUnitContext getRootNode() {
    return getCollector().getRootNode();
  }

  /**
   * Returns the phase collector's current job parameters.
   * 
   * @see io.github.douira.glsl_transformer.transform.PhaseCollector#getJobParameters()
   * 
   * @return The phase collector's current job parameters
   */
  default public T getJobParameters() {
    return getCollector().getJobParameters();
  }
}
