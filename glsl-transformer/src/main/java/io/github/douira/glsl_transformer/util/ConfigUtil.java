package io.github.douira.glsl_transformer.util;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Holds utility methods for configuring core transformations and transformation
 * phases.
 */
public class ConfigUtil {
  /**
   * Uses a default generator to return a value if the given set value is null. If
   * the generated value is also null, it throws an error. This is used for
   * writing getter methods in core classes that use the chaining configuration
   * pattern.
   * 
   * @param <R>              The value type
   * @param setValue         The value set for this configuration by the user
   * @param defaultGenerator The default value generator
   * @return The set value or the generated value
   */
  public static <R> R withDefault(R setValue, Supplier<R> defaultGenerator) {
    return setValue == null ? withDefault(setValue, defaultGenerator.get()) : setValue;
  }

  /**
   * Uses a default value if the given value generator is null. If the default
   * value is also null, an error is thrown. This is used for writing getter
   * methods in core classes that use the chaining configuration pattern. Note
   * that nothing special happens if the set value generator returns null.
   * 
   * @param <R>              The value type
   * @param setValueSupplier The value generator set for this configuration by the
   *                         user
   * @param defaultValue     The default value
   * @return The generated value or the default value
   */
  public static <R> R withDefault(Supplier<R> setValueSupplier, R defaultValue) {
    return setValueSupplier == null ? withDefault((R) null, defaultValue) : setValueSupplier.get();
  }

  /**
   * Returns the set value if it is not null and returns the default value
   * otherwise. If the default is also null it throws an error.
   * 
   * @param <R>          The value type
   * @param setValue     The value set for this configuration by the user
   * @param defaultValue The default value
   * @return The set value or the default value
   */
  public static <R> R withDefault(R setValue, R defaultValue) {
    if (setValue == null) {
      Objects.requireNonNull(defaultValue, "Generated default value is null!");
      return defaultValue;
    } else {
      return setValue;
    }
  }
}
