package io.github.douira.glsl_transformer.ast.transform;

import java.util.EnumMap;
import java.util.function.*;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;

public class EnumASTTransformer<T extends JobParameters, E extends Enum<E>>
    extends GroupedASTTransformer<T, E, EnumMap<E, String>, EnumMap<E, TranslationUnit>> {

  public EnumASTTransformer(Consumer<EnumMap<E, TranslationUnit>> transformation, Class<E> enumClass) {
    super(transformation, () -> new EnumMap<>(enumClass), () -> new EnumMap<>(enumClass));
  }

  public EnumASTTransformer(Class<E> enumClass) {
    super(() -> new EnumMap<>(enumClass), () -> new EnumMap<>(enumClass));
  }

  @Override
  public void setResultMapSupplier(Supplier<EnumMap<E, String>> resultMapSupplier) {
    throw new UnsupportedOperationException("The enum map suppliers may not be changed.");
  }

  @Override
  public void setTuMapSupplier(Supplier<EnumMap<E, TranslationUnit>> tuMapSupplier) {
    throw new UnsupportedOperationException("The enum map suppliers may not be changed.");
  }
}
