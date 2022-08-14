package io.github.douira.glsl_transformer.basic;

import org.antlr.v4.runtime.RecognitionException;

/**
 * A transformer takes a string, does something with it and returns a string.
 * It's similar to a {@code Passthrough<String>}.
 */
public interface Transformer<V> {
  V transform(V str) throws RecognitionException;
}
