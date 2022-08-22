package io.github.douira.glsl_transformer.ast.node;

import io.github.douira.glsl_transformer.ast.node.basic.ASTNode;
import io.github.douira.glsl_transformer.ast.query.Root;
import io.github.douira.glsl_transformer.ast.traversal.ASTVisitor;

/**
 * The version statement holds information about the selected GLSL version and
 * profile. This class' {@link Version} and {@link Profile} enums are declared
 * outside of this class for easier use in the lexer.
 */
public class VersionStatement extends ASTNode {
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

  @Override
  public VersionStatement clone() {
    return (VersionStatement) super.clone();
  }

  @Override
  public VersionStatement cloneInto(Root root) {
    return (VersionStatement) super.cloneInto(root);
  }

  @Override
  public VersionStatement cloneSeparate() {
    return (VersionStatement) super.cloneSeparate();
  }
}
