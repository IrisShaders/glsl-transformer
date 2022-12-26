package io.github.douira.glsl_transformer.basic;

import io.github.douira.glsl_transformer.*;
import io.github.douira.glsl_transformer.basic.EnhancedParser.ParsingStrategy;
import io.github.douira.glsl_transformer.token_filter.TokenFilter;

/**
 * The parser interface provides the basic methods for interfacing with an
 * object that contains an {@link EnhancedParser}.
 */
public interface ParserInterface {
  GLSLLexer getLexer();

  GLSLParser getParser();

  void setThrowParseErrors(boolean throwParseErrors);

  void setParsingStrategy(ParsingStrategy parsingStrategy);

  void setSLLOnly();

  void setLLOnly();

  void setTokenFilter(TokenFilter<?> parseTokenFilter);
}
