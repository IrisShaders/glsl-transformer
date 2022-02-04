package io.github.douira.glsl_transformer.core;

import io.github.douira.glsl_transformer.transform.Transformation;
import io.github.douira.glsl_transformer.transform.TransformationPhase;

/**
 * The wrap identifier transformation wraps the usage of a certain identifier
 * with new code by replacing its usage with a new expression and inserting code
 * that takes care of handling the conversion from the new to the old value. It
 * also checks that the wrapped value isn't already present in the code.
 */
public abstract class WrapIdentifier<T> extends Transformation<T> {
  /**
   * Creates a new wrap identifier transformation.
   * 
   * @param wrapResultFilter A phase that makes sure the wrap result doesn't
   *                         already exist in the tree
   * @param wrappingReplacer The replacer phase that should replace a target
   *                         identifier with a replacement expression or
   *                         identifier
   * @param wrappingInjector A transformation phase that does the additional
   *                         code injection, usually providing a definition
   *                         for the newly inserted identifier in the form of
   *                         an external declaration of some sort
   */
  public WrapIdentifier(
      TransformationPhase<T> wrapResultFilter,
      TransformationPhase<T> wrappingReplacer,
      TransformationPhase<T> wrappingInjector) {
    // throw if the wrap result already exists
    addPhase(wrapResultFilter);
    addPhase(wrappingReplacer);
    addConcurrentPhase(wrappingInjector);
  }
}
