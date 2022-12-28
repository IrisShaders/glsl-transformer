package io.github.douira.glsl_transformer.ast.transform;

import org.antlr.v4.runtime.RecognitionException;

public interface ParameterizedTransformer<J, V> extends ParameterHolder<J>, Transformer<V> {
  default V transform(V str, J parameters) throws RecognitionException {
    return withJobParameters(parameters, () -> transform(str));
  }
}
