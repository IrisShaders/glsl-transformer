package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.*;

public interface Transformer<T> {
  default String transform(String str) throws RecognitionException {
    return transform(str, null);
  }

  default String transform(String str, T parameters) throws RecognitionException {
    return transformStream(CharStreams.fromString(str), parameters);
  }

  default String transformStream(IntStream charStream) throws RecognitionException {
    return transformStream(charStream, null);
  }

  String transformStream(IntStream charStream, T parameters) throws RecognitionException;
}
