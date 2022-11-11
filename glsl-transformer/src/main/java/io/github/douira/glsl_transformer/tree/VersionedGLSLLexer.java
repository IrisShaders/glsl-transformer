package io.github.douira.glsl_transformer.tree;

import org.antlr.v4.runtime.*;

import io.github.douira.glsl_transformer.ast.node.Version;

/**
 * Provides utility methods for the Lexer to only recognize tokens allowed in
 * the current context, version, profile, extension setting, ES/non-ES mode,
 * Vulkan/non-Vulkan mode. For more information, see
 * https://github.com/KhronosGroup/glslang/blob/master/glslang/MachineIndependent/Scan.cpp}
 * and
 * https://github.com/KhronosGroup/glslang/blob/master/glslang/MachineIndependent/Versions.cpp.
 */
public abstract class VersionedGLSLLexer extends Lexer {
  public Version version = Version.latest;
  public boolean enableCustomDirective = false;
  public boolean enableIncludeDirective = false;
  // public Profile profile;
  // public EnumSet<Extension> extensions;
  // public boolean vulkan = true;

  public VersionedGLSLLexer(CharStream input) {
    super(input);
  }

  public VersionedGLSLLexer() {
  }

  protected boolean isAfter(int atLeast) {
    return version.number >= atLeast;
  }
}
