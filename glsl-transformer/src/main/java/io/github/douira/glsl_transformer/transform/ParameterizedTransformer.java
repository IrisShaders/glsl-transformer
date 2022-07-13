package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.*;

public interface ParameterizedTransformer<T> extends Transformer, ParameterHolder<T> {
  String transformStreamBare(IntStream charStream) throws RecognitionException;

  default String transform(String str, T parameters) throws RecognitionException {
    return transformStream(CharStreams.fromString(str), parameters);
  }

  default String transformStream(IntStream charStream, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transformStreamBare(charStream));
  }

  @Override
  default String transform(String str) throws RecognitionException {
    return transform(str, null);
  }

  @Override
  default String transformStream(IntStream charStream) throws RecognitionException {
    return transformStream(charStream, null);
  }
}
