package io.github.douira.glsl_transformer.ast.transform;

import java.util.EnumMap;
import java.util.function.Consumer;

import org.antlr.v4.runtime.RecognitionException;

import io.github.douira.glsl_transformer.ast.node.TranslationUnit;
import io.github.douira.glsl_transformer.ast.print.PrintType;
import io.github.douira.glsl_transformer.job_parameter.JobParameters;
import io.github.douira.glsl_transformer.util.TriConsumer;

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

  // TODO: add condensed version with consumers of 3, 4 and 5 parameters like in single AST transformer

  public TriASTTransformer(Class<E> enumClass, E aType, E bType, E cType) {
    super(enumClass);
    this.aType = aType;
    this.bType = bType;
    this.cType = cType;
    this.enumClass = enumClass;
  }

  public EnumMap<E, String> transformThree(PrintType printType, String a, String b, String c)
      throws RecognitionException {
    var items = new EnumMap<E, String>(enumClass);
    items.put(aType, a);
    items.put(bType, b);
    items.put(cType, c);
    return transform(printType, items);
  }
}
