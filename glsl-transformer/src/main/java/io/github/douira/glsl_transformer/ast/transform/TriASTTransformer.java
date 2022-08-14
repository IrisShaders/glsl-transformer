package io.github.douira.glsl_transformer.ast.transform;

import java.util.EnumMap;
import java.util.function.Consumer;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.util.*;

public class TriASTTransformer<T extends JobParameters, E extends Enum<E>> extends EnumASTTransformer<T, E> {
  private final E aType, bType, cType;
  private final Class<E> enumClass;

  public TriASTTransformer(
      Consumer<EnumMap<E, TranslationUnit>> transformation,
      Class<E> enumClass,
      E aType,
      E bType,
      E cType) {
    super(transformation, enumClass);
    this.aType = aType;
    this.bType = bType;
    this.cType = cType;
    this.enumClass = enumClass;
  }

  public TriASTTransformer(Class<E> enumClass, E aType, E bType, E cType) {
    super(enumClass);
    this.aType = aType;
    this.bType = bType;
    this.cType = cType;
    this.enumClass = enumClass;
  }

  public TriASTTransformer(
      TriConsumer<TranslationUnit, TranslationUnit, TranslationUnit> transformation,
      Class<E> enumClass,
      E aType,
      E bType,
      E cType) {
    this(enumClass, aType, bType, cType);
    setTransformation(transformation);
  }

  public TriASTTransformer(
      TriRootOnlyTransformation<TranslationUnit> transformation,
      Class<E> enumClass,
      E aType,
      E bType,
      E cType) {
    this(enumClass, aType, bType, cType);
    setTransformation(transformation);
  }

  public TriASTTransformer(
      TriFullTransformation<TranslationUnit, T> transformation,
      Class<E> enumClass,
      E aType,
      E bType,
      E cType) {
    this(enumClass, aType, bType, cType);
    setTransformation(transformation);
  }

  public void setTransformation(TriConsumer<TranslationUnit, TranslationUnit, TranslationUnit> transformation) {
    super.setTransformation(map -> transformation.accept(map.get(aType), map.get(bType), map.get(cType)));
  }

  public void setTransformation(TriRootOnlyTransformation<TranslationUnit> transformation) {
    super.setTransformation(map -> {
      final var a = map.get(aType);
      final var b = map.get(bType);
      final var c = map.get(cType);
      transformation.accept(a, b, c,
          a == null ? null : a.getRoot(),
          b == null ? null : b.getRoot(),
          c == null ? null : c.getRoot());
    });
  }

  public void setTransformation(TriFullTransformation<TranslationUnit, T> transformation) {
    super.setTransformation(map -> {
      final var a = map.get(aType);
      final var b = map.get(bType);
      final var c = map.get(cType);
      transformation.accept(a, b, c,
          a == null ? null : a.getRoot(),
          b == null ? null : b.getRoot(),
          c == null ? null : c.getRoot(),
          getJobParameters());
    });
  }

  @Override
  public void setEnumType(Class<E> enumClass) {
    throw new UnsupportedOperationException("The tri enum map types may not be changed.");
  }

  public EnumMap<E, String> transform(String a, String b, String c)
      throws RecognitionException {
    var items = new EnumMap<E, String>(enumClass);
    items.put(aType, a);
    items.put(bType, b);
    items.put(cType, c);
    return transform(items);
  }

  public Triple<String> transform(Triple<String> str) throws RecognitionException {
    var result = transform(str.a, str.b, str.c);
    return new Triple<>(result.get(aType), result.get(bType), result.get(cType));
  }
}
