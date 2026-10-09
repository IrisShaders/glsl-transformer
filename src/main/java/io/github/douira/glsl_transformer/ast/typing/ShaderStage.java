package io.github.douira.glsl_transformer.ast.typing;

import java.util.Locale;

/**
 * The shader stages. The stage determines which builtin variables and
 * functions are available. An AST does not know which stage it is for, the
 * stage has to be provided through the {@link TypeEnvironment}.
 */
public enum ShaderStage {
  VERTEX("vert"),
  TESSELLATION_CONTROL("tesc"),
  TESSELLATION_EVALUATION("tese"),
  GEOMETRY("geom"),
  FRAGMENT("frag"),
  COMPUTE("comp"),
  RAY_GENERATION("rgen"),
  INTERSECTION("rint"),
  ANY_HIT("rahit"),
  CLOSEST_HIT("rchit"),
  MISS("rmiss"),
  CALLABLE("rcall"),
  TASK("task"),
  MESH("mesh");

  /**
   * The conventional file extension for shaders of this stage, which is also
   * the name of the stage in the builtin definition files.
   */
  public final String extension;

  ShaderStage(String extension) {
    this.extension = extension;
  }

  /**
   * Returns the stage for a conventional file extension like "vert" or "frag".
   *
   * @param extension the file extension without the dot, case-insensitive
   * @return the stage, or null if the extension does not denote a stage
   */
  public static ShaderStage fromExtension(String extension) {
    var normalized = extension.toLowerCase(Locale.ROOT);
    for (var stage : values()) {
      if (stage.extension.equals(normalized)) {
        return stage;
      }
    }
    return null;
  }
}
