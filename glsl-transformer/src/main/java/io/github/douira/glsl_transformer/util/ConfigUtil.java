package io.github.douira.glsl_transformer.util;

import java.util.function.Supplier;

public class ConfigUtil {
  public static <V> V withDefault(V setValue, Supplier<V> defaultGenerator) {
    return setValue == null ? withDefault(setValue, defaultGenerator.get()) : setValue;
  }

  public static <V> V withDefault(Supplier<V> setValueSupplier, V defaultValue) {
    return setValueSupplier == null ? withDefault((V) null, defaultValue) : setValueSupplier.get();
  }

  public static <V> V withDefault(V setValue, V defaultValue) {
    if (setValue == null) {
      if (defaultValue == null) {
        throw new AssertionError("Generated default value is null!");
      }
      return defaultValue;
    } else {
      return setValue;
    }
  }
}
