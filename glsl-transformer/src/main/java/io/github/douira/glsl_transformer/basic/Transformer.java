package io.github.douira.glsl_transformer.basic;

import org.antlr.v4.runtime.*;

public interface Transformer {
  String transform(String str) throws RecognitionException;
}
