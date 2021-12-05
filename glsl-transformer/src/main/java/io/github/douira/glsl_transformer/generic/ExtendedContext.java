package io.github.douira.glsl_transformer.generic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Implements custom behavior in parse rule contexts. This class is used as the
 * super class for all contexts in the parser.
 * 
 * This class is not meant to be constructed manually but is the base class
 * which ANTLR extends in the generated parser code.
 */
public class ExtendedContext extends ParserRuleContext {
  /**
   * Creates a new extended parser rule context. This is required for the
   * generated parse code to be valid.
   * 
   * @param parent              The parent node
   * @param invokingStateNumber The invoking state number
   */
  public ExtendedContext(ParserRuleContext parent, int invokingStateNumber) {
    super(parent, invokingStateNumber);
  }
}
