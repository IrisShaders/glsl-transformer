package io.github.douira.glsl_transformer.basic;

import org.antlr.v4.runtime.*;

public interface Transformer {
  default String transform(String str) throws RecognitionException {
    return transformStream(CharStreams.fromString(str));
  }

  String transformStream(IntStream charStream) throws RecognitionException;
}
