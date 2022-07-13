package io.github.douira.glsl_transformer.transform;

import org.antlr.v4.runtime.*;

public interface ParameterizedTransformer<T> extends Transformer<T>, ParameterHolder<T> {
  String transformStreamBare(IntStream charStream) throws RecognitionException;

  @Override
  default String transformStream(IntStream charStream, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transformStreamBare(charStream));
  }
}
