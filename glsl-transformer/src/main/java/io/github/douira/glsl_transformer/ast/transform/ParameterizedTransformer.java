package io.github.douira.glsl_transformer.ast.transform;

import org.antlr.v4.runtime.RecognitionException;

public interface ParameterizedTransformer<T, V> extends ParameterHolder<T>, Transformer<V> {
  default V transform(V str, T parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transform(str));
  }
}
