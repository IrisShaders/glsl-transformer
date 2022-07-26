package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;

public enum Version implements TokenTyped {
  GL20(GLSLLexer.NR_GL_110, 110),
  GL21(GLSLLexer.NR_GL_120, 120),
  GLES20(GLSLLexer.NR_GLES_100, 100, true),
  GL30(GLSLLexer.NR_GL_130, 130),
  GL31(GLSLLexer.NR_GL_140, 140),
  GL32(GLSLLexer.NR_GL_150, 150),
  GL33(GLSLLexer.NR_GL_330, 330),
  GLES30(GLSLLexer.NR_GLES_300, 300, true),
  GLES31(GLSLLexer.NR_GLES_310, 310, true),
  GLES32(GLSLLexer.NR_GLES_320, 320, true),
  GL40(GLSLLexer.NR_GL_400, 400),
  GL41(GLSLLexer.NR_GL_410, 410),
  GL42(GLSLLexer.NR_GL_420, 420),
  GL43(GLSLLexer.NR_GL_430, 430),
  GL44(GLSLLexer.NR_GL_440, 440),
  GL45(GLSLLexer.NR_GL_450, 450),
  GL46(GLSLLexer.NR_GL_460, 460);

  public static Version latest = GL46;

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
