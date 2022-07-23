package io.github.douira.glsl_transformer.basic;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;

/**
 * The parser interface provides the basic methods for interfacing with an
 * object that contains an {@link EnhancedParser}.
 */
public interface ParserInterface {
  GLSLLexer getLexer();

  GLSLParser getParser();

  EnhancedParser getInternalParser();

  void setParsingStrategy(ParsingStrategy parsingStrategy);

  void setSLLOnly();

  void setLLOnly();
}
