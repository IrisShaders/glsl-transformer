package io.github.douira.glsl_transformer.job_parameter;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.basic.Transformer;

public interface ParameterizedTransformer<T, V> extends Transformer<V>, ParameterHolder<T> {
  default V transform(V str, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transform(str));
  }
}
