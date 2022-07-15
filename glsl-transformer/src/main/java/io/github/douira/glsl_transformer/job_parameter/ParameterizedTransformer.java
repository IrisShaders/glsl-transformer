package io.github.douira.glsl_transformer.job_parameter;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.basic.Transformer;

public interface ParameterizedTransformer<T> extends Transformer, ParameterHolder<T> {
  String transformBare(String str) throws RecognitionException;

  default String transform(String str, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transformBare(str));
  }

  @Override
  default String transform(String str) throws RecognitionException {
    return transform(str, null);
  }
}
