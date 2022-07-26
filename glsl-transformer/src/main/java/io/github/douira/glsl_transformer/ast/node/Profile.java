package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;

public enum Profile implements TokenTyped {
  CORE(GLSLLexer.NR_CORE),
  COMPATIBILITY(GLSLLexer.NR_COMPATIBILITY),
  ES(GLSLLexer.NR_ES);

  public final int tokenType;

  private Profile(int tokenType) {
    this.tokenType = tokenType;
  }

  @Override
  public int getTokenType() {
    return tokenType;
  }

  public static Profile fromToken(Token token) {
    return TypeUtil.enumFromToken(Profile.values(), token);
  }
}
