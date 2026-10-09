package io.github.douira.glsl_transformer.ast.typing;

import java.util.*;

import io.github.douira.glsl_transformer.ast.node.*;
import io.github.douira.glsl_transformer.ast.node.abstract_node.ASTNode;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective;
import io.github.douira.glsl_transformer.ast.node.external_declaration.ExtensionDirective.ExtensionBehavior;

/**
 * The environment a shader is typed in. It determines which implicit
 * conversions are allowed and which builtin functions and variables are
 * available.
 *
 * @param version    the GLSL version
 * @param profile    the profile, never null. If null is given, the default
 *                   profile of the version is used. ES versions always have
 *                   the ES profile.
 * @param stage      the shader stage, or null if the stage is not known, in
 *                   which case the builtins of all stages are available
 * @param extensions the names of the enabled extensions. The name "all"
 *                   enables every extension.
 */
public record TypeEnvironment(Version version, Profile profile, ShaderStage stage, Set<String> extensions) {
  /**
   * The name that stands for all extensions in extension directives.
   */
  public static final String ALL_EXTENSIONS = "all";

  /**
   * The latest GLSL version with the compatibility profile and no specific
   * stage. This environment is used for trees that are not translation units.
   */
  public static final TypeEnvironment LATEST = new TypeEnvironment(
      Version.latest, Profile.COMPATIBILITY, null, Set.of());

  public TypeEnvironment {
    Objects.requireNonNull(version, "The version may not be null");
    profile = version.es ? Profile.ES
        : profile != null ? profile
            : version.number >= 150 ? Profile.CORE : Profile.COMPATIBILITY;
    extensions = Set.copyOf(extensions);
  }

  public TypeEnvironment(Version version, Profile profile) {
    this(version, profile, null, Set.of());
  }

  /**
   * Derives the environment from a tree. For translation units the version
   * statement and the extension directives are used. As the GLSL specification
   * requires, a translation unit without a version statement has version 1.10.
   * All other trees use {@link #LATEST}.
   * <p>
   * Extension directives are not treated positionally: an extension that is
   * enabled anywhere in the translation unit is enabled for all of it, unless
   * a later directive disables it again. A directive for the name
   * {@link #ALL_EXTENSIONS} enables all extensions, or disables all extensions
   * including those that were enabled by name.
   *
   * @param top the top node of the tree
   * @return the environment of the tree
   */
  public static TypeEnvironment of(ASTNode top) {
    if (!(top instanceof TranslationUnit translationUnit)) {
      return LATEST;
    }
    var versionStatement = translationUnit.getVersionStatement();
    var version = versionStatement == null ? Version.GLSL11 : versionStatement.version;
    var profile = versionStatement == null ? null : versionStatement.profile;
    var extensions = new HashSet<String>();
    for (var child : translationUnit.getChildren()) {
      if (child instanceof ExtensionDirective directive) {
        if (directive.behavior != ExtensionBehavior.DISABLE) {
          extensions.add(directive.getName());
        } else if (directive.getName().equals(ALL_EXTENSIONS)) {
          // disabling all extensions also disables those that were enabled by name
          extensions.clear();
        } else {
          extensions.remove(directive.getName());
        }

      }
    }
    return new TypeEnvironment(version, profile, null, extensions);
  }

  public TypeEnvironment withStage(ShaderStage stage) {
    return new TypeEnvironment(version, profile, stage, extensions);
  }

  public TypeEnvironment withExtensions(Collection<String> extensions) {
    return new TypeEnvironment(version, profile, stage, Set.copyOf(extensions));
  }

  public boolean isES() {
    return version.es;
  }

  /**
   * Checks if this environment has at least the given version. Desktop and ES
   * versions are numbered independently.
   *
   * @param desktopVersion the minimum desktop version number, 0 if the checked
   *                       feature is never available on desktop
   * @param esVersion      the minimum ES version number, 0 if the checked
   *                       feature is never available in ES
   * @return true if the version of this environment is high enough
   */
  public boolean isAtLeast(int desktopVersion, int esVersion) {
    var minimum = version.es ? esVersion : desktopVersion;
    return minimum != 0 && version.number >= minimum;
  }

  public boolean hasExtension(String name) {
    return extensions.contains(name) || extensions.contains(ALL_EXTENSIONS);
  }
}
