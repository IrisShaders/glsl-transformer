package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.ast.data.*;
import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

public class VersionStatement extends ASTNode {
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

    public final int tokenType;
    public final int version;
    public final boolean es;

    private Version(int tokenType, int version, boolean es) {
      this.tokenType = tokenType;
      this.version = version;
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
  }

  public Version version;
  public Profile profile; // TODO: nullable

  public VersionStatement(Version version, Profile profile) {
    this.version = version;
    this.profile = profile;
  }

  public VersionStatement(Version version) {
    this(version, Profile.CORE);
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitVersionStatement(this);
  }
}
