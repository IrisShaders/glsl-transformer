package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.*;

public class VersionStatement extends ASTNode {
  public int version;
  public Profile profile;

  public enum Profile {
    CORE,
    COMPATIBILITY,
    ES
  }

  @Override
  public <R> R accept(ASTVisitor<? extends R> visitor) {
    return visitor.visitVersionStatement(this);
  }
}
