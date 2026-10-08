package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;

public enum Version implements TokenTyped {
  GLSLES10(GLSLLexer.NR_GLSLES_100, 100, true),
  GLSLES30(GLSLLexer.NR_GLSLES_300, 300, true),
  GLSLES31(GLSLLexer.NR_GLSLES_310, 310, true),
  GLSLES32(GLSLLexer.NR_GLSLES_320, 320, true),
  GLSL11(GLSLLexer.NR_GLSL_110, 110),
  GLSL12(GLSLLexer.NR_GLSL_120, 120),
  GLSL13(GLSLLexer.NR_GLSL_130, 130),
  GLSL14(GLSLLexer.NR_GLSL_140, 140),
  GLSL15(GLSLLexer.NR_GLSL_150, 150),
  GLSL33(GLSLLexer.NR_GLSL_330, 330),
  GLSL40(GLSLLexer.NR_GLSL_400, 400),
  GLSL41(GLSLLexer.NR_GLSL_410, 410),
  GLSL42(GLSLLexer.NR_GLSL_420, 420),
  GLSL43(GLSLLexer.NR_GLSL_430, 430),
  GLSL44(GLSLLexer.NR_GLSL_440, 440),
  GLSL45(GLSLLexer.NR_GLSL_450, 450),
  GLSL46(GLSLLexer.NR_GLSL_460, 460);

  public static Version latest = Version.GLSL46;

  public final int tokenType;
  public final int number;
  public final boolean es;

  private Version(int tokenType, int number, boolean es) {
    this.tokenType = tokenType;
    this.number = number;
    this.es = es;
  }

  private Version(int tokenType, int version) {
    this(tokenType, version, false);
  }

  @Override
  public int getTokenType() {
    return tokenType;
  }

  public static Version fromToken(Token token) {
    return TypeUtil.enumFromToken(Version.values(), token);
  }

  public static Version fromNumber(int number) {
    for (Version version : Version.values()) {
      if (version.number == number) {
        return version;
      }
    }
    throw new IllegalArgumentException("Unknown version: " + number);
  }
}
