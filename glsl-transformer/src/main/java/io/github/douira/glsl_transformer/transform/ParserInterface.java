package io.github.douira.glsl_transformer.transform;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.transform.EnhancedParser.ParsingStrategy;

public interface ParserInterface {
  GLSLLexer getLexer();

  GLSLParser getParser();

  EnhancedParser getInternalParser();

  void setParsingStrategy(ParsingStrategy parsingStrategy);

  void setSLLOnly();

  void setLLOnly();
}
