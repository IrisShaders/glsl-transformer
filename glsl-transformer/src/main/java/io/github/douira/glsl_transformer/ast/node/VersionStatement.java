package io.github.douira.glsl_transformer.ast.node;

import org.antlr.v4.runtime.Token;

import io.github.douira.glsl_transformer.GLSLLexer;
import io.github.douira.glsl_transformer.GLSLParser.VersionStatementContext;
import io.github.douira.glsl_transformer.ast.*;

public class VersionStatement extends ASTNode {
  public int version;
  public Profile profile;

  public enum Profile {
    CORE,
    COMPATIBILITY,
    ES;

    public static Profile fromToken(Token token) {
      switch (token.getType()) {
        case GLSLLexer.NR_CORE:
          return CORE;
        case GLSLLexer.NR_COMPATABILITY:
          return COMPATIBILITY;
        case GLSLLexer.NR_ES:
          return ES;
        default:
          throw new IllegalArgumentException("Unknown profile: " + token.getText());
      }
    }
  }

  public VersionStatement(int version, Profile profile) {
    this.version = version;
    this.profile = profile;
  }

  public VersionStatement(int version) {
    this(version, Profile.CORE);
  }

  public static VersionStatement from(VersionStatementContext node) {
    var version = Integer.parseInt(node.version.getText());
    return node.profile == null
        ? new VersionStatement(version)
        : new VersionStatement(version, Profile.valueOf(node.profile.getText()));
  }

  @Override
  public <R> R accept(ASTVisitor<R> visitor) {
    return visitor.visitVersionStatement(this);
  }
}
